// ============================================
// DriveEase - Admin JavaScript
// ============================================

// Load dashboard statistics
async function loadDashboardStats() {
    try {
        const response = await apiCall('/admin/dashboard');
        const stats = response.data;

        const statsCardsContainer = document.getElementById('statsCards');
        if (statsCardsContainer) {
            statsCardsContainer.innerHTML = `
                <div class="stats-card">
                    <h3>${stats.totalCars}</h3>
                    <p>Total Cars</p>
                </div>
                <div class="stats-card success">
                    <h3>${stats.totalUsers}</h3>
                    <p>Total Users</p>
                </div>
                <div class="stats-card warning">
                    <h3>${stats.totalBookings}</h3>
                    <p>Total Bookings</p>
                </div>
                <div class="stats-card danger">
                    <h3>${formatCurrency(stats.totalRevenue)}</h3>
                    <p>Total Revenue</p>
                </div>
            `;
        }
    } catch (error) {
        console.error('Error loading dashboard stats:', error);
        showAlert('Failed to load dashboard statistics', 'danger');
    }
}

// Load recent bookings
async function loadRecentBookings() {
    try {
        const response = await apiCall('/admin/bookings/recent');
        const bookings = response.data;

        const recentBookingsContainer = document.getElementById('recentBookings');
        if (recentBookingsContainer) {
            if (bookings.length === 0) {
                recentBookingsContainer.innerHTML = '<p>No recent bookings found.</p>';
            } else {
                recentBookingsContainer.innerHTML = `
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>User</th>
                                <th>Car</th>
                                <th>Pickup Date</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${bookings.map(booking => `
                                <tr>
                                    <td>#${booking.id}</td>
                                    <td>${booking.user.fullName}</td>
                                    <td>${booking.car.brand} ${booking.car.model}</td>
                                    <td>${formatDateTime(booking.pickupDate)}</td>
                                    <td>${formatCurrency(booking.totalAmount)}</td>
                                    <td><span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span></td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                `;
            }
        }
    } catch (error) {
        console.error('Error loading recent bookings:', error);
        showAlert('Failed to load recent bookings', 'danger');
    }
}

// Load all cars for admin
async function loadAdminCars() {
    try {
        const response = await apiCall('/cars');
        const cars = response.data;

        const carsTableContainer = document.getElementById('carsTable');
        if (carsTableContainer) {
            if (cars.length === 0) {
                carsTableContainer.innerHTML = '<p>No cars found.</p>';
            } else {
                carsTableContainer.innerHTML = `
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Brand</th>
                                <th>Model</th>
                                <th>Year</th>
                                <th>Price/Day</th>
                                <th>Fuel</th>
                                <th>Transmission</th>
                                <th>Available</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${cars.map(car => `
                                <tr>
                                    <td>${car.id}</td>
                                    <td>${car.brand}</td>
                                    <td>${car.model}</td>
                                    <td>${car.year}</td>
                                    <td>${formatCurrency(car.pricePerDay)}</td>
                                    <td>${car.fuelType}</td>
                                    <td>${car.transmission}</td>
                                    <td>${car.isAvailable ? 'Yes' : 'No'}</td>
                                    <td class="action-buttons">
                                        <button class="btn btn-primary btn-sm" onclick="editCar(${car.id})">Edit</button>
                                        <button class="btn btn-danger btn-sm" onclick="deleteCar(${car.id})">Delete</button>
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                `;
            }
        }
    } catch (error) {
        console.error('Error loading cars:', error);
        showAlert('Failed to load cars', 'danger');
    }
}

// Load all bookings for admin
async function loadAdminBookings(status = 'ALL') {
    try {
        let endpoint = '/admin/bookings';
        if (status !== 'ALL') {
            endpoint = `/admin/bookings/status/${status}`;
        }

        const response = await apiCall(endpoint);
        const bookings = response.data;

        const bookingsTableContainer = document.getElementById('bookingsTable');
        if (bookingsTableContainer) {
            if (bookings.length === 0) {
                bookingsTableContainer.innerHTML = '<p>No bookings found.</p>';
            } else {
                bookingsTableContainer.innerHTML = `
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>User</th>
                                <th>Car</th>
                                <th>Pickup</th>
                                <th>Return</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${bookings.map(booking => `
                                <tr>
                                    <td>#${booking.id}</td>
                                    <td>${booking.user.fullName}</td>
                                    <td>${booking.car.brand} ${booking.car.model}</td>
                                    <td>${formatDateTime(booking.pickupDate)}</td>
                                    <td>${formatDateTime(booking.returnDate)}</td>
                                    <td>${formatCurrency(booking.totalAmount)}</td>
                                    <td><span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span></td>
                                    <td class="action-buttons">
                                        ${booking.status === 'PENDING' ? `
                                            <button class="btn btn-success btn-sm" onclick="approveBooking(${booking.id})">Approve</button>
                                            <button class="btn btn-danger btn-sm" onclick="rejectBooking(${booking.id})">Reject</button>
                                        ` : ''}
                                        ${booking.status === 'CONFIRMED' ? `
                                            <button class="btn btn-primary btn-sm" onclick="completeBooking(${booking.id})">Complete</button>
                                        ` : ''}
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                `;
            }
        }
    } catch (error) {
        console.error('Error loading bookings:', error);
        showAlert('Failed to load bookings', 'danger');
    }
}

// Load all customers for admin
async function loadAdminCustomers() {
    try {
        const response = await apiCall('/users');
        const users = response.data;

        const customersTableContainer = document.getElementById('customersTable');
        if (customersTableContainer) {
            if (users.length === 0) {
                customersTableContainer.innerHTML = '<p>No customers found.</p>';
            } else {
                customersTableContainer.innerHTML = `
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Mobile</th>
                                <th>City</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${users.map(user => `
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.fullName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.mobileNumber}</td>
                                    <td>${user.city || 'N/A'}</td>
                                    <td>${user.isActive ? 'Active' : 'Inactive'}</td>
                                    <td class="action-buttons">
                                        <button class="btn btn-primary btn-sm" onclick="viewUser(${user.id})">View</button>
                                        ${user.isActive ? `
                                            <button class="btn btn-danger btn-sm" onclick="deactivateUser(${user.id})">Deactivate</button>
                                        ` : ''}
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                `;
            }
        }
    } catch (error) {
        console.error('Error loading customers:', error);
        showAlert('Failed to load customers', 'danger');
    }
}

// Filter bookings by status
function filterBookings(status) {
    loadAdminBookings(status);
}

// Show add car modal
function showAddCarModal() {
    document.getElementById('modalTitle').textContent = 'Add New Car';
    document.getElementById('carForm').reset();
    document.getElementById('carModal').style.display = 'flex';
}

// Close car modal
function closeCarModal() {
    document.getElementById('carModal').style.display = 'none';
}

// Handle car form submission
async function handleCarForm(event) {
    event.preventDefault();
    clearAlerts();

    const carData = {
        brand: document.getElementById('carBrand').value,
        model: document.getElementById('carModel').value,
        year: parseInt(document.getElementById('carYear').value),
        color: document.getElementById('carColor').value,
        licensePlate: document.getElementById('licensePlate').value,
        pricePerDay: parseFloat(document.getElementById('pricePerDay').value),
        fuelType: document.getElementById('fuelType').value,
        transmission: document.getElementById('transmission').value,
        seatingCapacity: parseInt(document.getElementById('seatingCapacity').value),
        description: document.getElementById('carDescription').value,
        imageUrl: document.getElementById('imageUrl').value,
        isAvailable: document.getElementById('isAvailable').value === 'true',
        category: { id: 1 } // Default category
    };

    try {
        const response = await apiCall('/cars', 'POST', carData);
        
        if (response.success) {
            showAlert('Car added successfully!', 'success');
            closeCarModal();
            loadAdminCars();
        } else {
            showAlert(response.message || 'Failed to add car', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to add car. Please try again.', 'danger');
    }
}

// Delete car
async function deleteCar(carId) {
    if (!confirm('Are you sure you want to delete this car?')) {
        return;
    }

    try {
        const response = await apiCall(`/cars/${carId}`, 'DELETE');
        
        if (response.success) {
            showAlert('Car deleted successfully!', 'success');
            loadAdminCars();
        } else {
            showAlert(response.message || 'Failed to delete car', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to delete car. Please try again.', 'danger');
    }
}

// Approve booking
async function approveBooking(bookingId) {
    try {
        const response = await apiCall(`/bookings/${bookingId}/approve`, 'PUT');
        
        if (response.success) {
            showAlert('Booking approved successfully!', 'success');
            loadAdminBookings();
        } else {
            showAlert(response.message || 'Failed to approve booking', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to approve booking. Please try again.', 'danger');
    }
}

// Reject booking
async function rejectBooking(bookingId) {
    const reason = prompt('Enter rejection reason:');
    if (!reason) return;

    try {
        const response = await apiCall(`/bookings/${bookingId}/reject`, 'PUT', { reason });
        
        if (response.success) {
            showAlert('Booking rejected successfully!', 'success');
            loadAdminBookings();
        } else {
            showAlert(response.message || 'Failed to reject booking', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to reject booking. Please try again.', 'danger');
    }
}

// Complete booking
async function completeBooking(bookingId) {
    try {
        const response = await apiCall(`/bookings/${bookingId}/complete`, 'PUT');
        
        if (response.success) {
            showAlert('Booking completed successfully!', 'success');
            loadAdminBookings();
        } else {
            showAlert(response.message || 'Failed to complete booking', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to complete booking. Please try again.', 'danger');
    }
}

// Deactivate user
async function deactivateUser(userId) {
    if (!confirm('Are you sure you want to deactivate this user?')) {
        return;
    }

    try {
        const response = await apiCall(`/users/${userId}`, 'DELETE');
        
        if (response.success) {
            showAlert('User deactivated successfully!', 'success');
            loadAdminCustomers();
        } else {
            showAlert(response.message || 'Failed to deactivate user', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Failed to deactivate user. Please try again.', 'danger');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    // Load dashboard stats if on dashboard page
    if (document.getElementById('statsCards')) {
        loadDashboardStats();
        loadRecentBookings();
    }

    // Load cars if on manage-cars page
    if (document.getElementById('carsTable')) {
        loadAdminCars();
        
        const carForm = document.getElementById('carForm');
        if (carForm) {
            carForm.addEventListener('submit', handleCarForm);
        }
    }

    // Load bookings if on manage-bookings page
    if (document.getElementById('bookingsTable')) {
        loadAdminBookings();
    }

    // Load customers if on manage-customers page
    if (document.getElementById('customersTable')) {
        loadAdminCustomers();
    }
});

// ============================================
// DriveEase - Booking JavaScript
// ============================================

let currentCar = null;

// Load car summary for booking page
async function loadCarSummary() {
    const urlParams = new URLSearchParams(window.location.search);
    const carId = urlParams.get('carId');

    if (!carId) {
        showAlert('Car ID not provided', 'danger');
        return;
    }

    try {
        const response = await apiCall(`/cars/${carId}`);
        currentCar = response.data;

        const carSummaryContainer = document.getElementById('carSummary');
        if (carSummaryContainer) {
            carSummaryContainer.innerHTML = `
                <img src="${currentCar.imageUrl || 'https://via.placeholder.com/400x200?text=Car+Image'}" 
                     alt="${currentCar.brand} ${currentCar.model}" 
                     style="width: 100%; height: 200px; object-fit: cover; border-radius: var(--radius);">
                <h3 style="margin-top: 1rem;">${currentCar.brand} ${currentCar.model} (${currentCar.year})</h3>
                <p><strong>Category:</strong> ${currentCar.categoryName || 'Standard'}</p>
                <p><strong>Fuel Type:</strong> ${currentCar.fuelType}</p>
                <p><strong>Transmission:</strong> ${currentCar.transmission}</p>
                <p><strong>Seating:</strong> ${currentCar.seatingCapacity} Seats</p>
                <p style="font-size: 1.25rem; color: var(--primary-color);">
                    <strong>${formatCurrency(currentCar.pricePerDay)}</strong> / day
                </p>
            `;

            // Update price per day in booking summary
            document.getElementById('pricePerDay').textContent = currentCar.pricePerDay.toLocaleString('en-IN');
        }
    } catch (error) {
        console.error('Error loading car summary:', error);
        showAlert('Failed to load car details. Please try again.', 'danger');
    }
}

// Calculate rental days and total amount
function calculateTotalAmount() {
    const pickupDate = new Date(document.getElementById('pickupDate').value);
    const returnDate = new Date(document.getElementById('returnDate').value);

    if (pickupDate && returnDate && currentCar) {
        const rentalDays = Math.ceil((returnDate - pickupDate) / (1000 * 60 * 60 * 24));
        
        if (rentalDays > 0) {
            document.getElementById('rentalDays').textContent = rentalDays;
            const totalAmount = rentalDays * currentCar.pricePerDay;
            document.getElementById('totalAmount').textContent = totalAmount.toLocaleString('en-IN');
        } else {
            document.getElementById('rentalDays').textContent = '0';
            document.getElementById('totalAmount').textContent = '0';
        }
    }
}

// Handle booking form submission
async function handleBooking(event) {
    event.preventDefault();
    clearAlerts();

    if (!isLoggedIn()) {
        showAlert('Please login to book a car', 'warning');
        setTimeout(() => {
            window.location.href = `login.html?redirect=booking.html?carId=${currentCar.id}`;
        }, 2000);
        return;
    }

    const pickupLocation = document.getElementById('pickupLocation').value;
    const dropoffLocation = document.getElementById('dropoffLocation').value;
    const pickupDate = document.getElementById('pickupDate').value;
    const returnDate = document.getElementById('returnDate').value;

    // Validation
    if (!pickupLocation || !dropoffLocation) {
        showAlert('Please enter pickup and drop-off locations', 'danger');
        return;
    }

    if (new Date(returnDate) <= new Date(pickupDate)) {
        showAlert('Return date must be after pickup date', 'danger');
        return;
    }

    const bookingData = {
        carId: currentCar.id,
        userId: currentUser.id,
        pickupLocation,
        dropoffLocation,
        pickupDate: pickupDate + ':00',
        returnDate: returnDate + ':00'
    };

    try {
        const response = await apiCall('/bookings', 'POST', bookingData);
        
        if (response.success) {
            showAlert('Booking created successfully!', 'success');
            setTimeout(() => {
                window.location.href = 'my-bookings.html';
            }, 2000);
        } else {
            showAlert(response.message || 'Booking failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Booking failed. Please try again.', 'danger');
    }
}

// Load user bookings
async function loadUserBookings(status = null) {
    if (!isLoggedIn()) {
        showAlert('Please login to view your bookings', 'warning');
        setTimeout(() => {
            window.location.href = 'login.html?redirect=my-bookings.html';
        }, 2000);
        return;
    }

    try {
        let endpoint = `/bookings/user/${currentUser.id}`;
        
        if (status) {
            endpoint = `/bookings/user/${currentUser.id}`;
            // Filter on client side for now
        }

        const response = await apiCall(endpoint);
        let bookings = response.data;

        // Filter by status if specified
        if (status && status !== 'ALL') {
            bookings = bookings.filter(b => b.status === status);
        }

        const bookingsContainer = document.getElementById('bookingsContainer');
        if (bookingsContainer) {
            if (bookings.length === 0) {
                bookingsContainer.innerHTML = '<p style="text-align: center;">No bookings found.</p>';
            } else {
                bookingsContainer.innerHTML = bookings.map(booking => `
                    <div class="card" style="margin-bottom: 1rem;">
                        <div class="card-body">
                            <div class="grid grid-2">
                                <div>
                                    <h3 class="card-title">${booking.car.brand} ${booking.car.model}</h3>
                                    <p class="card-text"><strong>Booking ID:</strong> #${booking.id}</p>
                                    <p class="card-text"><strong>Pickup:</strong> ${booking.pickupLocation}</p>
                                    <p class="card-text"><strong>Drop-off:</strong> ${booking.dropoffLocation}</p>
                                    <p class="card-text"><strong>Pickup Date:</strong> ${formatDateTime(booking.pickupDate)}</p>
                                    <p class="card-text"><strong>Return Date:</strong> ${formatDateTime(booking.returnDate)}</p>
                                </div>
                                <div>
                                    <p class="card-text"><strong>Rental Days:</strong> ${booking.rentalDays}</p>
                                    <p class="card-text"><strong>Total Amount:</strong> ${formatCurrency(booking.totalAmount)}</p>
                                    <p class="card-text">
                                        <strong>Status:</strong> 
                                        <span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span>
                                    </p>
                                    ${booking.status === 'PENDING' || booking.status === 'CONFIRMED' ? `
                                        <button class="btn btn-danger" onclick="cancelBooking(${booking.id})">Cancel Booking</button>
                                    ` : ''}
                                </div>
                            </div>
                        </div>
                    </div>
                `).join('');
            }
        }
    } catch (error) {
        console.error('Error loading bookings:', error);
        showAlert('Failed to load bookings. Please try again.', 'danger');
    }
}

// Cancel booking
async function cancelBooking(bookingId) {
    if (!confirm('Are you sure you want to cancel this booking?')) {
        return;
    }

    try {
        const response = await apiCall(`/bookings/${bookingId}/cancel`, 'PUT', { reason: 'Cancelled by user' });
        
        if (response.success) {
            showAlert('Booking cancelled successfully', 'success');
            loadUserBookings();
        } else {
            showAlert(response.message || 'Cancellation failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Cancellation failed. Please try again.', 'danger');
    }
}

// Show active bookings
function showActiveBookings() {
    loadUserBookings();
}

// Show all bookings
function showAllBookings() {
    loadUserBookings('ALL');
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    const bookingForm = document.getElementById('bookingForm');
    
    if (bookingForm) {
        bookingForm.addEventListener('submit', handleBooking);
        
        // Add event listeners for date changes
        document.getElementById('pickupDate').addEventListener('change', calculateTotalAmount);
        document.getElementById('returnDate').addEventListener('change', calculateTotalAmount);
        
        loadCarSummary();
    }

    // Load bookings if on my-bookings page
    if (document.getElementById('bookingsContainer')) {
        loadUserBookings();
    }
});

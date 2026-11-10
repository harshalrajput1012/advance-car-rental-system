// ============================================
// DriveEase - Cars JavaScript
// ============================================

// Load all cars
async function loadCars(filters = {}) {
    try {
        let endpoint = '/cars';
        let queryParams = [];

        if (filters.keyword) {
            endpoint = `/cars/search?keyword=${encodeURIComponent(filters.keyword)}`;
        }

        if (filters.brand) {
            queryParams.push(`brand=${encodeURIComponent(filters.brand)}`);
        }

        if (filters.fuelType) {
            queryParams.push(`type=${encodeURIComponent(filters.fuelType)}`);
        }

        if (filters.transmission) {
            queryParams.push(`type=${encodeURIComponent(filters.transmission)}`);
        }

        if (filters.minPrice && filters.maxPrice) {
            queryParams.push(`min=${filters.minPrice}&max=${filters.maxPrice}`);
        }

        if (filters.seating) {
            queryParams.push(`capacity=${filters.seating}`);
        }

        if (queryParams.length > 0) {
            endpoint += '?' + queryParams.join('&');
        }

        const response = await apiCall(endpoint);
        const cars = response.data;

        const carsGrid = document.getElementById('carsGrid');
        if (carsGrid) {
            if (cars.length === 0) {
                carsGrid.innerHTML = '<p style="grid-column: 1/-1; text-align: center;">No cars found matching your criteria.</p>';
            } else {
                carsGrid.innerHTML = cars.map(car => `
                    <div class="card">
                        <img src="${car.imageUrl || 'https://via.placeholder.com/400x200?text=Car+Image'}" 
                             alt="${car.brand} ${car.model}" 
                             class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">${car.brand} ${car.model}</h3>
                            <p class="card-text">${car.categoryName || 'Standard'}</p>
                            <p class="card-text">
                                <span class="badge badge-primary">${car.fuelType}</span>
                                <span class="badge badge-secondary">${car.transmission}</span>
                                <span class="badge badge-success">${car.seatingCapacity} Seats</span>
                            </p>
                            <p class="card-text"><strong>${formatCurrency(car.pricePerDay)}</strong> / day</p>
                            <div style="display: flex; gap: 0.5rem;">
                                <a href="car-details.html?id=${car.id}" class="btn btn-primary" style="flex: 1;">View Details</a>
                                <a href="booking.html?carId=${car.id}" class="btn btn-success" style="flex: 1;">Book Now</a>
                            </div>
                        </div>
                    </div>
                `).join('');
            }
        }
    } catch (error) {
        console.error('Error loading cars:', error);
        showAlert('Failed to load cars. Please try again.', 'danger');
    }
}

// Load car details
async function loadCarDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const carId = urlParams.get('id');

    if (!carId) {
        showAlert('Car ID not provided', 'danger');
        return;
    }

    try {
        const response = await apiCall(`/cars/${carId}`);
        const car = response.data;

        const carDetailsContainer = document.getElementById('carDetails');
        if (carDetailsContainer) {
            carDetailsContainer.innerHTML = `
                <div class="card">
                    <img src="${car.imageUrl || 'https://via.placeholder.com/800x400?text=Car+Image'}" 
                         alt="${car.brand} ${car.model}" 
                         style="width: 100%; height: 400px; object-fit: cover;">
                    <div class="card-body">
                        <h1 class="card-title" style="font-size: 2rem;">${car.brand} ${car.model} (${car.year})</h1>
                        <p class="card-text" style="font-size: 1.25rem; color: var(--primary-color);">
                            <strong>${formatCurrency(car.pricePerDay)}</strong> / day
                        </p>
                        <div style="margin: 1.5rem 0;">
                            <span class="badge badge-primary" style="font-size: 1rem; padding: 0.5rem 1rem;">${car.fuelType}</span>
                            <span class="badge badge-secondary" style="font-size: 1rem; padding: 0.5rem 1rem;">${car.transmission}</span>
                            <span class="badge badge-success" style="font-size: 1rem; padding: 0.5rem 1rem;">${car.seatingCapacity} Seats</span>
                            <span class="badge badge-warning" style="font-size: 1rem; padding: 0.5rem 1rem;">${car.categoryName || 'Standard'}</span>
                        </div>
                        <p class="card-text" style="font-size: 1.1rem;">${car.description || 'No description available.'}</p>
                        <div style="margin-top: 2rem;">
                            <a href="booking.html?carId=${car.id}" class="btn btn-primary btn-lg">Book This Car</a>
                            <a href="cars.html" class="btn btn-outline btn-lg">Back to Cars</a>
                        </div>
                    </div>
                </div>
            `;
        }
    } catch (error) {
        console.error('Error loading car details:', error);
        showAlert('Failed to load car details. Please try again.', 'danger');
    }
}

// Filter form handler
document.addEventListener('DOMContentLoaded', function() {
    const filterForm = document.getElementById('filterForm');
    
    if (filterForm) {
        filterForm.addEventListener('submit', function(event) {
            event.preventDefault();
            
            const filters = {
                keyword: document.getElementById('searchKeyword').value,
                brand: document.getElementById('brandFilter').value,
                fuelType: document.getElementById('fuelTypeFilter').value,
                transmission: document.getElementById('transmissionFilter').value,
                minPrice: document.getElementById('minPrice').value,
                maxPrice: document.getElementById('maxPrice').value,
                seating: document.getElementById('seatingFilter').value
            };

            loadCars(filters);
        });
    }

    // Load cars on page load
    if (document.getElementById('carsGrid')) {
        loadCars();
    }

    // Load car details if on car-details page
    if (document.getElementById('carDetails')) {
        loadCarDetails();
    }
});

// Reset filters
function resetFilters() {
    document.getElementById('filterForm').reset();
    loadCars();
}

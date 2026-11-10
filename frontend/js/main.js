// ============================================
// DriveEase - Main JavaScript
// ============================================

// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Current user (stored in localStorage)
let currentUser = JSON.parse(localStorage.getItem('currentUser')) || null;

// Check if user is logged in
function isLoggedIn() {
    return currentUser !== null;
}

// Get current user
function getCurrentUser() {
    return currentUser;
}

// Set current user
function setCurrentUser(user) {
    currentUser = user;
    localStorage.setItem('currentUser', JSON.stringify(user));
}

// Clear current user (logout)
function clearCurrentUser() {
    currentUser = null;
    localStorage.removeItem('currentUser');
}

// Logout function
function logout() {
    clearCurrentUser();
    window.location.href = 'login.html';
}

// Show alert message
function showAlert(message, type = 'info') {
    const alertContainer = document.getElementById('alertContainer');
    if (alertContainer) {
        const alertClass = `alert-${type}`;
        alertContainer.innerHTML = `<div class="alert ${alertClass}">${message}</div>`;
        
        // Auto-hide after 5 seconds
        setTimeout(() => {
            alertContainer.innerHTML = '';
        }, 5000);
    }
}

// Clear alerts
function clearAlerts() {
    const alertContainer = document.getElementById('alertContainer');
    if (alertContainer) {
        alertContainer.innerHTML = '';
    }
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}

// Format date and time
function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('en-IN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Format currency
function formatCurrency(amount) {
    return '₹' + amount.toLocaleString('en-IN');
}

// Toggle password visibility
function togglePassword(inputId) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
    } else {
        input.type = 'password';
    }
}

// API call helper function
async function apiCall(endpoint, method = 'GET', data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, options);
        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || 'API call failed');
        }

        return result;
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Load featured cars on home page
async function loadFeaturedCars() {
    try {
        const response = await apiCall('/cars');
        const cars = response.data.slice(0, 6); // Get first 6 cars

        const featuredCarsContainer = document.getElementById('featuredCars');
        if (featuredCarsContainer) {
            featuredCarsContainer.innerHTML = cars.map(car => `
                <div class="card">
                    <img src="${car.imageUrl || 'https://via.placeholder.com/400x200?text=Car+Image'}" 
                         alt="${car.brand} ${car.model}" 
                         class="card-img-top">
                    <div class="card-body">
                        <h3 class="card-title">${car.brand} ${car.model}</h3>
                        <p class="card-text">${car.categoryName || 'Standard'}</p>
                        <p class="card-text"><strong>${formatCurrency(car.pricePerDay)}</strong> / day</p>
                        <a href="car-details.html?id=${car.id}" class="btn btn-primary">View Details</a>
                    </div>
                </div>
            `).join('');
        }
    } catch (error) {
        console.error('Error loading featured cars:', error);
    }
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    // Load featured cars if on home page
    if (document.getElementById('featuredCars')) {
        loadFeaturedCars();
    }

    // Update navigation based on login status
    updateNavigation();
});

// Update navigation based on login status
function updateNavigation() {
    const navLinks = document.querySelectorAll('.navbar-nav');
    
    navLinks.forEach(nav => {
        if (isLoggedIn()) {
            // User is logged in
            const loginLink = nav.querySelector('a[href="login.html"]');
            const registerLink = nav.querySelector('a[href="register.html"]');
            
            if (loginLink) loginLink.style.display = 'none';
            if (registerLink) registerLink.style.display = 'none';
        }
    });
}

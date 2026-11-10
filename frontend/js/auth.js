// ============================================
// DriveEase - Authentication JavaScript
// ============================================

// Register form handler
document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }
});

// Handle user registration
async function handleRegister(event) {
    event.preventDefault();
    clearAlerts();

    const fullName = document.getElementById('fullName').value;
    const email = document.getElementById('email').value;
    const mobileNumber = document.getElementById('mobileNumber').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const city = document.getElementById('city').value;
    const state = document.getElementById('state').value;

    // Validation
    if (password !== confirmPassword) {
        showAlert('Passwords do not match', 'danger');
        return;
    }

    if (mobileNumber.length !== 10) {
        showAlert('Mobile number must be 10 digits', 'danger');
        return;
    }

    const registerData = {
        fullName,
        email,
        mobileNumber,
        password,
        confirmPassword,
        city,
        state
    };

    try {
        const response = await apiCall('/auth/register', 'POST', registerData);
        
        if (response.success) {
            showAlert('Registration successful! Please login.', 'success');
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
        } else {
            showAlert(response.message || 'Registration failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Registration failed. Please try again.', 'danger');
    }
}

// Handle user login
async function handleLogin(event) {
    event.preventDefault();
    clearAlerts();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const loginData = {
        email,
        password
    };

    try {
        const response = await apiCall('/auth/login', 'POST', loginData);
        
        if (response.success) {
            setCurrentUser(response.data);
            showAlert('Login successful!', 'success');
            
            // Redirect based on user role
            setTimeout(() => {
                if (response.data.role && response.data.role.roleName === 'ADMIN') {
                    window.location.href = 'admin/dashboard.html';
                } else {
                    window.location.href = 'index.html';
                }
            }, 1000);
        } else {
            showAlert(response.message || 'Login failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Login failed. Please check your credentials.', 'danger');
    }
}

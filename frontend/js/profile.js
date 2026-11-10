// ============================================
// DriveEase - Profile JavaScript
// ============================================

// Load user profile
async function loadProfile() {
    if (!isLoggedIn()) {
        showAlert('Please login to view your profile', 'warning');
        setTimeout(() => {
            window.location.href = 'login.html?redirect=profile.html';
        }, 2000);
        return;
    }

    try {
        const response = await apiCall(`/users/${currentUser.id}`);
        const user = response.data;

        // Update current user data
        setCurrentUser(user);

        const profileInfoContainer = document.getElementById('profileInfo');
        if (profileInfoContainer) {
            profileInfoContainer.innerHTML = `
                <p><strong>Name:</strong> ${user.fullName}</p>
                <p><strong>Email:</strong> ${user.email}</p>
                <p><strong>Mobile:</strong> ${user.mobileNumber}</p>
                <p><strong>Address:</strong> ${user.address || 'Not provided'}</p>
                <p><strong>City:</strong> ${user.city || 'Not provided'}</p>
                <p><strong>State:</strong> ${user.state || 'Not provided'}</p>
                <p><strong>Postal Code:</strong> ${user.postalCode || 'Not provided'}</p>
                <p><strong>Country:</strong> ${user.country}</p>
                <p><strong>Account Status:</strong> ${user.isActive ? 'Active' : 'Inactive'}</p>
            `;
        }

        // Populate update form
        document.getElementById('fullName').value = user.fullName;
        document.getElementById('mobileNumber').value = user.mobileNumber;
        document.getElementById('address').value = user.address || '';
        document.getElementById('city').value = user.city || '';
        document.getElementById('state').value = user.state || '';
        document.getElementById('postalCode').value = user.postalCode || '';
    } catch (error) {
        console.error('Error loading profile:', error);
        showAlert('Failed to load profile. Please try again.', 'danger');
    }
}

// Handle profile update
async function handleProfileUpdate(event) {
    event.preventDefault();
    clearAlerts();

    const updateData = {
        fullName: document.getElementById('fullName').value,
        mobileNumber: document.getElementById('mobileNumber').value,
        address: document.getElementById('address').value,
        city: document.getElementById('city').value,
        state: document.getElementById('state').value,
        postalCode: document.getElementById('postalCode').value
    };

    try {
        const response = await apiCall(`/users/${currentUser.id}`, 'PUT', updateData);
        
        if (response.success) {
            showAlert('Profile updated successfully!', 'success');
            setCurrentUser(response.data);
            loadProfile();
        } else {
            showAlert(response.message || 'Profile update failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Profile update failed. Please try again.', 'danger');
    }
}

// Handle password change
async function handlePasswordChange(event) {
    event.preventDefault();
    clearAlerts();

    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmNewPassword = document.getElementById('confirmNewPassword').value;

    // Validation
    if (newPassword !== confirmNewPassword) {
        showAlert('New passwords do not match', 'danger');
        return;
    }

    if (newPassword.length < 6) {
        showAlert('New password must be at least 6 characters', 'danger');
        return;
    }

    const passwordData = {
        oldPassword: currentPassword,
        newPassword: newPassword
    };

    try {
        const response = await apiCall(`/users/${currentUser.id}/password`, 'PUT', passwordData);
        
        if (response.success) {
            showAlert('Password changed successfully!', 'success');
            document.getElementById('changePasswordForm').reset();
        } else {
            showAlert(response.message || 'Password change failed', 'danger');
        }
    } catch (error) {
        showAlert(error.message || 'Password change failed. Please try again.', 'danger');
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', function() {
    const updateProfileForm = document.getElementById('updateProfileForm');
    const changePasswordForm = document.getElementById('changePasswordForm');

    if (updateProfileForm) {
        updateProfileForm.addEventListener('submit', handleProfileUpdate);
    }

    if (changePasswordForm) {
        changePasswordForm.addEventListener('submit', handlePasswordChange);
    }

    // Load profile if on profile page
    if (document.getElementById('profileInfo')) {
        loadProfile();
    }
});

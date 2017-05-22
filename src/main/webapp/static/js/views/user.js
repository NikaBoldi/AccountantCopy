function showSsoForm() {
    hidePasswdForm();
    document.getElementById('noEditSso').style.display = "none";
    document.getElementById('editSso').style.display = "block";
    document.getElementById('mainButtons').style.display = "none";
}

function showPasswdForm() {
    hideSsoForm();
    document.getElementById('noEditPasswd').style.display = "none";
    document.getElementById('editPasswd').style.display = "block";
    document.getElementById('mainButtons').style.display = "none";
}

function hideSsoForm() {
    var validator = $('#userForm').data('formValidation');
    validator.resetField('ssoId');
    validator.resetField('passwdOld');
    document.getElementById('noEditSso').style.display = "block";
    document.getElementById('editSso').style.display = "none";
    document.getElementById('mainButtons').style.display = "block";
}

function hidePasswdForm() {
    var validator = $('#userForm').data('formValidation');
    validator.resetField('passwdOldPass');
    validator.resetField('passwdNew');
    validator.resetField('confirmPasswd');

    $('#passwdOldPass').val('');
    $('#passwdNew').val('');
    $('#confirmPasswd').val('');

    document.getElementById('noEditPasswd').style.display = "block";
    document.getElementById('editPasswd').style.display = "none";
    document.getElementById('mainButtons').style.display = "block";
}

function editSsoId() {
    $('#passwdOldPass').prop('disabled', true);
}

function editPasswd() {
    $('#passwdOldSso').prop('disabled', true);
}

$(document).ready(function() {
    validateUserForm('#userForm');
});
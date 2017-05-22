function deleteUser(dialogHeader, dialogBody, id) {
    deleteConfirm(dialogHeader, dialogBody, function() {
        $.ajax({
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            },
            url : "user/delete",
            datatype : "json",
            type : "POST",
            async : 'true',
            data : JSON.stringify(id),
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-Token', csrf_token);
            },
            success : function(response) {
                location.reload();
            },
            error : function(request, status, error) {
                alert("ERROR!!!!!!!");
            }
        });
    });
}

function getUser(id) {
    $.ajax({
        url : "user/" + id,
        type : 'GET',
        async : 'true',
        success : function(data) {
            $('#modalContentUserForm').html(data);
            validateUserForm('#userForm', true);
            $('#userFormModal').modal('show');
        },
        error : function(e) {
            alert("error" + e);
        }
    });
}
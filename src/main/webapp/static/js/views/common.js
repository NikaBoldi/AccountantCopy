function deleteConfirm(dialogHeader, dialogBody, func) {
    $("#dialogHeader").text(dialogHeader);
    $("#dialogBody").text(dialogBody);
    $("#confirmDialogModal").modal();
    $("#dialogConfirmButton").off('click').on("click", func);
}
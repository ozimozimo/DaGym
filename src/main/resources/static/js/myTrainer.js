function myTrainerDetailView() {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);

    var option = 'status=no, height=600, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    // var pageValue = $('#pageValue').val() || "";
    // var typeValue = $('#typeValue').val() || "";
    // var keywordValue = $('#keywordValue').val() || "";
    // var trainerId= $('#trainer_id').val();
    var url = `/ptUser/detail?id=${trainerId}&page=${pageValue}&type=${typeValue}&keyword=${keywordValue}`

    window.open(url, 'myTrainerDetailView', option);

}

function detailView(trainerId) {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);

    var option = 'status=no, height=600, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    var pageValue = $('#pageValue').val() || "";
    var typeValue = $('#typeValue').val() || "";
    var keywordValue = $('#keywordValue').val() || "";

    var url = `/ptUser/detail?id=${trainerId}&page=${pageValue}&type=${typeValue}&keyword=${keywordValue}`

    window.open(url, 'detailView', option);
}


function search() {
    var searchForm = $('#searchForm');
    searchForm.submit();
}

function Sclear() {
    var searchForm = $('#searchForm');
    searchForm.empty().submit();
}

function check(trainer_id) {
    var member_id = $('input[name=member_id]').val();
    console.log("member 값은"+member_id);


    console.log("trainer 값은" + trainer_id);

    $.ajax({
        type: 'get',
        url: '/ptUser/apply/check?',
        data: 'member_id='+member_id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (data.accept_condition == 0) {
            alert('PT 수락 대기 중입니다.');
            window.location.href = '/ptUser/view';
        } else if (data.accept_condition == 1) {
            alert('PT가 진행 중입니다.');
            window.location.href = '/ptUser/view';
        } else if(data.accept_condition == null || data.accept_condition == 2){
            console.log(data);
        }
    }).fail(function () {
        alert('PT신청 페이지로 이동하겠습니다');
        window.location.href = '/ptUser/apply?member_id='+member_id+"&trainer_id="+trainer_id;
    })
}


$(function (){

    $('.search_btn').on("click",search);
    $('.sclear_btn').on("click",Sclear);
    $('.checkBtn').on("click",function (){
        let trainerId = $(this).parent().parent().children('.trainerId').val();
        check(trainerId);
    });
    $('.detailBtn').on("click",function (){
        let trainerId = $(this).parent().parent().children('.trainerId').val();
        detailView(trainerId);
    });
})


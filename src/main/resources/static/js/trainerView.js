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

function discount() {
    let dis = $('.pt_discount');
    dis.each(function (i, el) {
        let disInfo = $(this).text();
        console.log("할인 % =" + disInfo);
        let di = parseInt(disInfo);
        console.log(typeof di);

    });
}

function basicInfo() {
    var total = $('.pt_total');
    total.each(function (i, el) {
        let count = $(this).text().substring(0, 3);
        let price = $(this).text().substring(5);

        let addCount = $('.pt_addCount').val();

        console.log("가격은 =" + price);
        console.log("PT횟수는 =" + count);
        console.log("추가횟수는  =" + addCount);


    });
}

function addr() {
    var addr = $('.address_normal');
    addr.each(function (i, el) {
        let normal = $(this).text().substring(5);
        console.log("주소는 =" + normal);
        $(this).text(normal);
    });
}

function age() {
    let ss = $('.user_rrn')
    ss.each(function (i, el) {
        var today = new Date();
        var year = String(today.getFullYear()).substring(2, 4);
        let getUserYear = parseInt($(this).text().substring(0, 2));
        // 00년대생 이상
        if (year - getUserYear >= 0) {
            let age = year - getUserYear + 1;
            $(this).text(age);
        } else {
            let age = year - getUserYear + 101;
            $(this).text(age);
        }
    });
}

function gender() {
    let gender = $('.user_gender');
    gender.each(function (i, el) {
        let text = parseInt($(this).text());
        if (text % 2 == 1) {
            $(this).text("남");
        } else {
            $(this).text("여");
        }
    })
}

function TrReviewList(trainerId) {
    console.log("function TrReviewList id = " + trainerId);

    $.ajax({
        type: 'get',
        url: '/pt/review/list/' + trainerId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        ReviewList(data);
        alert("성공");
    }).fail(function () {
        alert("실패");
    })
}


$(function (){
    gender();
    age();
    addr();
    basicInfo();
    discount();

    $('.search_btn').on("click",search);
    $('.clear_btn').on("click",Sclear);

    $('.checkBtn').on("click",function (){
        let trainerId = $(this).parent().parent().parent().children('.trainerId').val();
        // console.log(trainerId);
        check(trainerId);
    });
    $('.reviewBtn').on("click", function () {
        let trainerId = $(this).parent().parent().parent().children('.trainerId').val();
        console.log(trainerId);
        TrReviewList(trainerId);
    });
    $('.detailBtn').on("click",function (){
        let trainerId = $(this).parent().parent().parent().children('.trainerId').val();;
        detailView(trainerId);
    });
})



// var searchForm = $('#searchForm');

// function search() {
//     searchForm.submit();
// }
//
// function Sclear() {
//     searchForm.empty().submit();
// }

function search() {
    let value = $('#b').val();
    console.log(value);
    $('#a').val(value);
    console.log($('#a').val());

    var searchForm = $('#searchForm');
    searchForm.submit();

}

function Sclear() {
    var searchForm = $('#searchForm');
    searchForm.empty().submit();
}

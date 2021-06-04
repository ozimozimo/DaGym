$(function () {
    changeId();
    $(".addEx_btn").on("click", addExBtnClick);
    todayData();

});

let date = new Date();
date = getFormatDate(date);

function changeId(){
    let location = window.location.href;
    let arr = location.split("=");
    $('.member').val(arr[1]);
    console.log('arr : ' + arr[1]);
   $('.ex_record_member_id').val(arr[1]);
}

function getFormatDate(date) {
    let year = date.getFullYear();
    let month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    let day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

function addExBtnClick() {
    let id = $(".member").val();
    let ex_record_member_id = $('.ex_record_member_id').val();
    let ex_name = $("#ex_name").val();
    let ex_set = $("#ex_set").val();
    let ex_count = $("#ex_count").val();
    let ex_weight = $("#ex_weight").val();
    let ex_date = $('.date').val();
    console.log(id);
    let data = {
        id: id,
        ex_record_member_id: ex_record_member_id,
        ex_name: ex_name,
        ex_set: ex_set,
        ex_count: ex_count,
        ex_weight: ex_weight,
        ex_date: ex_date
    }
    // 모두 입력하게 하기
    if (
        ex_name.length == 0 ||
        ex_set.length == 0 ||
        ex_count.length == 0 ||
        ex_weight.length == 0
    ) {
        alert("모두 입력해주세요");
        return false;
    }
    $.ajax({
        type: 'post',
        url: '/ExRecord/save/' + id,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function (data) {
        alert('운동이 추가되었습니다')
        location.reload();
    }).fail(function (error) {
        alert(error);
    })
}

function delExBtnClick(a) {
    let id = a.parentNode.parentNode.firstChild.innerText;
    console.log(id);
    $.ajax({
        type: 'post',
        url: '/ExRecord/delete/' + id,
        contentType: 'application/json; charset=utf-8',
    }).done(function () {
        alert('기록이 삭제되었습니다');
        location.reload();
    }).fail(function (error) {
        alert(error);
        console.log(JSON.stringify(error));
    })
}

function mkExr(result) {
    let id = $('.loginId').text();
    for (let i = 0; i < result.length; i++) {
        let a = result[i].ex_record_id;
        let b = result[i].ex_record_member_id;
        let c = result[i].ex_name;
        let d = result[i].ex_set;
        let e = result[i].ex_count;
        let f = result[i].ex_weight;
        let g = result[i].ex_date;

        let content = "<tr>"
        content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
        content += "<td class='res_ex_name'>" + c + "</td>"
        content += "<td class='res_ex_set'>" + d + "세트</td>"
        content += "<td class='res_ex_count'>" + e + "개</td>"
        content += "<td class='res_ex_weight'>" + f + "KG</td>"
        content += "<td class='res_ex_date' style='display: none'>" + g + "</td>"
        if (id == b)
            content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>"
        content += "</tr>"
        $('.resultEx').append(content);
    }
}

// 오늘 날짜 데이터
function todayData() {
    let today = date;
    let id = $('.ex_record_member_id').val();
    let data = {
        id: id,
        ex_date: today
    }
    $.ajax({
        type: 'get',
        url: '/ExRecord/exDate/',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        mkExr(result);
    }).fail(function () {
        alert('문제가 있다');
    })
}
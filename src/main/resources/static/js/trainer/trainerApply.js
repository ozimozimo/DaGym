$(function () {
    $(".pre").on("click", preBtn);
    $(".next").on("click", nextBtn);
    $('#pt_puerpose_etc_btn').on("click", function () {
        $('#pt_puerpose_etc').toggle();
    });
    $('.submit').on("click",trainerApplySumbit);
    changBarPercent();
    gender();
    age();
});

function age() {
    var today = new Date();
    var year = String(today.getFullYear()).substring(2, 4);

    let ss = $('#user_rrn').val();

    let getUserYear = parseInt(ss.substring(0, 2));

    // 00년대생 이상
    if (year - getUserYear >= 0) {
        // console.log("현재 년도 : ", year);
        // console.log("태어난 년도 : ", getUserYear);

        let age = year - getUserYear + 1;
        // console.log(age);
        $("#user_age").attr("value", age);

    } else {
        // console.log("현재 년도 : ", year);
        // console.log("태어난 년도 : ", getUserYear);

        let age = year - getUserYear + 101;
        // console.log(age);
        $("#user_age").attr("value", age);
    }
}


function gender() {
    // 기본주소 값 가져오기
    let gender = $('#user_gender').val();

    gender = parseInt(gender);
    console.log(gender + "성별");
    console.log(typeof (gender));

    if (gender % 2 == 1) {
        $("input:radio[name='user_gender1']:radio[value='남']").prop("checked", true);
    } else {
        $("input:radio[name='user_gender2']:radio[value='여']").prop("checked", true);
    }
}

//페이지 추가 될때마다 false 값 뒤에 추가 하면됨
let page = [true, false, false, false, false, false];
// 퍼센트 시작점
let percentValue = 0;
// 퍼센트 올라가는 비율
let updatePercentValue = 20;

function nextBtn() {
    percentValue += updatePercentValue;
    $(".percent").text(percentValue + "%");
    changBarPercent(percentValue);
    let showPage;
    let hidePage;

    for (let i = 0; i < page.length; i++) {
        if (page[i]) {
            showPage = i + 1;
            hidePage = i;
            $(".pre").show();

            //   페이지가 0이 아닐때
            if (i != 0) {
                page[i] = false;
                // 페이지가 1 이상 끝-1 사이일때
                if (i < page.length - 1) {
                    page[i + 1] = true;
                    console.log("중간");
                    //페이지가 끝일때
                    if (i == page.length - 2) {
                        console.log("끝");
                        $(".submit").show();
                        $(".next").hide();
                    }
                }
            }
            // 페이지가 0일때
            else if (i == 0) {
                page[i] = false;
                page[i + 1] = true;
                console.log("중간");
            }

            $(".contents").eq(showPage).show();
            $(".contents").eq(hidePage).hide();
            console.log("페이지" + (showPage + 1));
            break;
        }
    }
    console.log(page);
}

function preBtn() {
    percentValue -= updatePercentValue;
    $(".percent").text(percentValue + "%");
    changBarPercent(percentValue);
    let showPage;
    let hidePage;
    $(".submit").hide();
    $(".next").show();
    for (let i = 0; i < page.length; i++) {
        if (page[i]) {
            showPage = i - 1;
            hidePage = i;

            //   페이지가 1이 아닐때

            $(".pre").show();
            page[i] = false;
            // 페이지가 1 이상 끝-1 사이일때
            if (i < page.length) {
                page[i - 1] = true;
            }
            if (i == 1) $(".pre").hide();
            $(".contents").eq(showPage).show();
            $(".contents").eq(hidePage).hide();
            console.log("페이지" + (showPage + 1));
            break;
        }
    }
    console.log(page);
}

function changBarPercent(percent) {
    const meters = document.querySelectorAll("svg[data-value] .meter");

    meters.forEach((path) => {
        // Get the length of the path
        let length = path.getTotalLength();

        // console.log(length) and hardcode the value for both stroke-dasharray & stroke-dashoffest styles
        // If unable to hardcode, set dynamically...
        // path.style.strokeDashoffset = length;
        // path.style.strokeDasharray = length;

        // Get the value of the meter
        // let value = parseInt(path.parentNode.getAttribute("data-value"));
        let value = percent || parseInt(path.parentNode.getAttribute("data-value"));
        // Calculate the percentage of the total length
        let to = length * ((100 - value) / 100);

        path.getBoundingClientRect();
        // Set the Offset
        path.style.strokeDashoffset = Math.max(0, to);
    });
}

function trainerApplySumbit() {
    let member_id = $('#member_id').val();
    let trainer_id = $('#trainer_id').val();
    let member_height = $('#member_height').val();
    let member_weight = $('#member_weight').val();
    let pt_purpose='';
    $('input[name="pt_purpose"]:checked').each(function () {
        var chk = $(this).val();
        if(chk=="기타"){
            chk = $('#pt_puerpose_etc').val();
            console.log(chk);
        }
        pt_purpose += chk + ",";
    });


    // 시작날짜 정하는거
    let pt_count = $('#pt_count').val();
    // 가능한 요일
    let pt_positiveDate='';
    $('input[name="pt_positiveDate"]:checked').each(function () {
        var chk = $(this).val();
        pt_positiveDate += chk + ",";
    });

    // 원하는 시간
    let pt_wantTime='';
    $('input[name="pt_wantTime"]:checked').each(function () {
        var chk = $(this).val();
        pt_wantTime += chk + ",";
    });

    let data = {
        member_height,
        member_weight,
        pt_purpose,
        pt_count,
        pt_positiveDate,
        pt_wantTime
    }

    console.log(data);
    $.ajax({
        type: 'post',
        url: '/ptUser/apply/success/' + member_id + "/" + trainer_id,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        alert("PT 신청 하셨습니다");

        console.log(data);

    }).fail(function (error) {
        alert("PT 신청에 실패하였습니다");
        console.log(error);
    })
}
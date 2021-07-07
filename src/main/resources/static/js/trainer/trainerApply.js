$(function () {
    $(".pre").on("click", preBtn);
    $(".next").on("click", nextBtn);
    $('#pt_puerpose_etc_btn').on("click", function () {
        $('#pt_puerpose_etc').toggle();
    });
    $('.submit').on("click", trainerApplySumbit);
    changBarPercent();
    gender();
    age();
    changePt();

    var sub = $('#final_data').val();
    var count = sub.substring(0, 2);
    var price = sub.substring(6, 15);
    var a = parseInt(price);
    var b = parseInt(count);
    console.log(sub);
    console.log("가격" + a);
    console.log("수는=" + b);
    $('#a1').text(count + "회 / " + price);
    $('#a2').text(count * 2 + "회 / " + price * 2);
    $('#a3').text(count * 3 + "회 / " + price * 3);

    $('#price').val(price);
});

function changePt() {
    var sub = $('#final_data').val();
    var count = sub.substring(0, 2);
    var price = sub.substring(6, 15);

    var a = parseInt(price);
    var b = parseInt(count);
    console.log(sub);
    console.log("수는" + count);
    console.log("가격=" + price);


    $('#selectPrice').change(function () {
        $('#selectPrice option:selected').each(function () {
            var data = $('#selectPrice').val();
            $('#final_data').val($(this).text());
            var da = $('#final_data').val();

            $('#final_info').val(da);
        })
    });

}

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
        $('#final_age').attr("value", age);

    } else {
        // console.log("현재 년도 : ", year);
        // console.log("태어난 년도 : ", getUserYear);

        let age = year - getUserYear + 101;
        // console.log(age);
        $("#user_age").attr("value", age);
        $('#final_age').attr("value", age);

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
        $("input:radio[name='final_gender1']:radio[value='남']").prop("checked", true);
    } else {
        $("input:radio[name='user_gender2']:radio[value='여']").prop("checked", true);
        $("input:radio[name='final_gender2']:radio[value='여']").prop("checked", true);
    }
}

//페이지 추가 될때마다 false값 뒤에 추가 하면됨
let page = [true, false, false, false, false, false, false, false];
// 퍼센트 시작점
let percentValue = 0;
// 퍼센트 올라가는 비율
let updatePercentValue = 15;

function nextBtn() {

    percentValue += updatePercentValue;
    if(percentValue > 100){
        percentValue = 100;
    }
    $(".percent").text(percentValue + "%");
    changBarPercent(percentValue);
    let showPage;
    let hidePage;
    let member_height = $('#member_height').val();
    let member_weight = $('#member_weight').val();

    let pt_purpose = '';
    $('input[name="pt_purpose"]:checked').each(function () {
        var chk = $(this).val();
        if (chk == "기타") {
            chk = $('#pt_puerpose_etc').val();
            console.log(chk);
        }
        pt_purpose += chk + ",";
    });
    let pt_count = $('#pt_count').val();
    let pt_positiveDate = '';
    $('input[name="pt_positiveDate"]:checked').each(function () {
        var chk = $(this).val();
        pt_positiveDate += chk + ",";
    });

    let pt_wantTime = '';
    $('input[name="pt_wantTime"]:checked').each(function () {
        var chk = $(this).val();
        pt_wantTime += chk + ",";
    });
    var sub = $('#final_data').val();
    var count = sub.substring(0, 2);
    var price = sub.substring(6, 15);
    var a = parseInt(price);
    var b = parseInt(count);
    console.log(sub);
    console.log("가격" + a);
    console.log("수는=" + b);
    $('#a1').text(count + "회 / " + price);
    $('#a2').text(count * 2 + "회 / " + price * 2);
    $('#a3').text(count * 3 + "회 / " + price * 3);


    $('#count').val(count);
    $('#final_price').val(price);
    $('#final_pt_purpose').val(pt_purpose);
    $('#final_height').val(member_height);
    $('#final_weight').val(member_weight);
    $('#final_count').val(pt_count);
    $('#final_positiveDate').val(pt_positiveDate);
    $('#final_wantTime').val(pt_wantTime);

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
    if(percentValue < 0){
        percentValue = 0;
    }
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
    $(".progress-bar").css({width: percent + "%"});
    // const meters = document.querySelectorAll("svg[data-value] .meter");

    // meters.forEach((path) => {
    //     // Get the length of the path
    //     let length = path.getTotalLength();

    //     // console.log(length) and hardcode the value for both stroke-dasharray & stroke-dashoffest styles
    //     // If unable to hardcode, set dynamically...
    //     // path.style.strokeDashoffset = length;
    //     // path.style.strokeDasharray = length;

    //     // Get the value of the meter
    //     // let value = parseInt(path.parentNode.getAttribute("data-value"));
    //     let value = percent || parseInt(path.parentNode.getAttribute("data-value"));
    //     // Calculate the percentage of the total length
    //     let to = length * ((100 - value) / 100);

    //     path.getBoundingClientRect();
    //     // Set the Offset
    //     path.style.strokeDashoffset = Math.max(0, to);
    // });
}

function trainerApplySumbit() {
    let member_id = $('#member_id').val();
    let trainer_id = $('#trainer_id').val();
    let member_height = $('#member_height').val();
    let member_weight = $('#member_weight').val();
    let pt_purpose = '';
    $('input[name="pt_purpose"]:checked').each(function () {
        var chk = $(this).val();
        if (chk == "기타") {
            chk = $('#pt_puerpose_etc').val();
            console.log(chk);
        }
        pt_purpose += chk + ",";
    });

    let pt_count = $('#pt_count').val();
    let pt_positiveDate = '';
    $('input[name="pt_positiveDate"]:checked').each(function () {
        var chk = $(this).val();
        pt_positiveDate += chk + ",";
    });
    let pt_wantTime = '';
    $('input[name="pt_wantTime"]:checked').each(function () {
        var chk = $(this).val();
        pt_wantTime += chk + ",";
    });

    let pt_times = $('#count').val();
    let final_price = $('#final_price').val();

    let name = $('#user_name').val();
    let pn = $('#user_pn').val();



    console.log("pt횟수는 =" + pt_times);
    console.log("최종 가격은 =" + final_price);



    var IMP = window.IMP;
    IMP.init('imp07054769');
    // 원래는 amount 에 실제 결제금액 final_price를 넣어야 된다
    IMP.request_pay({
            pg: 'inicis', // version 1.1.0부터 지원.
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: '주문명:PT결제테스트',
            amount: 100,
            buyer_name: name,
            buyer_tel: pn,
            m_redirect_url: 'https://www.yourdomain.com/payments/complete'
        },
        function (rsp) {
            if (rsp.success) {
                // console.log(rsp);
                let payment = {
                    imp_uid : rsp.imp_uid,
                    merchant_uid : rsp.merchant_uid,
                    pay_method : rsp.pay_method,
                    pt_amount : 100,
                    apply_num : rsp.apply_num,
                };
                $.ajax({
                    url: "/ptUser/payment/" + member_id + "/" + trainer_id,
                    method: "post",
                    data: JSON.stringify(payment),
                    contentType: 'application/json; charset=utf-8',
                }).done(function (data) {
                    console.log(data);
                    alert("결제 완료에 성공하셨습니다");
                    let date = {
                        member_height,
                        member_weight,
                        pt_purpose,
                        pt_times,
                        pt_count,
                        pt_positiveDate,
                        pt_wantTime
                    }
                    console.log(date);
                    $.ajax({
                        type: 'post',
                        url: '/ptUser/apply/success/' + member_id + "/" + trainer_id,
                        data: JSON.stringify(date),
                        contentType: 'application/json; charset=utf-8',
                    }).done(function (data) {
                        alert("PT 신청 하셨습니다");
                        location.href = "/ptUser/view";
                        console.log(data);
                    }).fail(function (error) {
                        alert("PT 신청에 실패하였습니다");
                        console.log(error);
                    })
                }).fail(function (error) {
                    // alert(error);
                    alert("실패하였습니다");
                });
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }
            // alert(msg);
        });



}
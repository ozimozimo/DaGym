function check() {
    var member_id = $('input[name=member_id]').val();
    var trainer_id = $('#trainer_id').val();
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

// 수락한거 조회하기
function acceptList() {
    var id = $('#member_id').val();
    console.log(id+"login 한 id는");

    var data = {
        id: id // 앞에 trainer_id -> id
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/manage',
        data: data,
        contentType: 'application/json; charset=utf-8'
    }).done(function (data) {
        console.log(data);
    }).fail(function (error) {
        console.log(error);
    });
}

// 신청온거 확인하기
function showList() {

    var member_id = $('#member_id').val();
    console.log(member_id);
    var data = {
        member_id: member_id,
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/findMember',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        mkApply(data);
    }).fail(function (error) {
        console.log(error);
    })
}

// 신청내용 확인하는 곳
function mkApply(data) {
    $('.applyListDetail').empty();
    for (let i = 0; i < data.length; i++) {
        var a = data[i].member_height;
        var b = data[i].member_weight;
        var c = data[i].pt_purpose;
        var d = data[i].pt_count;
        var e = data[i].pt_positiveDate;
        var f = data[i].pt_wantTime;
        var g = data[i].member.user_name;
        var h = data[i].member.user_pn;
        var j = data[i].member.user_rrn;
        var k = data[i].member.user_gender;
        var l = data[i].id;
        var m = data[i].member.user_id;

        // hidden 값 넣기
        var p = data[i].id;
        console.log("ptUser pk는"+p);



        let content = "<tr>"
        content += "<td class='ptUserId'>" + m + "</td>"
        content += "<td class='ptUserName'>" + g + "</td>"
        content += "<td class='ptUserPn'>" + h + "</td>"
        content += "<td class='ptUserRrn'>" + j + "</td>"
        content += "<td class='ptUserGender'>" + k + "</td>"
        content += "<td class='ptUserHeight'>" + a + "</td>"
        content += "<td class='ptUserWeight'>" + b + "</td>"
        content += `<input type="hidden" value="${p}" id="pt_user_id" name="pt_user_id"> `
        content += `<td><a href='/ptUser/view/detail/${l}'>상세보기</a></td>`
        content += "<td><button type='button' class='btn-primary Accept' onclick='updateAccept(this)'>수락</button></td>"
        content += "<td><button type='button' class='btn-primary Deny' onclick='updateAccept(this)'>거절</button></td></tr>"
        $('.applyListDetail').append(content);
    }
}

// 수락, 거절 눌러서 accept_condition 바꾸기
function updateAccept(a) {
    // PTUserApplyConDto에 넘겨줄 apply_if값
    let apply_if = 0;
    // 내가 누른 버튼의 id값
    // let id = a.parentNode.parentNode.firstChild.innerText;

    let id = $('#member_id').val();

    let pt_user_id = $('#pt_user_id').val();
    console.log("pt_user_id="+pt_user_id)

    console.log("매개변수 a는" + a);
    console.log("id=" + id);

    // 내가 누른 버튼의 텍스트값
    let con = a.innerText;

    console.log("수락버튼" + con);
    // 수락이면 apply_if에 1 저장하고 아니면 2 저장
    if (con == "수락") {
        apply_if = 1;
    } else {
        apply_if = 2;
    }
    let data = {
        // PTUserApplyConDto에 넘겨줄 id랑 apply_if값
        apply_if: apply_if,
    }
    $.ajax({
        type: 'post',
        url: '/ptUser/apply/update/' + pt_user_id,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (con == "수락") {
            alert("PT신청이 수락되었습니다");
        } else if (con == "거절") {
            alert("PT신청이 거절되었습니다");
        }
        location.reload();
    }).fail(function (error) {
        console.log(error);
    })
}

$(function () {
    let role = $('#role').val();
    // let state = $('#')
    console.log(role);

    if (role == 'trainer') {
        acceptList();
        showList();
    }else{

    }

})

function a() {
    $('#selectEmail').change(function () {
        $(".category option:selected").each(function () {
            if ($(this).val() == '1') { //직접입력일 경우
                $('.category').removeId
                $('#trainer_category').append("<input type='text' id='trainer_category'>");

                $("#str_email02").val(''); //값 초기화
                $("#str_email02").attr("disabled", false); //활성화
            } else { //직접입력이 아닐경우
                $("#str_email02").val($(this).text()); //선택값 입력
                $("#str_email02").attr("disabled", true); //비활성화
            }
        });
    });

}
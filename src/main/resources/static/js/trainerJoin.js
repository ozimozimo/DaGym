$(function () {

    let zip = $('#zip');

    let uploadBtn = $('#uploadBtn');
    let uploadResult = $('#uploadResult');
    let joinBtn = $('.joinBtn');

    $('#uploadResult').on("click", ".removeBtn", function (e) {

        var target = $(this);
        var fileName = target.data("name");
        var targetDiv = $(this).closest("div");
        console.log(fileName);

        $.post('/removeFile', {fileName: fileName}, function (result) {
            console.log(result);
            if (result === true) {
                targetDiv.remove();
            }
        })
    });

    zip.on("click", GymPostCode);
    uploadBtn.on("click", Upload);
    joinBtn.on("click", trainerJoin);



});


// 도로명주소 가져오는 함수
function GymPostCode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ""; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
                extraRoadAddr +=
                    extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName;
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== "") {
                extraRoadAddr = " (" + extraRoadAddr + ")";
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if (fullRoadAddr !== "") {
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            console.log(data.zonecode);
            console.log(fullRoadAddr);

            $("[name=gym_addr]").val(data.zonecode);
            $("[name=trainer_address_normal]").val(fullRoadAddr);

            /* document.getElementById('signUpUserPostNo').value = data.zonecode; //5자리 새우편번호 사용
                        document.getElementById('signUpUserCompanyAddress').value = fullRoadAddr;
                        document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress; */
        },
    }).open();
}

//사진 업로드
function Upload() {
    var formData = new FormData();
    var inputFile = $("input[type='file']");
    var files = inputFile[0].files;

    for (var i = 0; i < files.length; i++) {
        console.log(files[i]);
        formData.append("uploadFiles", files[i]);
    }


    // 실제 업로드 부분
    $.ajax({
        url: '/uploadFile',
        processData: false,
        contentType: false,
        data: formData,
        type: "POST",
        dataType: "json",
        success: function (result) {
            console.log(result);
            showUploadedImages(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
        }
    }) // ajax end
};

function showUploadedImages(arr) {
    console.log("===================================" + arr + "======================================");
    var divArea = $(".uploadResult");

    var str = "";
    for (var i = 0; i < arr.length; i++) {
        var uuid = arr[i].thumbnailURL;
        var imgName = arr[i].imageURL;
        var fileName = arr[i].fileName;

        console.log(uuid);
        console.log(imgName);
        console.log(fileName);


        str += "<div>";
        str += "<img src='/display?fileName=" + arr[i].thumbnailURL + "'>";
        str += "<button class='removeBtn' data-name='" + arr[i].imageURL + "'>삭제</button>";
        str += "</div>"
        str += "<input type='hidden' id='uuid' name='uuid' value='"+ uuid+"'>"
        str += "<input type='hidden' id='imgName' name='imgName' value='"+ imgName+"'>"
    }
    divArea.append(str);
}

// 집코드 + 상세주소 합치기
const trainer_addr_concat = () => {
    // 네임 값
    let zip = $('#zipCode').val();
    let addr = $('#trainer_address_normal').val();

    let zipAddr = zip + addr;

    $('#trainer_address_normal').val(zipAddr);
};

// 트레이너 근무시작시간 + 종료시간
const trainer_time_concat = () => {

    let trainer_time1 = $('#trainer_time1').val();
    let trainer_time2 = $('#trainer_time2').val();

    let trainer_workTime = trainer_time1 + trainer_time2;
}

function trainerJoin() {
    var id = $('#id').val();
    let trainer_time1 = $('#trainer_time1').val();
    let trainer_time2 = $('#trainer_time2').val();
    let trainer_workTime = trainer_time1 + "~" + trainer_time2;

    var data = {
        trainer_category: $('#trainer_category').val(),
        trainer_workTime,
        uuid: $('#uuid').val(),
        imgName: $('#imgName').val(),
        fileName: $('#fileName').val(),
        trainer_address_normal: $('#trainer_address_normal').val(),
        trainer_address_detail: $('#trainer_address_detail').val(),
        trainer_instagram: $('#trainer_instagram').val(),
        trainer_kakao: $('#trainer_kakao').val(),
        trainer_content: $('#trainer_content').val()
    }


    console.log(id);
    console.log(data.trainer_type);
    console.log(data.trainer_workTime);
    console.log(data.uuid + "위에서 변환");
    console.log(data.imgName + "이미지 이름");
    console.log(data.fileName + "파일이름");
    console.log(data.trainer_address_normal + "통과");
    console.log(data.trainer_address_detail + "통과");
    console.log(data.trainer_instagram + "통과");
    console.log(data.trainer_kakao + "통과");
    console.log(data.trainer_content + "통과");

    $.ajax({
        url: '/trainer/trInfo/' + id,
        type: 'post',
        dataType: "json",
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data);
            console.log(data.fileName);
            console.log(data.imgName)
            alert('추가정보 가입에 성공하였습니다');
            location.href = "/member/login";
        },
        error: function () {
            alert('잘못된 정보입니다');
        }
    })
}

// trim = 양옆 공백 제거
function checkData() {
    let data = {
        trainer_time1: $('#trainer_time1').val().trim().length,
        trainer_time2: $('#trainer_time2').val().trim().length,
        trainer_category: $('#trainer_category').val().trim().length,
        trainer_address_normal: $('#trainer_address_normal').val().trim().length,
        trainer_address_detail: $('#trainer_address_detail').val().trim().length,
        trainer_instagram: $('#trainer_instagram').val().trim().length,
        trainer_kakao: $('#trainer_kakao').val().trim().length,
        trainer_content: $('#trainer_content').val().trim().length
    }

    let {
        trainer_time1,
        trainer_time2,
        trainer_category,
        trainer_address_normal,
        trainer_address_detail,
        trainer_instagram,
        trainer_kakao,
        trainer_content
    } = data;
    if (trainer_time1==0 || trainer_time2==0) {
        alert('시간을 입력하세요');
    } else if (trainer_address_normal == 0) {
        alert('주소를 입력하세요');
    } else if (trainer_address_detail == 0) {
        alert('주소를 입력하세요');
    } else if (trainer_instagram == 0) {
        alert('인스타그램 아이디를 입력하세요');
    } else if (trainer_kakao == 0) {
        alert('카카오톡 아이디를 입력하세요');
    } else if (trainer_content == 0) {
        alert('트레이너 경력사항을 입력하세요');
    } else {
        return true;
    }
    return false;


}
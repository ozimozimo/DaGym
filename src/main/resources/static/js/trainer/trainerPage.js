$(function () {
    let zip = $('#zip');

    let updateLoadBtn = document.getElementById('updateLoadBtn');
    let updateResult = document.getElementById('updateResult');

    updateLoadBtn.addEventListener("click", UpdateUpload);
    zip.on("click", GymPostCode);

    findPostCode();
    findTimeCode();
    category_input();
    addBtn();
    delBtn();
    $('#fileNameLabel').text($('#fileName').val());
});

function trainerUpdate() {
    var id = $('#id').val();
    let trainer_time1 = $('#trainer_time1').val();
    let trainer_time2 = $('#trainer_time2').val();
    let trainer_workTime = trainer_time1 + "~" + trainer_time2;
    let zip = $('#zipCode').val();
    let addr = $('#trainer_address_normal').val();

    let trainer_address_normal = zip + addr;

    var data = {
        trainer_category: $('#trainer_category').val(),
        trainer_workTime,
        uuid: $('#uuid').val(),
        imgName: $('#imgName').val(),
        fileName: $('#fileName').val(),
        trainer_pt_total :$('#trainer_pt_total').val(),
        trainer_address_normal,
        trainer_address_detail: $('#trainer_address_detail').val(),
        trainer_instagram: $('#trainer_instagram').val(),
        trainer_kakao: $('#trainer_kakao').val(),
        trainer_content: $('#trainer_content').val()
    }

    console.log("data는" + data)

    $.ajax({
        url: '/trainer/trUpdate/' + id,
        type: 'post',
        dataType: "json",
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log("성공 데이타" + data);
            alert('프로필 수정에 성공하였습니다');
            location.href = "/";
        },
        error: function () {
            alert('잘못된 정보입니다');
        }
    })

}

function priceCode() {
    let fp = $('#final_price').val();
    let count = fp.substring(0, 1);
    let price = fp.substring()
}

function findTimeCode() {
    // 기본주소 값 가져오기
    let wt = $('#trainer_workTime').val();
    let time1 = wt.substring(0, 5);
    let time2 = wt.substring(6, 12);
    console.log(time1);
    console.log(time2);
    // zip 코드 삽입
    $('#trainer_time1').attr('value', time1);
    $('#trainer_time2').attr('value', time2);
}

// 주소 나누기
function findPostCode() {
    // 기본주소 값 가져오기
    let adr = $('#trainer_address_normal').val();
    let zip = adr.substring(0, 5);
    let trainer_address_normal = adr.substring(5)

    // zip 코드 삽입
    $('#zipCode').attr('value', zip);
    $('#trainer_address_normal').attr('value', trainer_address_normal);
}

function showUploadedImages(arr) {
    console.log(arr);
    $('.updateResult').addClass('hidden');
    $('#updateLoadBtn').addClass('hidden');
    var divArea = $(".updateResult2");

    var str = "";

    for (var i = 0; i < arr.length; i++) {
        var uuid = arr[i].thumbnailURL;
        var imgName = arr[i].imageURL;
        var fileName = arr[i].fileName;
        str += "<div>";
        str += "<img src='/display?fileName=" + arr[i].thumbnailURL + "'/>";
        str += "<button class='removeBtn' data-name='" + arr[i].imageURL + "'>삭제</button>";
        str += "<input type='hidden' id='uuid' name='uuid' value='" + uuid + "'>"
        str += "<input type='hidden' id='imgName' name='imgName' value='" + imgName + "'>"
        str += "<input type='hidden' id='fileName' name='fileName' value='" + fileName + "'>"
        str += "</div>"
    }
    divArea.append(str);
}

$('.updateResult2').on("click", ".removeBtn", function () {
    var target = $(this);
    var fileName = target.data("name");
    var targetDiv = $(this).closest("div");

    $('.updateResult').removeClass('hidden');
    $('#updateLoadBtn').removeClass('hidden');

    console.log(fileName);

    $.post('/removeFile', {fileName: fileName}, function (result) {
        console.log(result);
        if (result === true) {
            targetDiv.remove();
        }
    })
})

function UpdateUpload() {

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

function category_input() {
    if ($("#trainer_category option:selected").val() == '기타') {
        $('.input_category').show();
    }
    $('.category').change(function () {
        $(".category option:selected").each(function () {
            if ($(this).val() == '기타') { //직접입력일 경우
                $('.category').attr('id', ''); //id 초기화
                $('.input_category').show();
                $('.input_category').attr('id', 'trainer_category');
            } else { //직접입력이 아닐경우 $("#str_email02").val($(this).text()); //선택값 입력
                // $('.trainer_category').attr('value','');
                $('.category').attr('id', 'trainer_category');
                $('.input_category').hide();
                $('.input_category').attr('id', '');
            }
        });
    });


}

function addBtn() {
    if ($('#trainer_content').val() != '') {
        let content = $('#trainer_content').val().split('!');
        console.log(content);
        content.forEach((value, index) => {
            if (value != '') {
                let contents = value;
                let htmlContents = `<div class="con"><span>${contents}</span><button type="button" class="delBtn">삭제</button></div>`
                $('.showContent').append(htmlContents);
                // delBtn();
            }
        })
    }


    $('.addBtn').on("click", function () {
        if ($('#trainer_contents').val() == '') {
            alert('입력 후 추가 버튼을 눌러주세요');
            return false;
        } else {
            let contents = $('#trainer_contents').val();
            let htmlContents = `<div class="con">${contents}<button type="button" class="delBtn">삭제</button></div>`

            $('.showContent').append(htmlContents);

            contents = $('#trainer_contents').val() + "!";

            let content = $('#trainer_content').val() + contents;
            $('#trainer_contents').val('');
            $('#trainer_content').val(content);
            delBtn();
        }
    });

}

function delBtn(){
    $('.delBtn').on('click', function () {
        let content = $('#trainer_content').val();
        let span = $(this).siblings('span').text()+"!";
        console.log(span);
        let replaceContent = content.replace(span,'');
        console.log(replaceContent);
        $('#trainer_content').val(replaceContent);
        $(this).parent().remove();

    })
}
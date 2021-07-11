$(function () {

    let zip = $('#zip');

    let uploadBtn = $('#uploadBtn');
    let updateResult = $('.updateResult');
    let joinBtn = $('.joinBtn');
    addBtn();
    delBtn();
    zip.on("click", GymPostCode);
    uploadBtn.on("click", Upload);
    joinBtn.on("click", trainerJoin);

    $(".time").flatpickr({
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
    });
    cal();
    init();
    category_input();
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
    $('.noImg').hide();
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
    $('.updateResult').addClass('hidden');
    $('#uploadBtn').addClass('hidden');
    var divArea = $(".updateResult2");

    var str = "";
    var removeBtn = '';
    for (var i = 0; i < arr.length; i++) {
        var uuid = arr[i].thumbnailURL;
        var imgName = arr[i].imageURL;
        var fileName = arr[i].fileName;

        console.log(uuid);
        console.log(imgName);
        console.log(fileName);

        str += "<div>";
        str += `<img src='/display?fileName=${arr[i].thumbnailURL}' width="200px" height="200px">`;
        str += "<input type='hidden' id='uuid' name='uuid' value='" + uuid + "'>"
        str += "<input type='hidden' id='imgName' name='imgName' value='" + imgName + "'>"
        str += "<input type='hidden' id='fileName' name='fileName' value='" + fileName + "'>"
        str += "</div>"

        removeBtn += "<button class='removeBtn btn btn-primary' data-name='" + arr[i].imageURL + "'>삭제</button>";

    }
    divArea.append(str);
    $('.btns').append(removeBtn);

}
$('.updateResult2').on("click", ".removeBtn", function (e) {
    var target = $(this);
    var fileName = target.data("name");
    var targetDiv = $(this).closest("div");

    $('.updateResult').removeClass('hidden');
    $('#uploadBtn').removeClass('hidden');
    console.log(fileName);

    $.post('/removeFile', {fileName: fileName}, function (result) {
        console.log(result);
        if (result === true) {
            targetDiv.remove();
        }
    })
});

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

// 주소 합체하기
const addr_concat = () => {
    // 네임 값
    let zip = $('#zipCode').val();
    let addr = $('#trainer_address_normal').val();

    let zipAddr = zip + addr;

    $('#address_normal').val(zipAddr);
};

function trainerJoin() {
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
        trainer_gymName: $('#trainer_gymName').val(),
        trainer_pt_total: $('#trainer_pt_total').val(),
        trainer_pt_discount : $('#discount_price').val(),
        trainer_pt_AddCount : $('#discount_pt_count').val(),
        trainer_address_normal,
        trainer_address_detail: $('#trainer_address_detail').val(),
        trainer_instagram: $('#trainer_instagram').val(),
        trainer_kakao: $('#trainer_kakao').val(),
        trainer_content: $('#trainer_content').val()
    }
    console.log(data.trainer_pt_discount);
    console.log(data.trainer_pt_AddCount);

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
    console.log(data.trainer_pt_total + "pt금액은?");
    addr_concat();
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
    if (trainer_time1 == 0 || trainer_time2 == 0) {
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


function addBtn() {
    if ($('#trainer_content').val() != '') {
        let content = $('#trainer_content').val().split('!');
        console.log(content);
        content.forEach((value, index) => {
            if (value != '') {
                let contents = value;
                let htmlContents = `<div class="con col-sm-12 mt-3"><i class="fas fa-check"></i><span> ${contents}</span><button type="button" class="delBtn btn btn-secondary btn-sm float-md-right">삭제</button></div>`
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
            let htmlContents = `<div class="con col-sm-12 mt-3"><i class="fas fa-check"></i><span> ${contents}</span><button type="button" class="delBtn btn btn-secondary btn-sm float-md-right">삭제</button></div>`

            $('.showContent').append(htmlContents);

            contents = $('#trainer_contents').val() + "!";

            let content = $('#trainer_content').val() + contents;
            $('#trainer_contents').val('');
            $('#trainer_content').val(content);
            delBtn();
        }
    });

}

function delBtn() {
    $('.delBtn').on('click', function () {
        console.log('무야호');
        let content = $('#trainer_content').val();
        let span = $(this).siblings('span').text() + "!";
        console.log(span);
        let replaceContent = content.replace(span, '');
        console.log(replaceContent);
        $('#trainer_content').val(replaceContent);
        $(this).parent().remove();

    })
}
function cal(){
    let count = 10;

    let pt_price = $('#pt_price').val();
    let pt_discount = $('#discount_price').val();
    let pt_count = $('#discount_pt_count').val();
    let final_price = $('#final_price').val();

    pt_price = parseInt(pt_price);
    pt_discount = parseInt(pt_discount);
    pt_count = parseInt(pt_count);
    final_price = parseInt(final_price);

    $("#discount").click(function () {
        let pt_price = $('#pt_price').val();
        let pt_discount = $('#discount_price').val();
        let pt_count = $('#discount_pt_count').val();


        // 형변환
        pt_price = parseInt(pt_price);
        pt_discount = parseInt(pt_discount);
        pt_count = parseInt(pt_count);

        // 할인금액
        let dis = pt_price - pt_discount * 0.01 * pt_price;
        // 추가 카운트
        let final_count = pt_count + count;

        // 최종값
        $('#final_price').val(final_count + "회 / " + dis);
        var trainer_pt_total = $('#final_price').val();

        $('#trainer_pt_total').val(trainer_pt_total);

    })

}


function init(){
    //DOM elements
    const DOMstrings = {
        stepsBtnClass: "multisteps-form__progress-btn",
        stepsBtns: document.querySelectorAll(`.multisteps-form__progress-btn`),
        stepsBar: document.querySelector(".multisteps-form__progress"),
        stepsForm: document.querySelector(".multisteps-form__form"),
        stepsFormTextareas: document.querySelectorAll(".multisteps-form__textarea"),
        stepFormPanelClass: "multisteps-form__panel",
        stepFormPanels: document.querySelectorAll(".multisteps-form__panel"),
        stepPrevBtnClass: "js-btn-prev",
        stepNextBtnClass: "js-btn-next",
    };

//remove class from a set of items
    const removeClasses = (elemSet, className) => {
        elemSet.forEach((elem) => {
            elem.classList.remove(className);
        });
    };

//return exect parent node of the element
    const findParent = (elem, parentClass) => {
        let currentNode = elem;

        while (!currentNode.classList.contains(parentClass)) {
            currentNode = currentNode.parentNode;
        }

        return currentNode;
    };

//get active button step number
    const getActiveStep = (elem) => {
        return Array.from(DOMstrings.stepsBtns).indexOf(elem);
    };

//set all steps before clicked (and clicked too) to active
    const setActiveStep = (activeStepNum) => {
        //remove active state from all the state
        removeClasses(DOMstrings.stepsBtns, "js-active");

        //set picked items to active
        DOMstrings.stepsBtns.forEach((elem, index) => {
            if (index <= activeStepNum) {
                elem.classList.add("js-active");
            }
        });
    };

//get active panel
    const getActivePanel = () => {
        let activePanel;

        DOMstrings.stepFormPanels.forEach((elem) => {
            if (elem.classList.contains("js-active")) {
                activePanel = elem;
            }
        });

        return activePanel;
    };

//open active panel (and close unactive panels)
    const setActivePanel = (activePanelNum) => {
        //remove active class from all the panels
        removeClasses(DOMstrings.stepFormPanels, "js-active");

        //show active panel
        DOMstrings.stepFormPanels.forEach((elem, index) => {
            if (index === activePanelNum) {
                elem.classList.add("js-active");

                setFormHeight(elem);
            }
        });
    };

//set form height equal to current panel height
    const formHeight = (activePanel) => {
        const activePanelHeight = activePanel.offsetHeight;

        DOMstrings.stepsForm.style.height = `${activePanelHeight}px`;
    };

    const setFormHeight = () => {
        const activePanel = getActivePanel();

        formHeight(activePanel);
    };

//STEPS BAR CLICK FUNCTION
    DOMstrings.stepsBar.addEventListener("click", (e) => {
        //check if click target is a step button
        const eventTarget = e.target;

        if (!eventTarget.classList.contains(`${DOMstrings.stepsBtnClass}`)) {
            return;
        }

        //get active button step number
        const activeStep = getActiveStep(eventTarget);

        //set all steps before clicked (and clicked too) to active
        setActiveStep(activeStep);

        //open active panel
        setActivePanel(activeStep);
    });

//PREV/NEXT BTNS CLICK
    DOMstrings.stepsForm.addEventListener("click", (e) => {
        const eventTarget = e.target;

        //check if we clicked on `PREV` or NEXT` buttons
        if (
            !(
                eventTarget.classList.contains(`${DOMstrings.stepPrevBtnClass}`) ||
                eventTarget.classList.contains(`${DOMstrings.stepNextBtnClass}`)
            )
        ) {
            return;
        }

        //find active panel
        const activePanel = findParent(
            eventTarget,
            `${DOMstrings.stepFormPanelClass}`
        );

        let activePanelNum = Array.from(DOMstrings.stepFormPanels).indexOf(
            activePanel
        );

        //set active step and active panel onclick
        if (eventTarget.classList.contains(`${DOMstrings.stepPrevBtnClass}`)) {
            activePanelNum--;
        } else {
            activePanelNum++;
        }

        setActiveStep(activePanelNum);
        setActivePanel(activePanelNum);
    });

//SETTING PROPER FORM HEIGHT ONLOAD
    window.addEventListener("load", setFormHeight, false);

//SETTING PROPER FORM HEIGHT ONRESIZE
    window.addEventListener("resize", setFormHeight, false);

//changing animation via animation select !!!YOU DON'T NEED THIS CODE (if you want to change animation type, just change form panels data-attr)

// const setAnimationType = (newType) => {
//   DOMstrings.stepFormPanels.forEach((elem) => {
//     elem.dataset.animation = newType;
//   });
// };

// //selector onchange - changing animation
// const animationSelect = document.querySelector(".pick-animation__select");

// animationSelect.addEventListener("change", () => {
//   const newAnimationType = animationSelect.value;

//   setAnimationType(newAnimationType);
// });

    DOMstrings.stepFormPanels.forEach((elem) => {
        elem.dataset.animation = "slideHorz";
    });

}

function category_input() {
    // if ($("#trainer_category option:selected").val() == '기타') {
    // if ($(".real_category").val() != 'PT' && $(".real_category").val() != '재활' &&
    //     $(".real_category").val() != '대회') {
    //     $('.category').attr('id', ''); //id 초기화
    //     $('.input_category').show();
    //     $('.input_category').attr('id', 'trainer_category');
    // }
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
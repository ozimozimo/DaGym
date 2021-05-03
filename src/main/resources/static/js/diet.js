o// 함수 선언 부분
$(function () {
    $('.dietDelete').on('click', dietDelete);
    $('.dietSearch').on('click', dietSearch);
    $('.dietWindow').on('click', dietWindow);
    $('.dietClose').on('click', dietClose);
    $('.trainerCommentAdd').on('click', trainerCommentAdd);
})

// 식단 검색
function dietSearch() {
    $('.dietSearch').click(function () {
        var diet = $('#diet').val();
        console.log(diet);
        if (diet.length == 0) {
            alert("음식 이름을 입력하세요");
            $('#diet').focus();
            return false;
        }
        $.ajax({
            dataType: 'json',
            url: 'http://openapi.foodsafetykorea.go.kr/api/454c90aa80ae4a1ca314/I2790/json/1/1000/DESC_KOR=' + diet,
            success: function (result) {
                alert('검색완료');
                dietResult(result);
                dietAdd();
            },
            error: function (error) {
                alert("fail");
                console.log(JSON.stringify(error));
            }
        });
    });
}

// 식단 검색 결과
function dietResult(result) {
    $('.dietList').empty();
    for (let i = 0; i < result.I2790.row.length; i++) {
        var str = result.I2790.row[i];
        // 결과값 시작
        let content = "<tr>"
        content += "<td class='diet_id 'style='display: none'>" + str + "</td>"
        // 음식이름
        content += "<td class='diet_name' id='diet_name'>" + str['DESC_KOR'] + "</td>"
        // 칼로리
        content += "<td class='diet_kcal' id='diet_kcal'>" + str['NUTR_CONT1'] + "</td>"
        // 탄수화물
        content += "<td class='diet_carbo' id='diet_carbo'>" + str['NUTR_CONT2'] + "</td>"
        // 단백질
        content += "<td class='diet_protein' id='diet_protein'>" + str['NUTR_CONT3'] + "</td>"
        // 지방
        content += "<td class='diet_fat' id='diet_fat'>" + str['NUTR_CONT4'] + "</td>"
        // 시간
        content += "<td>" +
            "<select name='diet_time'>" +
            "<option value='아침'>아침</option>" +
            "<option value='점심'>점심</option>" +
            "<option value='저녁'>저녁</option>" +
            "<option value='간식'>간식</option>" +
            "</select>" +
            "</td>"
        // 추가버튼
        content += "<td><button type='button' class='dietAdd'>추가하기</td>"
        content += "</tr>"
        // diet.html tbody에 삽입
        $('.dietList').append(content);
    }
}
// 식단 추가
function dietAdd() {
    $('.dietAdd').on("click", function (e) {
        var id = $('.member_id').val();
        var data = {
            diet_member_id: $('.diet_member_id').val(),
            diet_name: $(this).parent().siblings("td.diet_name").text(),
            diet_kcal: $(this).parent().siblings("td.diet_kcal").text() || 0,
            diet_carbo: $(this).parent().siblings("td.diet_carbo").text() || 0,
            diet_protein: $(this).parent().siblings("td.diet_protein").text() || 0,
            diet_fat: $(this).parent().siblings("td.diet_fat").text() || 0,
            diet_time: $(this).parent().siblings().children("select[name=diet_time]").val(),
        }
        console.log(id);
        $.ajax({
            type: 'post',
            url: '/diet/save/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            console.log(data)
            alert('식단이 추가되었습니다');
        }).fail(function (error) {
            alert(error);
            console.log(JSON.stringify(error));
        })
    });
}

// 식단 삭제
function dietDelete() {
    // td 안에 버튼 들어가있어서 parent()씀
    var id = $(this).parent().siblings('.diet_id').text();
    $.ajax({
        type: 'post',
        url: '/diet/delete/' + id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('식단이 삭제되었습니다');
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

// 검색창
function dietWindow() {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);
    var option = 'status=no, height=700, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    // var url = "http://localhost:8090/diet/search";
    // window.open(url, 'dietWindow', option);

    var url = "http://140.238.25.78:8090/diet/search";
    window.open(url, 'dietWindow', option);
}

// 선택완료
function dietClose() {
    window.close();
    opener.document.location.reload();
}

// 얘는 왜 여기 있는가
function trainerCommentAdd() {
    $('.trainerCommentAdd').click(function () {
        let content = $('.trainerCommentWrite').val();
        $('.trainerCommentList').html(content);
    })
}
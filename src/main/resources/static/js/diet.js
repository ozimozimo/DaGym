// 식단 검색
function dietSearch() {
    $('.dietSearch').click(function () {
        var diet = $('#diet').val();
        console.log(diet);
        if(diet.length == 0) {
            alert("음식 이름을 입력하세요");
            $('#diet').focus();
            return false;
        }
        $.ajax({
            dataType: 'json',
            url: 'http://openapi.foodsafetykorea.go.kr/api/454c90aa80ae4a1ca314/I2790/json/1/1000/DESC_KOR=' + diet,
            success: function (result) {
                dietResult(result);
                // 식단 추가
                $('.btnAdd').on("click", function (e) {
                    var id = $('#member_id').val();
                    var data = {
                        diet_member_id: $('#diet_member_id').val(),
                        diet_name: $(this).siblings("td.diet_name").text(),
                        diet_kcal: $(this).siblings("td.diet_kcal").text(),
                        diet_carbo: $(this).siblings("td.diet_carbo").text(),
                        diet_protein: $(this).siblings("td.diet_protein").text(),
                        diet_fat: $(this).siblings("td.diet_fat").text(),
                        diet_time: $(this).siblings().children("select[name=diet_time]").val()
                    }
                    $.ajax({
                        type: 'post',
                        url: '/diet/save/'+id,
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(data)
                    }).done(function (data) {
                        console.log(data)
                        alert('식단이 추가되었습니다');
                    }).fail(function (error) {
                        alert(error);
                    })
                });
            },
            error: function () {
                alert("fail");
            }
        });
    });
}

// 식단 검색 결과
function dietResult(result) {
    $('#dietList').empty();
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
            "</select>" +
            "</td>"
        // 추가버튼
        content += "<td class='btnAdd'><button type='button'>추가하기</td>"
        content += "</tr>"
        // diet.html tbody에 삽입
        $('#dietList').append(content);
    }
}

// 식단 삭제
function dietDelete() {
    // td 안에 버튼 들어가있어서 parent()씀
        var id = $(this).parent().siblings('.diet_id').text();
        $.ajax({
            type: 'post',
            url: '/diet/delete/'+id,
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
    var url = "http://localhost:8090/diet/search";
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY= (window.screen.height /2) - (700 / 2);
    var option= 'status=no, height=700, width=800, left='+ popupX + ', top='+ popupY + ', screenX='+ popupX + ', screenY= '+ popupY;
    window.open(url, 'dietWindow', option);

    // var url = "http://140.238.25.78:8090/diet/list";
    // window.open("http://140.238.25.78:8090/diet/list", 'dietWindow', option);
}

function dietClose() {
    window.close();
    opener.document.location.reload();
}
$(function (){
    $('.dietDelete').on('click', dietDelete);
    $('.dietSearch').on('click', dietSearch);
    $('.dietWindow').on('click', dietWindow);
    $('.dietClose').on('click', dietClose);
})
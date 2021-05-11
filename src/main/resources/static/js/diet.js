// 함수 선언 부분
$(function () {
    $('.dietSearch').on('click', dietSearch);
    $('.dietWindow').on('click', dietWindow);
    $('.dietClose').on('click', dietClose);
    todayData();
    showDayAllResult();
})

// 오늘 날짜 가져오기 (2021-04-30 형태)
var date = new Date();
date = getFormatDate(date);

function getFormatDate(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    var day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

// 식단 검색
function dietSearch() {
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
        // list.html tbody에 삽입
        $('.dietList').append(content);
    }
}

// 식단 추가
function dietAdd() {
    $('.dietAdd').on('click', function (e) {
        splitDate();
        console.log($('.date').val());
        var id = $('.member_id').val();
        var data = {
            diet_member_id: $('.diet_member_id').val(),
            diet_name: $(this).parent().siblings("td.diet_name").text(),
            diet_kcal: $(this).parent().siblings("td.diet_kcal").text() || 0,
            diet_carbo: $(this).parent().siblings("td.diet_carbo").text() || 0,
            diet_protein: $(this).parent().siblings("td.diet_protein").text() || 0,
            diet_fat: $(this).parent().siblings("td.diet_fat").text() || 0,
            diet_time: $(this).parent().siblings().children("select[name=diet_time]").val(),
            diet_date: $('.date').val(),
        }
        $.ajax({
            type: 'post',
            url: '/diet/save/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            alert('식단이 추가되었습니다');
            console.log(data)
        }).fail(function (error) {
            alert(error);
            console.log(JSON.stringify(error));
        })
    })
}

// 검색창
function dietWindow() {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);
    var date = $('.date').val();
    var option = 'status=no, height=700, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    var url = "http://localhost:8090/diet/search" + "?" + date;
    window.open(url, 'dietWindow', option);
    console.log($('.date').val());
    // var url = "http://140.238.25.78:8090/diet/search";
    // window.open(url, 'dietWindow', option);
}

// 선택완료
function dietClose() {
    window.close();
    opener.document.location.reload();
}

function dietDelete(a) {
    let id = a.parentNode.parentNode.firstChild.innerText;
    console.log(id);
    $.ajax({
        type: 'post',
        url: '/diet/delete/' + id,
        contentType: 'application/json; charset=utf-8',
    }).done(function () {
        alert('식단이 삭제되었습니다');
        location.reload();
    }).fail(function (error) {
        alert(error);
        console.log(JSON.stringify(error));
    })
}

function mkDiet(result) {
    // 결과 출력
    let id = $('.loginId').text();
    for (let i = 0; i < result.length; i++) {
        var a = result[i].diet_name;
        var b = result[i].diet_kcal;
        var c = result[i].diet_carbo;
        var d = result[i].diet_protein;
        var e = result[i].diet_fat;
        var f = result[i].diet_time;
        var g = result[i].diet_id;
        var h = result[i].diet_member_id;
        var j = result[i].diet_date;

        function input(time, list, value) {
            if (f == time) {
                let content = "<tr class=" + list + ">"
                content += "<td class='diet_id' style='display: none'>" + g + "</td>"
                content += "<td class='diet_member_id' style='display: none'>" + h + "</td>"
                content += "<td class='diet_name'>" + a + "</td>"
                content += "<td class='diet_kcal'>" + b + "</td>"
                content += "<td class='diet_carbo'>" + c + "</td>"
                content += "<td class='diet_protein'>" + d + "</td>"
                content += "<td class='diet_fat'>" + e + "</td>"
                content += "<td class='diet_date' style='display: none'>" + j + "</td>"
                if (h == id)
                    content += "<td><button type='button' class='diet_Delete' onclick='dietDelete(this)'>삭제</button>" + "</td>"
                content += "</tr>"
                $('.diet').children(value).children('.dietList').append(content);
            }
        }

        input('아침', 'breakfastList', '.breakfast');
        input('점심', 'lunchList', '.lunch');
        input('저녁', 'dinnerList', '.dinner');
        input('간식', 'extraList', '.extra');
    }

    // 시간별 합계
    function mkEat(time, classEat, classKcal, classCarbo, classProtein, classFat) {
        let content = "<tr class=" + classEat + ">"
        content += "<td>영양소 합계</td>"
        content += "<td class=" + classKcal + "></td>"
        content += "<td class=" + classCarbo + "></td>"
        content += "<td class=" + classProtein + "></td>"
        content += "<td class=" + classFat + "></td>"
        content += "</tr>"
        $(time).children('.dietList').append(content);
    }

    mkEat('.breakfast', 'breakfastEat', 'breakfast_kcal', 'breakfast_carbo', 'breakfast_protein', 'breakfast_fat');
    mkEat('.lunch', 'lunchEat', 'lunch_kcal', 'lunch_carbo', 'lunch_protein', 'lunch_fat');
    mkEat('.dinner', 'dinnerEat', 'dinner_kcal', 'dinner_carbo', 'dinner_protein', 'dinner_fat');
    mkEat('.extra', 'extraEat', 'extra_kcal', 'extra_carbo', 'extra_protein', 'extra_fat');

    // 영양소별 합계
    function sumNutr(time, timeList, nutr, timeEat, timeNutr) {
        let sum = 0;
        for (let i = 0; i < $(time).children('.dietList').children(timeList).find(nutr).length; i++) {
            sum += parseFloat($(time).children('.dietList').children(timeList).children(nutr).eq(`${i}`).text());
        }
        $(time).children('.dietList').children(timeEat).children(timeNutr).append(sum.toFixed(1))
    }

    sumNutr('.breakfast', '.breakfastList', '.diet_kcal', '.breakfastEat', '.breakfast_kcal')
    sumNutr('.breakfast', '.breakfastList', '.diet_carbo', '.breakfastEat', '.breakfast_carbo')
    sumNutr('.breakfast', '.breakfastList', '.diet_protein', '.breakfastEat', '.breakfast_protein')
    sumNutr('.breakfast', '.breakfastList', '.diet_fat', '.breakfastEat', '.breakfast_fat')

    sumNutr('.lunch', '.lunchList', '.diet_kcal', '.lunchEat', '.lunch_kcal')
    sumNutr('.lunch', '.lunchList', '.diet_carbo', '.lunchEat', '.lunch_carbo')
    sumNutr('.lunch', '.lunchList', '.diet_protein', '.lunchEat', '.lunch_protein')
    sumNutr('.lunch', '.lunchList', '.diet_fat', '.lunchEat', '.lunch_fat')

    sumNutr('.dinner', '.dinnerList', '.diet_kcal', '.dinnerEat', '.dinner_kcal')
    sumNutr('.dinner', '.dinnerList', '.diet_carbo', '.dinnerEat', '.dinner_carbo')
    sumNutr('.dinner', '.dinnerList', '.diet_protein', '.dinnerEat', '.dinner_protein')
    sumNutr('.dinner', '.dinnerList', '.diet_fat', '.dinnerEat', '.dinner_fat')

    sumNutr('.extra', '.extraList', '.diet_kcal', '.extraEat', '.extra_kcal')
    sumNutr('.extra', '.extraList', '.diet_carbo', '.extraEat', '.extra_carbo')
    sumNutr('.extra', '.extraList', '.diet_protein', '.extraEat', '.extra_protein')
    sumNutr('.extra', '.extraList', '.diet_fat', '.extraEat', '.extra_fat')
}

// 오늘 날짜 데이터
function todayData() {
    var today = date;
    var id = $('.loginId').text();
    var data = {
        id: id,
        diet_date: today
    }
    $.ajax({
        type: 'get',
        url: '/diet/dietToday/',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        mkDiet(result);
    }).fail(function () {
        alert('문제가 있다');
    })
}

function noData() {
    let content = "<tr>"
    content += "<td colspan = 6>"
    content += "데이터가 없습니다"
    content += "</td></tr>"
    $('.dietList').append(content);
}

function splitDate() {
    // 캘린더 Input JS
    var link = document.location.href.split("?");
    let date = link[1];
    $(".date").val(date);
}

function showDayAllResult(){
    var k = [".breakfast_kcal", ".lunch_kcal", ".dinner_kcal", ".extra_kcal"];
    var c = [".breakfast_carbo", ".lunch_carbo", ".dinner_carbo", ".extra_carbo"];
    var p = [".breakfast_protein", ".lunch_protein", ".dinner_protein", ".extra_protein"];
    var f = [".breakfast_fat", ".lunch_fat", ".dinner_fat", ".extra_fat"];

    nutr(k, '.dailyKcal');
    nutr(c, '.dailyCarbo');
    nutr(p, '.dailyProtein');
    nutr(f, '.dailyFat');

    console.log($('.dailyKcal').text())
    console.log($('.breakfast_kcal').text())
    console.log($('.lunch_kcal').text())
    console.log($('.dinner_kcal').text())
    console.log($('.extra_kcal').text())
}

// 일일 합계
function nutr(arr, where) {
    let sum = 0;
    arr.forEach(function (n) {
        console.log($('.dietList').find(".breakfast_kcal").text());
        let value = parseFloat($('.dietList').find(n).text());
    console.log('밸류 : ', value);
        sum += value;
    })
    console.log('합 : ', sum);
    $(where).append(sum.toFixed(1));
}

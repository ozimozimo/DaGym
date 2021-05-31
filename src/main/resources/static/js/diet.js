$(function () {
    changeId();
    $('.dietSearch').on('click', dietSearch);
    $('.dietWindow').on('click', dietWindow);
    $('.dietClose').on('click', function () {
        window.close();
        opener.document.location.reload();
    });

    // 오늘 날짜랑 선택한 날짜가 같다면
    if ($('.date').val() == date) {
        console.log('오늘');
        todayData();
    }
})
function changeId(){
    let location = window.location.href;
    let arr = location.split("=");
    $('.member_id').val(arr[1]);
    console.log('arr : ' + arr[1]);
}


// 오늘 날짜 가져오기 (2021-04-30 형태)
var date = new Date();
date = getFormatDate(date);
let myChart;

// 날짜 포맷 변경
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
        // 1회 제공량
        content += "<td class='diet_serve' id='diet_serve'>" + str['SERVING_SIZE'] + "</td>"
        // 칼로리
        content += "<td class='diet_kcal' id='diet_kcal'>" + str['NUTR_CONT1'] + "</td>"
        // 탄수화물
        content += "<td class='diet_carbo' id='diet_carbo'>" + str['NUTR_CONT2'] + "</td>"
        // 단백질
        content += "<td class='diet_protein' id='diet_protein'>" + str['NUTR_CONT3'] + "</td>"
        // 지방
        content += "<td class='diet_fat' id='diet_fat'>" + str['NUTR_CONT4'] + "</td>"
        // 내가 먹은 양 입력하기
        content += "<td>" + "<input type='text' class='eat_rate'>" + "</td>"
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
        // 내가 먹은 양
        var eat_rate = $(this).parent().siblings().children('.eat_rate').val();
        // 기존 제공량
        var default_eat_rate = $(this).parent().siblings('.diet_serve').text();
        var divide_eat_rate = default_eat_rate / eat_rate;
        var eat_kcal = $(this).parent().siblings("td.diet_kcal").text()/divide_eat_rate;
        var eat_carbo = $(this).parent().siblings("td.diet_carbo").text()/divide_eat_rate;
        var eat_protein = $(this).parent().siblings("td.diet_protein").text()/divide_eat_rate;
        var eat_fat = $(this).parent().siblings("td.diet_fat").text()/divide_eat_rate;
        var data = {
            diet_member_id: $('.diet_member_id').val(),
            diet_name: $(this).parent().siblings("td.diet_name").text(),
            eat_rate: eat_rate || default_eat_rate,
            diet_kcal: eat_kcal || $(this).parent().siblings("td.diet_kcal").text() || 0,
            diet_carbo: eat_carbo || $(this).parent().siblings("td.diet_carbo").text() || 0,
            diet_protein: eat_protein || $(this).parent().siblings("td.diet_protein").text() || 0,
            diet_fat: eat_fat || $(this).parent().siblings("td.diet_fat").text() || 0,
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
    var option = 'status=no, height=700, width=1560, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    var url = "http://localhost:8090"
    // var url = "http://140.238.25.78:8090";
    url += "/diet/search" + "?" + date;
    window.open(url, 'dietWindow', option);
    console.log($('.date').val());
}

// 식단 삭제
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
    for (let i = 0; i < result.length; i++) {
        let obj = {
            'a': result[i].diet_name,
            'b': result[i].diet_kcal,
            'c': result[i].diet_carbo,
            'd': result[i].diet_protein,
            'e': result[i].diet_fat,
            'f': result[i].diet_time,
            'g': result[i].diet_id,
            'h': result[i].diet_member_id,
            'j': result[i].diet_date,
            'k': result[i].eat_rate
        }
        input(obj);
    }
    showSumNutr();
}

function showSumNutr(){
    sumNutr('.breakfastList', '.eat_rate', '.breakfast_eat_rate');
    sumNutr('.breakfastList', '.diet_kcal', '.breakfast_kcal');
    sumNutr('.breakfastList', '.diet_carbo', '.breakfast_carbo');
    sumNutr('.breakfastList', '.diet_protein', '.breakfast_protein');
    sumNutr('.breakfastList', '.diet_fat', '.breakfast_fat');

    sumNutr('.lunchList', '.eat_rate', '.lunch_eat_rate');
    sumNutr('.lunchList', '.diet_kcal', '.lunch_kcal');
    sumNutr('.lunchList', '.diet_carbo', '.lunch_carbo');
    sumNutr('.lunchList', '.diet_protein', '.lunch_protein');
    sumNutr('.lunchList', '.diet_fat', '.lunch_fat');

    sumNutr('.dinnerList', '.eat_rate', '.dinner_eat_rate');
    sumNutr('.dinnerList', '.diet_kcal', '.dinner_kcal');
    sumNutr('.dinnerList', '.diet_carbo', '.dinner_carbo');
    sumNutr('.dinnerList', '.diet_protein', '.dinner_protein');
    sumNutr('.dinnerList', '.diet_fat', '.dinner_fat');

    sumNutr('.extraList', '.eat_rate', '.extra_eat_rate');
    sumNutr('.extraList', '.diet_kcal', '.extra_kcal');
    sumNutr('.extraList', '.diet_carbo', '.extra_carbo');
    sumNutr('.extraList', '.diet_protein', '.extra_protein');
    sumNutr('.extraList', '.diet_fat', '.extra_fat');

    chartCreate();

}
// 영양소별 합계
function sumNutr(timeList, nutr, timeNutr) {
    let sum = 0;
    for (let i = 0; i < $(timeList).find(nutr).length; i++) {
        let value = parseInt($(timeList).children(nutr).eq(`${i}`).text());
        sum += value;
    }
    $(timeNutr).text(sum);
    showDayAllResult();

}

// 값 넣기
function input(obj) {

    function eatTime(time, list, value, eat) {
        this.time = time;
        this.list = list;
        this.value = value;
        this.eat = eat;
    }

    let breakfast = new eatTime('아침', 'breakfastList', '.breakfast', '.breakfastEat');
    let lunch = new eatTime('점심', 'lunchList', '.lunch', '.lunchEat');
    let dinnerList = new eatTime('저녁', 'dinnerList', '.dinner', '.dinnerEat');
    let extra = new eatTime('간식', 'extraList', '.extra', '.extraEat');
    let timeObj;
    let id = $('.loginId').text();

    switch (obj.f) {
        case "아침" :
            timeObj = breakfast;
            break;
        case "점심" :
            timeObj = lunch;
            break;
        case "저녁" :
            timeObj = dinnerList;
            break;
        case "간식" :
            timeObj = extra;
            break;
    }

    let content = "<tr class=" + timeObj.list + ">"
    content += "<td class='diet_id' style='display: none'>" + obj.g + "</td>"
    content += "<td class='diet_member_id' style='display: none'>" + obj.h + "</td>"
    content += `<td class='diet_name' title='${obj.a}'>` + obj.a + "</td>"
    content += "<td class='eat_rate'>" + obj.k + "</td>"
    content += "<td class='diet_kcal'>" + obj.b + "</td>"
    content += "<td class='diet_carbo'>" + obj.c + "</td>"
    content += "<td class='diet_protein'>" + obj.d + "</td>"
    content += "<td class='diet_fat'>" + obj.e + "</td>"
    content += "<td class='diet_date' style='display: none'>" + obj.j + "</td>"
    if (obj.h == id)
        content += "<td><button type='button' class='diet_Delete' onclick='dietDelete(this)'>삭제</button>" + "</td>"
    content += "</tr>"
    $(timeObj.eat).before(content);
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


function splitDate() {
    // 캘린더 Input JS
    var link = document.location.href.split("?");
    let date = link[1];
    $(".date").val(date);
}

function showDayAllResult() {
    var e = [".breakfast_eat_rate", ".lunch_eat_rate", ".dinner_eat_rate", ".extra_eat_rate"];
    var k = [".breakfast_kcal", ".lunch_kcal", ".dinner_kcal", ".extra_kcal"];
    var c = [".breakfast_carbo", ".lunch_carbo", ".dinner_carbo", ".extra_carbo"];
    var p = [".breakfast_protein", ".lunch_protein", ".dinner_protein", ".extra_protein"];
    var f = [".breakfast_fat", ".lunch_fat", ".dinner_fat", ".extra_fat"];

    nutr(e, '.dailyEatRate');
    nutr(k, '.dailyKcal');
    nutr(c, '.dailyCarbo');
    nutr(p, '.dailyProtein');
    nutr(f, '.dailyFat');
}

// 일일 합계
function nutr(arr, where) {
    let sum = 0;
    arr.forEach(function (el, index) {
        let value = $(el).text();
        sum += parseInt(value);
    })

    $(where).text(sum);
    return sum;
}

// 식단 리스트 삭제
function removeNutr() {
    $('.breakfastList').remove();
    $('.lunchList').remove();
    $('.dinnerList').remove();
    $('.extraList').remove();
}

function hideEat(){
    $('.breakfastEat').hide();
    $('.lunchEat').hide();
    $('.dinnerEat').hide();
    $('.extraEat').hide();
    $('.dailyEat').hide();
    $('.breakfastList').remove();
    $('.lunchList').remove();
    $('.dinnerList').remove();
    $('.extraList').remove();
    $('.noData').show();
}

function showEat(){
    $('.breakfastEat').show();
    $('.lunchEat').show();
    $('.dinnerEat').show();
    $('.extraEat').show();
    $('.dailyEat').show();
    $('.noData').hide();
}

function chartCreate(){
    let a = parseInt($('.dailyCarbo').text());
    let b = parseInt($('.dailyProtein').text());
    let c = parseInt($('.dailyFat').text());

    let dailyCarbo = (a/(a+b+c)*100).toFixed(2);
    let dailyProtein = (b/(a+b+c)*100).toFixed(2);
    let dailyFat = (c/(a+b+c)*100).toFixed(2);
    if(myChart){
        myChart.destroy();
    }

    myChart = new Chart(
        document.getElementById('myChart'),
        {

            type: 'pie',
            data: {
                labels: [
                    '탄수화물',
                    '단백질',
                    '지방'
                ],
                datasets: [{
                    label: 'chart',
                    data: [dailyCarbo, dailyProtein, dailyFat],
                    backgroundColor: [
                        'rgb(255, 99, 132)',
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
            },
            options : {
                responsive:false,
                title: {
                    display: true,
                    text: '하루 섭취량',
                }
            }
        }
    );

}

function zeroData(){
    $('.dailyCarbo').text('0');
    $('.dailyProtein').text('0');
    $('.dailyFat').text('0');

}
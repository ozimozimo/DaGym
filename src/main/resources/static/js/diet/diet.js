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

    if( $('.login_id').val() != $('.diet_member_id').val()){
        $('.addBtn').hide();
        // $('.diet_Delete').hide();
    }
    clickList()

})

// 내가 선택한 회원 정보 조회를 위해 회원 아이디만 따로 빼오는 곳
function changeId() {
    let location = window.location.href;
    if(location.indexOf('list')>-1) {
        let arr = location.split("=");
        $('.diet_member_id').val(arr[1]);
    } else {
        splitDate();
    }
}

// 오늘 날짜 가져오기 (2021-04-30 형태)
let date = new Date();
date = getFormatDate(date);
let myChart;

// 날짜 포맷 변경
function getFormatDate(date) {
    let year = date.getFullYear();
    let month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    let day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

// 식단 검색
function dietSearch() {
    let diet = $('#diet').val();
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
        let str = result.I2790.row[i];
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
        console.log($('.date').val());
        let id = $('.diet_member_id').val();
        console.log("id : " + id);
        // 내가 먹은 양
        let eat_rate = $(this).parent().siblings().children('.eat_rate').val();
        // 기존 제공량
        let default_eat_rate = $(this).parent().siblings('.diet_serve').text();
        let divide_eat_rate = Math.round(default_eat_rate / eat_rate);
        let eat_kcal = Math.round($(this).parent().siblings("td.diet_kcal").text() / divide_eat_rate);
        let eat_carbo = Math.round($(this).parent().siblings("td.diet_carbo").text() / divide_eat_rate);
        let eat_protein = Math.round($(this).parent().siblings("td.diet_protein").text() / divide_eat_rate);
        let eat_fat = Math.round($(this).parent().siblings("td.diet_fat").text() / divide_eat_rate);
        let data = {
            diet_member_id: id,
            diet_name: $(this).parent().siblings("td.diet_name").text(),
            eat_rate: eat_rate || default_eat_rate,
            diet_kcal: eat_kcal || $(this).parent().siblings("td.diet_kcal").text() || 0,
            diet_carbo: eat_carbo || $(this).parent().siblings("td.diet_carbo").text() || 0,
            diet_protein: eat_protein || $(this).parent().siblings("td.diet_protein").text() || 0,
            diet_fat: eat_fat || $(this).parent().siblings("td.diet_fat").text() || 0,
            diet_time: $(this).parent().siblings().children("select[name=diet_time]").val(),
            diet_date: $('.date').val(),
        }
        console.log('데이타 : ', data);
        $.ajax({
            type: 'POST',
            url: '/diet/save/' + id,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            alert('식단이 추가되었습니다');
            console.log(data)
        }).fail(function (error) {
            alert(error);
            console.log(data);
            console.log(JSON.stringify(error));
        })
    })
}

// 검색창
function dietWindow() {
    let popupX = (window.screen.width / 2) - (800 / 2);
    let popupY = (window.screen.height / 2) - (700 / 2);
    let date = $('.date').val();
    let option = 'status=no, height=700, width=1560, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    // let url = "http://localhost:8090"
    // let url = "http://140.238.25.78:8090";
    let url = "/diet/search" + "?" + date;
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

function showSumNutr() {
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
    let id = $('.login_id').val();

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
        content += "<td><button type='button' class='diet_Delete btn btn-inline-secondary' onclick='dietDelete(this)'>삭제</button>" + "</td>"
    // else {
    //     content += "<td></td>"
    // }
    content += "</tr>"
    $(timeObj.eat).before(content);
}

// 오늘 날짜 데이터
function todayData() {
    let today = date;
    let id = $('.diet_member_id').val();
    let data = {
        id: id,
        diet_date: today
    }
    $.ajax({
        type: 'get',
        url: '/diet/dietToday/',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        console.log(result);
        mkDiet(result);
    }).fail(function () {
        alert('문제가 있다');
    })
}


function splitDate() {
    // 캘린더 Input JS
    let link = document.location.href.split("?");
    let date = link[1];
    $(".date").val(date);
}

function showDayAllResult() {
    let e = [".breakfast_eat_rate", ".lunch_eat_rate", ".dinner_eat_rate", ".extra_eat_rate"];
    let k = [".breakfast_kcal", ".lunch_kcal", ".dinner_kcal", ".extra_kcal"];
    let c = [".breakfast_carbo", ".lunch_carbo", ".dinner_carbo", ".extra_carbo"];
    let p = [".breakfast_protein", ".lunch_protein", ".dinner_protein", ".extra_protein"];
    let f = [".breakfast_fat", ".lunch_fat", ".dinner_fat", ".extra_fat"];

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

function hideEat() {
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

function showEat() {
    $('.breakfastEat').show();
    $('.lunchEat').show();
    $('.dinnerEat').show();
    $('.extraEat').show();
    $('.dailyEat').show();
    $('.noData').hide();
}

function chartCreate() {
    let a = parseInt($('.dailyCarbo').text());
    let b = parseInt($('.dailyProtein').text());
    let c = parseInt($('.dailyFat').text());

    // let dailyCarbo = (a / (a + b + c) * 100).toFixed(2);
    // let dailyProtein = (b / (a + b + c) * 100).toFixed(2);
    // let dailyFat = (c / (a + b + c) * 100).toFixed(2);
    let dailyCarbo = Math.round(a / (a + b + c) * 100);
    let dailyProtein = Math.round(b / (a + b + c) * 100);
    let dailyFat = Math.round(c / (a + b + c) * 100);


    if (myChart) {
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
            options: {
                responsive: false,
                title: {
                    display: true,
                    text: '하루 섭취량(%)',
                }
            }
        }
    );

}

function zeroData() {
    $('.dailyCarbo').text('0');
    $('.dailyProtein').text('0');
    $('.dailyFat').text('0');

}

function clickList(){
    $('.minus').on("click",function (){
        $(this).closest('table').find('tbody').toggle();

        if($(this).hasClass('plus')){
            $(this).removeClass("plus");
            $(this).html('<i class="fas fa-minus"></i>')
        }
        else{
            $(this).addClass("plus");
            $(this).html('<i class="fas fa-plus"></i>')
        }
    })
}
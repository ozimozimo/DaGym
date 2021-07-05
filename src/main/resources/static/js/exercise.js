let date = new Date();
date = getFormatDate(date);

function changeId() {
    let location = window.location.href;
    let arr = location.split("=");
    $('.member').val(arr[1]);
    console.log('arr : ' + arr[1]);
    $('.ex_record_member_id').val(arr[1]);
}

function getFormatDate(date) {
    let year = date.getFullYear();
    let month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    let day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

function AllAddClassValue(className) {
    let name = $(className);
    let contents = "";
    name.each(function () {
        let value = $(this).val() || "";
        value += ","
        contents += value;
    })
    return contents;
}

function addExBtnClick() {
    let id = $(".loginUser").val();
    let user = $(".ex_record_member_id").val();
    console.log("id= " + id);
    console.log("user= " + user);

    // let ex_set = $("#ex_set").val();
    // let ex_count = $("#ex_count").val();
    // let ex_weight = $("#ex_weight").val();
    // let ex_date = $('.date').val();
    // let ex_time = $('#ex_time').val();
    // let ex_meter = $('#ex_meter').val();
    // let exercise_id = $('.exer_name option:selected').attr("id");
    // let ex_parts = $('.parts option:selected').val();
    // let ex_name = $('#input_exer_name').val();
    // let ex_kcal = $('#ex_kcal').val();
    // let ex_category = $('.category option:selected').val();

    let set = AllAddClassValue(".ex_set1");
    let count = AllAddClassValue(".ex_count1");
    let weight = AllAddClassValue(".ex_weight1");
    let time = AllAddClassValue('.ex_time1');
    let meter = AllAddClassValue('.ex_meter1');
    let name = AllAddClassValue('.input_exer_name1');
    let kcal = AllAddClassValue('.ex_kcal1');

    let ex_set = set
    let ex_count = count
    let ex_weight = weight
    let ex_date = $('.date').val();
    let ex_time = time
    let ex_meter = meter
    let exercise_id = $('.exer_name option:selected').attr("id");
    let ex_parts = $('.parts option:selected').val();
    let ex_name = name
    let ex_kcal = kcal
    let ex_category = $('.category option:selected').val();


    alert("ex_name = " + ex_name);

    console.log("addExBtnClick ex_date = " + ex_date);

    let category = $('.category option:selected').val();
    let data;
    if (category == '유산소') {
        data = JSON.stringify({
            ex_time: ex_time,
            ex_meter: ex_meter,
            ex_date: ex_date,
            exercise_id: exercise_id,
            ex_parts: ex_parts,
            kcal: ex_kcal,
            ex_category: ex_category
        });
    } else if (category == '렙만') {
        data = JSON.stringify({
            ex_set: ex_set,
            ex_count: ex_count,
            ex_date: ex_date,
            exercise_id: exercise_id,
            ex_category: ex_category
        });
    } else if (category == '지속 시간') {
        data = JSON.stringify({
            ex_set: ex_set,
            ex_time: ex_time,
            ex_date: ex_date,
            exercise_id: exercise_id,
            ex_category: ex_category
        });
    } else if (category == '기타(유산소)') {
        data = JSON.stringify({
            ex_time: ex_time,
            ex_meter: ex_meter,
            ex_date: ex_date,
            ex_name: ex_name,
            ex_parts: ex_parts,
            kcal: ex_kcal,
            ex_category: category
        });
    } else {
        data = JSON.stringify({
            ex_set: ex_set,
            ex_count: ex_count,
            ex_weight: ex_weight,
            ex_date: ex_date,
            exercise_id: exercise_id,
            ex_category: ex_category
        });
    }
    console.log(data);
    $.ajax({
        type: 'post',
        url: '/ExRecord/save/' + id,
        contentType: 'application/json; charset=utf-8',
        data: data
    }).done(function (result) {
        alert('운동이 추가되었습니다')
        location.reload();
    }).fail(function (error) {
        alert(error);
    });
}

function delExBtnClick(a) {
    let id = a.parentNode.parentNode.firstChild.innerText;
    console.log(id);
    $.ajax({
        type: 'post',
        url: '/ExRecord/delete/' + id,
        contentType: 'application/json; charset=utf-8',
    }).done(function () {
        alert('기록이 삭제되었습니다');
        location.reload();
    }).fail(function (error) {
        alert(error);
        console.log(JSON.stringify(error));
    })
}

function mkExr(result) {
    // console.log("mkExr in + " + result[0].ex_name);
    let id = $('.loginUser').val();
    let allKcal = 0;

    result.sort(function (a, b) {
        return a.ex_category < b.ex_category ? -1 : a.ex_category > b.ex_category ? 1 : 0;
    })
    result.sort(function (a, b) {
        return a.ex_name < b.ex_name ? -1 : a.ex_name > b.ex_name ? 1 : 0;
    })

    for (let i = 0; i < result.length; i++) {
        console.log("id  " + id);
        let a = result[i].ex_record_id;
        let member_id = result[i].ex_record_member_id;
        let exName = result[i].ex_name;
        let exDate = result[i].ex_date;
        let exCategory = result[i].ex_category;
        let exParts = result[i].ex_parts;

        let exSet = removeString(result[i].ex_set || "");
        let exCount = removeString(result[i].ex_count || "");
        let exWeight = removeString(result[i].ex_weight || "");
        let exMeter = removeString(result[i].ex_meter || "");
        let exTime = removeString(result[i].ex_time || "");
        let exKcal = removeString(result[i].kcal || "");
        for (let i in exKcal) {
            allKcal += parseInt(exKcal[i]);
        }
        console.log("allKcal = " + allKcal);
        console.log("member_id  " + member_id);

        function removeString(str) {
            // let result = str.match(/,/g);
            // if(result != null) {
            //     if (result == 1) {
            //         let prevStr = str;
            //         let fStr = prevStr.replace(',', '');
            //
            //     } else {
            //         let splitStr = str.split(',');
            //         return splitStr;
            //     }
            // }

            let splitStr = str.split(',');
            return splitStr;
        }

        let content = "";
        if (exCategory == '렙만') {
            $(".onlyCount").css("display", "");
            if (exSet.length > 1) {
                for (let i in exSet) {
                    if (!(exSet[i] == "")) {
                        content += "<tr>"
                        content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                        // if(i == 0){
                        content += "<td class='res_ex_category'>" + exCategory + "</td>"
                        content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                        // } else {
                        //     content += "<td></td>"
                        //     content += "<td></td>"
                        // }
                        content += "<td class='res_ex_set'>" + exSet[i] + "세트</td>"
                        content += "<td class='res_ex_count'>" + exCount[i] + "개</td>"
                        content += "<td>" + "</td>";
                        content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                        if (id == member_id)
                            content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>"
                        content += "</tr>"
                    }
                }
            } else {
                content += "<tr>";
                content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                content += "<td class='res_ex_category'>" + exCategory + "</td>"
                content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                content += "<td class='res_ex_set'>" + exSet + "세트</td>"
                content += "<td class='res_ex_count'>" + exCount + "개</td>"
                content += "<td>" + "</td>";
                content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                if (id == member_id)
                    content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>"
                content += "</tr>"
            }
            $('.onlyCountEx').append(content);

        } else if (exCategory == '유산소' || exCategory == '기타(유산소)') {
            $(".cardio").css("display", "");

            if (exTime.length > 1) {
                for (let i in exTime) {
                    if (!(exTime[i] == "")) {
                        content += "<tr>";
                        content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                        content += "<td class='res_ex_category'>" + exCategory + "</td>"
                        content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                        content += "<td class='res_ex_time'>" + exTime[i] + "분</td>"
                        content += "<td class='res_ex_meter'>" + exMeter[i] + "KM</td>"
                        content += "<td class='res_ex_kcal'>" + exKcal[i] + "Kcal</td>"
                        content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                        if (id == member_id)
                            content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>"
                        content += "</tr>"
                    }
                }
            } else {
                content += "<tr>";
                content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                content += "<td class='res_ex_category'>" + exCategory + "</td>"
                content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                content += "<td class='res_ex_time'>" + exTime + "분</td>"
                content += "<td class='res_ex_meter'>" + exMeter + "KM</td>"
                content += "<td class='res_ex_kcal'>" + exKcal + "Kcal</td>"
                content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                if (id == member_id)
                    content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>"
                content += "</tr>"
            }
            $('.cardioEx').append(content);
        } else if (exCategory == '지속 시간') {
            $(".time").css("display", "");

            if (exSet.length > 1) {
                for (let i in exSet) {
                    if (!(exSet[i] == "")) {

                        content += "<tr>";
                        content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>";
                        content += "<td class='res_ex_category'>" + exCategory + "</td>"
                        content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                        content += "<td class='res_ex_set'>" + exSet[i] + "세트</td>";
                        content += "<td class='res_ex_time'>" + exTime[i] + "분</td>";
                        content += "<td>" + "</td>";
                        content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>";

                        if (id == member_id)
                            content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>";
                        content += "</tr>";
                    }
                }
            } else {
                content += "<tr>";
                content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>";
                content += "<td class='res_ex_category'>" + exCategory + "</td>"
                content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                content += "<td class='res_ex_set'>" + exSet + "세트</td>";
                content += "<td class='res_ex_time'>" + exTime + "분</td>";
                content += "<td>" + "</td>";
                content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>";

                if (id == member_id)
                    content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>";
                content += "</tr>";
            }

            $('.timeEx').append(content);
        } else {
            $(".another").css("display", "");
            if (exSet.length > 1) {
                for (let i in exSet) {
                    if (!(exSet[i] == "")) {

                        content += "<tr>";
                        content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                        content += "<td class='res_ex_category'>" + exCategory + "</td>"
                        content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                        content += "<td class='res_ex_set'>" + exSet[i] + "세트</td>"
                        content += "<td class='res_ex_count'>" + exCount[i] + "개</td>"
                        content += "<td class='res_ex_weight'>" + exWeight[i] + "KG</td>"
                        content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                        if (id == member_id)
                            content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>";
                        content += "</tr>";
                    }
                }
            } else {
                content += "<tr>";
                content += "<td class='res_ex_record_id' style='display: none'>" + a + "</td>"
                content += "<td class='res_ex_category'>" + exCategory + "</td>"
                content += "<td class='res_ex_name'>" + exName + '(' + exParts + ')' + "</td>"
                content += "<td class='res_ex_set'>" + exSet + "세트</td>"
                content += "<td class='res_ex_count'>" + exCount + "개</td>"
                content += "<td class='res_ex_weight'>" + exWeight + "KG</td>"
                content += "<td class='res_ex_date' style='display: none'>" + exDate + "</td>"

                if (id == member_id)
                    content += "<td><button type='button' class='delEx_btn' onclick='delExBtnClick(this)'>삭제</button></td>";
                content += "</tr>";
            }

            $('.anotherEx').append(content);
        }
    }
    $('.allKcal').text("오늘의 소모 칼로리는 " + allKcal + "Kcal 입니다.");
}

// 오늘 날짜 데이터
function todayData() {
    let today = date;
    let id = $('.ex_record_member_id').val();


    console.log("id="+id)
    console.log("today="+today)
    let data = {
        id: id,
        ex_date: today
    }
    $.ajax({
        type: 'get',
        url: '/ExRecord/exDate/',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        console.log(result);
        mkExr(result);
    }).fail(function () {
        alert("실패");
    });

}

function name_select() {
    let name = $('.exer_name option:selected').val();
    let id = $('.exer_name option:selected').attr("id");
    console.log('name = ' + name);
    console.log('id = ' + id);

    $.ajax({
        type: 'post',
        url: '/ExRecord/nameSelect/' + id,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        let category = result.ex_category;
        console.log('category = ' + category);
        changeInsertBox(category);
    }).fail(function () {

    });
}

function changeInsertBox(category) {

    console.log("changeInsertBox = " + category);

    if (category == '렙만') {
        $(".ex_time").css("display", "none");
        $(".ex_weight").css("display", "none");
        $(".ex_meter").css("display", "none");
        $(".ex_count").css("display", "");
        $(".ex_kcal").css("display", "none");
        $(".ex_set").css("display", "");
    } else if (category == '유산소') {
        $(".ex_time").css("display", "");
        $(".ex_weight").css("display", "none");
        $(".ex_meter").css("display", "");
        $(".ex_count").css("display", "none");
        $(".ex_kcal").css("display", "");
        $(".ex_set").css("display", "none");
    } else if (category == '지속 시간') {
        $(".ex_time").css("display", "");
        $(".ex_weight").css("display", "none");
        $(".ex_meter").css("display", "none");
        $(".ex_count").css("display", "none");
        $(".ex_kcal").css("display", "none");
        $(".ex_set").css("display", "");
    } else {
        $(".ex_time").css("display", "none");
        $(".ex_weight").css("display", "");
        $(".ex_meter").css("display", "none");
        $(".ex_count").css("display", "");
        $(".ex_kcal").css("display", "none");
        $(".ex_set").css("display", "");
    }
}

function category_select() {
    let category = $('.category option:selected').val()
    let parts = $('.parts option:selected').val()

    if (category == '기타(유산소)') {
        $('#input_exer_name').css("display", "");
        $('#exer_name').css("display", "none");

        $(".ex_time").css("display", "");
        $(".ex_weight").css("display", "none");
        $(".ex_meter").css("display", "");
        $(".ex_count").css("display", "none");
        $(".ex_kcal").css("display", "");
        $(".ex_set").css("display", "none");
    } else {
        $('#input_exer_name').css("display", "none");
        $('#exer_name').css("display", "");
        let data = JSON.stringify({
            ex_category: category,
            ex_parts: parts
        })

        $.ajax({
            type: 'post',
            url: '/ExRecord/select',
            data: data,
            contentType: 'application/json; charset=utf-8',
        }).done(function (result) {
            ChangeExerciseName(result);
            if (category != '' && parts != '') {
                $(".recommendEx").remove();
                findRandom(result);
            }
        }).fail(function () {
        })
    }

    console.log("category = " + category);

}

function ChangeExerciseName(result) {
    $('.exer_name').empty();
    let content = '<option>' + '선택해주세요' + '</option>';
    for (let i = 0; i < result.length; i++) {
        content += `<option id="${result[i].ex_id}" value="${result[i].ex_name}">${result[i].ex_name}</option>`;
    }
    $('.exer_name').append(content);
}

function nameSelected(select) {
    console.log(select);
}

function findRandom(result) {
    let category = result[0].ex_category;
    let parts = result[0].ex_parts;

    let data = JSON.stringify({
        ex_category: category,
        ex_parts: parts
    })

    $.ajax({
        type: 'post',
        url: '/ExRecord/random',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        let num;
        if (result.ex_category == "지속시간" || result.ex_category == "렙만") num = 6;
        else num = 7;
        let content = `<tr class="recommendEx"><td colspan="${num}"><span>${result.ex_parts}부위${result.ex_category}운동인</span><a class="nameClick" onclick="nameClick()">${result.ex_name}</a><span>을 해보는건 어떠신가요?</span></td></tr>`;
        $(".addEx").append(content);
    }).fail(function (error) {

    });
}

function nameClick() {
    let name = $(".nameClick").text();
    let category = $(".category option:selected").val();

    $(".exer_name").val(name);
    console.log("nameClike category = " + category);
    changeInsertBox(category);
}

$(function () {
    $(".ex_time").css("display", "none");
    $(".ex_meter").css("display", "none");
    $(".ex_kcal").css("display", "none");
    changeId();
    $(".addEx_btn").on("click", addExBtnClick);
    todayData();

    if ($('.loginId').text() != $('.member').val()) {
        $('.add_area').hide();
    }
    $('.setAddBtn').on("click", addBtn);
});

function addBtn() {
    let category = $('.category').val();
    let content = `<tr class="addInput">
<td>
</td>
<td>
</td>
<td class="ex_name">
</td>
<td class="ex_set">
<input type="text" class="ex_set1" name="ex_set"/>
</td>
 <td class="ex_count">
<input type="text" class="ex_count1" name="ex_count"/>
</td>
<td class="ex_weight">
<input type="text" class="ex_weight1" name="ex_weight"/>
</td>
<td class="ex_time">
<input type="text" class="ex_time1" name="ex_set"/>
</td>
<td class="ex_meter">
<input type="text" class="ex_meter1" name="ex_meter"/>
</td>
<td class="ex_kcal">
    <input type="text" class="ex_kcal1" name="ex_kcal"/>
    </td>
</tr>`
    $('.addEx').append(content);
    changeInsertBox(category);
}
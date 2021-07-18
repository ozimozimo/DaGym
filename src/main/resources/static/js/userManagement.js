$(function () {
    $('.checkBtn').on("click", function () {
        let pt_id = $(this).parent().parent().children('.pt_id').val();
        let pt_times = $(this).parent().parent().children('.pt_times').val();
        PTFinish(pt_id, pt_times);
    });

    $('.user_pn').each(function () {
        let str = $(this).text();
        let phone = str.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
        $(this).text(phone);
    })

    // btnShow();
    $( window ).resize(function() {
        $('.active').removeClass('active');
    });

});

function PTFinish(pt_id, pt_times) {

    console.log(pt_id);
    console.log(pt_times);
    let count = parseInt(pt_times);
    if (count > 0) {
        alert("PT 잔여 횟수가 " + count + "회 남았습니다");
    } else {
        $.ajax({
            type: 'post',
            url: '/ptUser/ptEnd/' + pt_id,
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('PT가 종료되었습니다.');
            location.reload();
        }).fail(function () {
            alert('PT 잔여 횟수가 아직 남았습니다');
        });
    }
}
//
// function btnShow() {
//     $('.viewMoreBtn').on("click", function () {
//         let viewItem = $(this).next();
//
//         if(viewItem.hasClass('active')){
//             viewItem.removeClass('active');
//             return false;
//         }
//         $('.active').removeClass('active');
//         console.log($(this).offset().top);
//         console.log($(this).offset().left);
//         let top = $(this).offset().top;
//         let left = $(this).offset().left;
//
//
//         // let one = viewItem.find('one');
//         // let two = viewItem.find('two');
//         // let three = viewItem.find('three');
//         // let four = viewItem.find('four');
//         //
//         // console.log(one);
//         // console.log(two);
//         // console.log(three);
//         // console.log(four);
//
//
//         // let topValue = 250;
//         // let leftValue = 250;
//         //
//         // one.css('top', top + topValue);
//         // one.css('left', left - leftValue);
//         //
//         // two.css('top', top - topValue);
//         // two.css('left', left);
//         //
//         // three.css('top', top);
//         // three.css('left', left + leftValue);
//         //
//         // four.css('top', top + topValue);
//         // four.css('left', left);
//
//         viewItem.css('top', top-50);
//         viewItem.css('left', left-120);
//
//         viewItem.toggleClass('active');
//
//     })
// }

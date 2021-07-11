function EmoSelect() {
    let bb_num = $('#bb_num').val();
    console.log(bb_num);
    $.ajax({
        type: 'GET',
        url: '/boastboard/emotion/select/' + bb_num,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function(result) {
        $('#l_num').text(result.l);
        $('#m_num').text(result.m);
        $('#s_num').text(result.s);
        $('#a_num').text(result.a);
        $('#w_num').text(result.w);
    }).fail(function(fail) {

    })
}

$(function (){
    EmoSelect();
})

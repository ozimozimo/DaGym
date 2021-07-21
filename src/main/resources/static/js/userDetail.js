function phone(){
    let str = $('.user_pn').text();
    let phone = str.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
    $('.user_pn').text(phone);
}
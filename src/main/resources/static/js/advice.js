$(function () {
    $('.adviceAdd').on('click', adviceAdd);
    $('.adviceUpdate').on('click', adviceUpdate);
})

function adviceAdd() {
    $('.adviceAdd').on('click', function () {
        var id = $('.loginId ').text();
        var id2 = $('.diet_id').text();
        var content = $('.adviceWrite').val();
        console.log(id);
        console.log(id2);
        console.log(content);
    })
}

// 트레이너 한마디 수정
function adviceUpdate() {
    $('.adviceUpdate').on('click', function () {
        alert('hello');
    })
}

$(function () {
    $('.q').on("click",function (){
        $(this).siblings('.a').toggle();
        console.log(this);

    })
})



$(function () {
    $('.q').on("click",function (){
        $(this).siblings('.a').toggle();

        if($(this).find('i').hasClass('fa-chevron-down')){
            $(this).find('i').removeClass('fa-chevron-down');
            $(this).find('i').addClass('fa-chevron-up')
        } else {
            $(this).find('i').addClass('fa-chevron-down');
            $(this).find('i').removeClass('fa-chevron-up')
        }

        console.log(this);

    })
})


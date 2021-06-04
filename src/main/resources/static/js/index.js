$(function () {
    $(".name,.student,.small-intro").lettering();

    // IMAGES OVERLAY EFFECT
    anime({
        targets: ".home .person-overlay",
        translateX: 1100,
        delay: 100,
        duration: 150,
        easing: "easeInSine",
    });
    anime({
        targets: ".home .person img",
        opacity: 1,
        delay: 150,
        duration: 150,
        easing: "easeInSine",
    });

    // Student
    anime({
        targets: ".student span",
        opacity: 1,
        duration: 150,
        easing: "easeInSine",
        delay: anime.stagger(100, {start: 300}),
    });

    // MICHAEL NAME - FADE in
    anime({
        targets: ".name span",
        opacity: 1,
        duration: 300,
        easing: "easeInSine",
        delay: anime.stagger(100, {start: 450}),
    });

    // MICHAEL 2ND ANIMATION UP AND DOWN LETTERS  :DAGYM부분 글씨 UP AND DOWN
    // anime({
    //   targets: ".name .char2, .name .char4, .name .char6",
    //   keyframes: [{ translateY: 100, duration: 1500 }],
    //   easing: "easeInOutExpo",
    //   delay: 8000,
    // });
    // anime({
    //   targets: ".name .char3, .name .char5",
    //   keyframes: [{ translateY: -100, duration: 1500 }],
    //   easing: "easeInOutExpo",
    //   delay: 8000,
    // });

    // logo
    anime({
        targets: ".logo",
        opacity: 1,
        duration: 300,
        delay: 950,
    });
    //menu
    anime({
        targets: ".menu li",
        opacity: 1,
        duration: 300,
        delay: anime.stagger(150, {start: 1050}),
    });
    // INTRO
    anime({
        targets: ".small-intro span",
        opacity: 1,
        duration: 100,
        easing: "easeInSine",
        delay: anime.stagger(50, {start: 1100}),
    });
});
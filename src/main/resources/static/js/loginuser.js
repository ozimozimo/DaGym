if(!$('.login_user').val()) {
    let path = location.pathname;
    if (path != '/' && !path.includes('/member')) {
        alert('로그인 후 이용해주세요');
        location.href = "/member/login";
    }
}


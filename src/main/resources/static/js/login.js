const registerMember = function () {
    let memberId = document.getElementById('memberId').value;
    let memberPw = document.getElementById('memberPw').value;

    const params = {memberId: memberId, memberPw: memberPw};
    Commons.ajaxPost("/setting/regMember", params, function(resp) {
        if (resp.success === true) {
            swal({
                title: "Success!",
                text: "Congratulations, Register Success",
                icon: "success"
            }).then(function() {
                // 성공시 확인 버튼 클릭 후 login 페이지로 리다이렉트
                window.location.href = "/chat/room";
            });
        } else if (resp.success === false){
            swal({
                title: 'Fail', /*상단 타이틀*/
                text: 'Name is already Exist', /*내용*/
                icon: 'error' /*아이콘 타입*/
            });
        }
    });
};
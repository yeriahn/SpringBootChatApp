const registerMember = function () {
    let memberId = document.getElementById('memberId').value;
    let memberPw = document.getElementById('memberPw').value;

    if(chatRoomValidation(memberId, memberPw) == 'exit') return;

    const params = {memberId: memberId, memberPw: memberPw};
    Commons.ajaxPost("/setting/regMember", params, function(resp) {
        if (resp.success === true) {
            swal({
                title: 'Success!',
                text: '회원가입에 성공했습니다.',
                icon: 'success'
            }).then(function() {
                // 성공시 확인 버튼 클릭 후 login 페이지로 리다이렉트
                window.location.href = "/chat/room";
            });
        } else if (resp.success === false){
            swal({
                title: 'Fail', /*상단 타이틀*/
                text: '중복된 아이디입니다.', /*내용*/
                icon: 'error' /*아이콘 타입*/
            });
        }
    });
};

function chatRoomValidation(memberId, memberPw)  {
    let specialCheck = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

    if(memberId == null || memberId === '') {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '아이디는 필수 입력 사항입니다.', /*내용*/
            icon: 'error' /*아이콘 타입*/
        });
        document.getElementById('room-name').focus();
        return 'exit'
    }else if(memberId.search(/\s/) != -1 || specialCheck.test(memberId)) {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '아이디는 공백 및 특수문자를 포함할 수 없습니다.', /*내용*/
            icon: 'error' /*아이콘 타입*/
        });
        document.getElementById('room-name').focus();
        return 'exit'
    }

    if(memberPw == null || memberPw == '') {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '비밀번호는 필수 입력 사항입니다.', /*내용*/
            icon: 'error' /*아이콘 타입*/
        });
        document.getElementById('room-pw').focus();
        return 'exit'
    }else if(memberPw.search(/\s/) != -1 || specialCheck.test(memberPw)) {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '비밀번호는 공백 및 특수문자를 포함할 수 없습니다.', /*내용*/
            icon: 'error' /*아이콘 타입*/
        });
        document.getElementById('room-pw').focus();
        return 'exit'
    }

}
function checkValidation(data)  {
    let specialCheck = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

    if(data == null || data === '') {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '입력되지 않은 항목이 존재합니다. 모두 입력해주세요.', /*내용*/
            icon: 'error' /*아이콘 타입*/,
        });
        return 'exit'
    }else if(data.search(/\s/) != -1 || specialCheck.test(data)) {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '공백 및 특수문자를 포함할 수 없습니다.', /*내용*/
            icon: 'error' /*아이콘 타입*/
        });
        return 'exit'
    }
}

function checkRoomValidation(data)  {
    if(data == null || data == '') {
        swal({
            title: 'Fail', /*상단 타이틀*/
            text: '채팅 내용을 입력해주세요.', /*내용*/
            icon: 'error' /*아이콘 타입*/,
        });
        return 'exit'
    }
}
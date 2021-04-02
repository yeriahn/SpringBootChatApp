const Room_detail = (function () {
    const updateRoom = function () {
        alert("updateRoom");
    };
    const deleteRoom = function () {
        let roomId = document.getElementById('roomId').value;
        let roomPw = document.getElementById('delete-room-pw').value;

        if(checkValidation(roomPw) == 'exit') return;

        const params = {roomId: roomId, roomPw: roomPw};
        Commons.ajaxDelete("/api/chat/delete-room", params, function(resp) {
            if(resp == 1) {
                location.replace("/chat/room");
            }else {
                swal({
                    title: 'Fail', /*상단 타이틀*/
                    text: '채팅방 삭제에 실패하였습니다. 다시 한번 확인해주세요.', /*내용*/
                    icon: 'error' /*아이콘 타입*/,
                });
            }
        });

    };
    return {
        updateRoom: updateRoom,
        deleteRoom: deleteRoom
    };
})();

//modal
const openDelete = document.getElementById("delete-open");
const closeDelete = document.getElementById("delete-close");
const modalDelete = document.querySelector(".delete-modal-wrapper");
openDelete.onclick = () => {
    modalDelete.style.display = "flex";
};
closeDelete.onclick = () => {
    modalDelete.style.display = "none";
};
const Room_detail = (function () {
    const updateRoom = function () {
        alert("updateRoom");
    };
    const deleteRoom = function () {
        let roomId = document.getElementById('roomId').value;
        let roomPw = document.getElementById('delete-room-pw').value;

        console.log(roomId);
        console.log(roomPw);

        const params = {roomId: roomId, roomPw: roomPw};
        Commons.ajaxDelete("/api/chat/delete-room", params, function(resp) {
            if(resp == 1) {
                location.replace("/chat/room");
            }else {
                alert("채팅방 삭제에 실패하였습니다. 다시 한번 확인해주세요.");
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
const Room = (function () {


    // TODO: Overlay
    getListRenderAll();

    function getListRenderAll() {
        const $list = $("#items");
        const $row = $("#chat-room-list-template").find("tr");
        $list.empty();

        const container = document.querySelector('.items');

        Commons.ajaxGet("/api/chat/chat-room", function(data) {
            console.log(data);
            if(Commons.isNotEmpty(data)) {
                const container = document.querySelector('.items');
                container.innerHTML = data.map((item) => createHTMLString(item)).join('');
            }
        })
    };

    const createRoom = function () {
        const roomName = document.getElementById('room-name').value;
        const params = {name: roomName};
        Commons.ajaxPost("/api/chat/chat-room", params, function(resp) {
            getListRenderAll();
        });
    };
    const updateRoom = function () {
        alert("updateRoom");
    };
    const deleteRoom = function () {
        alert("deleteRoom");
    };
    return {
        getListRenderAll: getListRenderAll,
        createRoom: createRoom,
        updateRoom: updateRoom,
        deleteRoom: deleteRoom
    };
})();


function createHTMLString(item) {
    return `
    <li class="item">
        <span class="item__description"><a href="/chat/room/detail/"+${item.id}">${item.id} 번방 : ${item.name}</a></span>
    </li>
    `;
}

const open = document.getElementById("open");
const close = document.getElementById("close");
const modal = document.querySelector(".modal-wrapper");
open.onclick = () => {
    modal.style.display = "flex";
};
close.onclick = () => {
    modal.style.display = "none";
};

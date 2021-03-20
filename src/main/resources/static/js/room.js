const Room = (function () {

    // TODO: Overlay
    getListRenderAll();

    function getListRenderAll() {
        const $list = $("#items");
        const $row = $("#chat-room-list-template").find("tr");
        $list.empty();

        const container = document.querySelector('.items');

        Commons.ajaxGet("/api/chat/chat-room", function(data) {
            if(Commons.isNotEmpty(data)) {
                displayItems(data);
            }else {
                container.innerHTML = `
                    <li class="item">
                        <span class="item__description">채팅방이 존재하지 않습니다.</span>
                    </li>
                    `;
            }

            setEventListeners(data);
        })
    };

    const createRoom = function () {
        //채팅방 이름
        let roomName = document.getElementById('room-name').value;
        //채팅방 카테고리
        let categorySelect = document.querySelector('#room-category');
        let roomCategory = categorySelect.options[categorySelect.selectedIndex].value;

        const params = {name: roomName, category: roomCategory};
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
        <span class="item__description"><a href="/chat/room/detail/${item.id}">${item.name}</a></span>
    </li>
    `;
}

//버튼이 클릭되었을 때 동작
function setEventListeners(items) {
    const logo = document.querySelector('.logo');
    const buttons = document.querySelector('.button-group-prepend'); //버튼이 모여있는 컨테이너에 이벤트 부여
    logo.addEventListener('click', () => displayItems(items)); //로고 클릭 시, 모든 아이템 리스트 출력
    buttons.addEventListener('click', (event) => onButtonClick(event, items)); //버튼이 클릭되면 이벤트를 처리할 수 있도록 이벤트를 인자로 전달
}

function onButtonClick(event, items) {
    const dataset = event.target.dataset;
    const key = dataset.key;
    const value = dataset.value;
    console.log("dataset :"+dataset);
    console.log("key :"+key);
    console.log("value :"+value);


    if (key == null || value == null) {
        return; //아무것도 처리하지 않고 함수를 끝냄
    }

    const filtered = items.filter((item) => item[key] === value);
    displayItems(filtered);
}

function displayItems(items) {
    //items를 html 요소로 변환하여 페이지에 표기
    const container = document.querySelector('.items');
    container.innerHTML = items.map((item) => createHTMLString(item)).join('');
}

//modal
const open = document.getElementById("open");
const close = document.getElementById("close");
const modal = document.querySelector(".modal-wrapper");
open.onclick = () => {
    modal.style.display = "flex";
};
close.onclick = () => {
    modal.style.display = "none";
};

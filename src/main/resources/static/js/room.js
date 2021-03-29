const Room = (function () {

    let page = 0;
    let size = 10;


    // TODO: Overlay
    getListRenderAll();

    function getListRenderAll(page) {
        console.log(page);
        const $list = $("#items");
        const $row = $("#chat-room-list-template").find("tr");
        $list.empty();

        const container = document.querySelector('.items');

        let searchType = $("#searchType").val();
        let search = $("#searchInput").val();

        Commons.ajaxGet("/api/chat/chat-room?page="+page+"&size="+size+"&sort=id&category="+searchType+"&name="+search, function(data) {
            if(Commons.isNotEmpty(data)) {
                displayItems(data);
                getPage(data);
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

        $('#room-category').find('option:first').attr('selected', 'selected');
        $('#room-name').val('');
        modal.style.display = "none";
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
    logo.addEventListener('click', () => displayItems(items)); //로고 클릭 시, 모든 아이템 리스트 출력
}

function searchChatRoomList()  {
    const searchType = $("#keywordType").val();
    const search = $("#keyword").val();

    $("#searchInput").val(search);
    $("#searchType").val(searchType);

    Room.getListRenderAll();
}

function displayItems(items) {
    //items를 html 요소로 변환하여 페이지에 표기
    const container = document.querySelector('.items');

    container.innerHTML = items.contents.map((item) => createHTMLString(item)).join('');
}

function getPage(data) {
    const startPage = data['startPage'];
    const endPage = data['endPage'];

    let block = "";

    block += `<li class="page-item">
                    <a class="page-link" href="javascript:Room.getListRenderAll(${startPage})" aria-disabled="true">Previous</a>
                </li>`;

    for (let i = startPage ; i <= endPage; i++) {
        if (data !== i) {
            block += `<li class="page-item"><a class="page-link" href="javascript:Room.getListRenderAll(${i})">${i}</a></li>`;
        } else {
            block += `<li class="page-item disabled"><a class="page-link">${i + 1}</a></li>`;
        }
    }

    block += `<li class="page-item">
                <a class="page-link" href="javascript:Room.getListRenderAll(${endPage})">Next</a>
              </li>`;

    $("#pagination").html(block);

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

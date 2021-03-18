// json파일로부터 데이터 받아오기
function loadItems() {
    return fetch('/js/data.json')
        .then((response) => response.json()) //받아온 데이터가 성공적이면 json으로 변환
        .then((json) => json.items); //json 안에 있는 item들을 리턴
}

function displayItems(items) {
    //items를 html 요소로 변환하여 페이지에 표기
    const container = document.querySelector('.items');
    container.innerHTML = items.map((item) => createHTMLString(item)).join('');
}

function createHTMLString(item) {
    return `
    <li class="item">
        <img src="${item.image}" alt="${item.type}" class="item__thumbnail" />
        <span class="item__description">${item.gender}, ${item.size}</span>
    </li>
    `;
}

function onButtonClick(event, items) {
    const dataset = event.target.dataset;
    const key = dataset.key;
    const value = dataset.value;

    if (key == null || value == null) {
        return; //아무것도 처리하지 않고 함수를 끝냄
    }

    const filtered = items.filter((item) => item[key] === value);
    displayItems(filtered);
}

//버튼이 클릭되었을 때 동작
function setEventListeners(items) {
    const logo = document.querySelector('.logo');
    const buttons = document.querySelector('.button-group-prepend'); //버튼이 모여있는 컨테이너에 이벤트 부여
    logo.addEventListener('click', () => displayItems(items)); //로고 클릭 시, 모든 아이템 리스트 출력
    buttons.addEventListener('click', (event) => onButtonClick(event, items)); //버튼이 클릭되면 이벤트를 처리할 수 있도록 이벤트를 인자로 전달
}

//main
loadItems()
    .then((items) => {
        displayItems(items);
        setEventListeners(items);
    })
    .catch(console.log);

const open = document.getElementById("open");
const close = document.getElementById("close");
const modal = document.querySelector(".modal-wrapper");
open.onclick = () => {
    modal.style.display = "flex";
};
close.onclick = () => {
    modal.style.display = "none";
};

function roomCreateSubmit(event) {
    event.submit();
    event.preventDefault();
}

const roomCreateForm = document.getElementById('roomCreateForm');
roomCreateForm.addEventListener('submit', roomCreateSubmit);
(function($) {
    let stompClient = null;
    let memberId = null;
    let roomId = null;
    let myAccess = false;
    let token = null;

	/**
	 * DOM객체만 로드 후에 socketUrl 업데이트 후 접근.
	 */
	$(document).ready(function() {
	    roomId = $("#roomId").val();
        let socket = new SockJS('/stomp');
        stompClient = Stomp.over(socket);

        Commons.ajaxGet("/chat/user", function(data) {
            token = data.token;
            stompClient.connect({"token": token}, connectionSuccess, connectionClose);
        })



		$("#btn-input").keyup(function(event) {
			if (event.keyCode == "13") {
				sendMessage(event);
			}
		});

		$("#btn-chat").click(function(event) {
			sendMessage(event);
		});

        $("#btnOut").click(function(event) {
            outRoom(event);
        });
	});

    function connectionSuccess() {
        stompClient.subscribe('/topic/chatting.'+roomId, onMessageReceived);

        stompClient.send("/app/"+roomId+"/chat.newUser", {"token": token}, JSON.stringify({
            roomId : roomId,
            sender : memberId,
            type : 'newUser'
        }));
	}

	function connectionClose(message) {
	    alert("서버와의 접속이 끊어졌습니다. \n" + message);
	    //location.href = "/logout";
	}

    function sendMessage(event) {
        let btnInput = $("#btn-input");
        let messageContent = btnInput.val();

        if (messageContent && stompClient) {
            let chatMessage = {
                roomId : roomId,
                sender : memberId,
                content : messageContent,
                type : 'CHAT'
            };

            stompClient.send("/app/"+roomId+"/chat.sendMessage", {"token": token}, JSON
                    .stringify(chatMessage));
            btnInput.val('');
        }
        event.preventDefault();
    }

    function onMessageReceived(payload) {
        let data = JSON.parse(payload.body);
        let type = data.type;

        if(["Leave","newUser","CHAT"].includes(type)) {
            if(["Leave","newUser"].includes(type)) {
                // 중복로그인이 발생한 경우 채팅방에 접속되었던 계정 전부 disconnect 처리
                if("newUser" === type) {
                    if(memberId === data.memberId) {
                        stompClient.disconnect(disconnectCallback)
                        return;
                    }
                    if(!myAccess) {
                        memberId = data.memberId;
                       myAccess = true;
                    }
                }
                // 채팅방 인원 정보 갱신.
                stompClient.send("/app/"+roomId+"/chat.callParticipants", {"token": token}, JSON.stringify({}))
            }

            memberId =  data.sender;
            let dateTime = moment(data.dateTime).format("YYYY-MM-DD HH:mm:ss");
            printMessage(data.content, data.sender, dateTime);
            return;
        }
        printParticipants(data);
    }

    // disconnect 처리 후 로그아웃
    function disconnectCallback(event) {
          alert("다른 곳에서 로그인되어 접속을 해지합니다.");
          location.href = "/logout";
    }

	/**
	 * 전송받은 내용을 뿌림.
	 */
	function printMessage(message, userName, dateTime) {

        let chat = $(".chat-section ul.listSelectGroup");
        let wrap = $(document.createElement("li")).addClass("left").addClass(
				"clearfix");

        let chatBody = $(document.createElement("div")).addClass("chat-body")
				.addClass("clearfix");

        let chatBodyHeader = $(document.createElement("div"))
				.addClass("header");
        let primaryFont = $(document.createElement("strong")).addClass(
				"primary-font");
        let textMuted = $(document.createElement("small")).attr({
			"class" : "pull-right text-muted"
		});
        let glyphiconTime = $(document.createElement("span")).attr({
			"class" : "glyphicon glyphicon-time"
		});
        let content = $(document.createElement("p")).text(message);


		userName = userName ? userName : noti.chatapp.dto.ChatMessage.memberId;
		primaryFont.text(userName);
		chatBodyHeader.append(primaryFont);
		textMuted.append(glyphiconTime).text(dateTime);
		chatBodyHeader.append(textMuted);

		chatBody.append(chatBodyHeader);
		chatBody.append(content);

		wrap.append(chatBody);

		chat.append(wrap);

        let panelBody = $(".listMessageGroup");
        let chatHeight = panelBody.find(".listSelectGroup").height();
		panelBody.scrollTop(chatHeight);
	}

    function outRoom(event){
        window.location.href = "/chat/room";
    }

    /**
     * 유저정보를 오른쪽에 업데이트
     */
    function printParticipants(participantData) {
        let participantUsers = $(".participant-section ul.participant-users");
        // 자식노드 전부 삭제 후에 현재 유저를 넣음.
        participantUsers.empty();
        $.each(participantData, function(ind, obj) {
            let wrap = $(document.createElement("li")).attr({
                "class" : "left clearfix"
            });

            let userBody = $(document.createElement("div")).attr({
                "class" : "user-body clearfix"
            });

            let userBodyHeader = $(document.createElement("div")).addClass(
                "header");
            let primaryFont = $(document.createElement("strong")).addClass(
                "primary-font");

            primaryFont.text(obj.memberId);

            userBodyHeader.append(primaryFont);
            userBody.append(userBodyHeader);

            wrap.append(userBody);

            participantUsers.append(wrap);
        });
    }

})(jQuery);
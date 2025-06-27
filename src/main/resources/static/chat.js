// 메시지를 <li> 형태로 만들어서 .messages 에 추가
function appendMessage(msg) {
    const list = document.getElementById('messageList');
    const li   = document.createElement('li');

    // 나(you)인지 상대(sender)인지 구분
    if (msg.sender === username) {
        li.classList.add('you');
    }

    // 아바타(이니셜) + 텍스트
    const avatar = document.createElement('div');
    avatar.className = 'avatar';
    avatar.textContent = msg.sender.charAt(0).toUpperCase();

    const text = document.createElement('div');
    text.className = 'text';
    text.textContent = msg.content;

    li.appendChild(avatar);
    li.appendChild(text);
    list.appendChild(li);

    // 스크롤 맨 아래로
    list.scrollTop = list.scrollHeight;
}

// 기존 구독 콜백에서 appendMessage(msg) 호출하도록 변경
stompClient.subscribe('/topic/public', frame => {
    const msg = JSON.parse(frame.body);
    appendMessage(msg);
});

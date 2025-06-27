package kr.ac.kopo.cjj.myapp.controller;

// ChatController.java
import kr.ac.kopo.cjj.myapp.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    // 클라이언트가 /app/chat.send 로 보낸 메세지를 /topic/public으로 브로드캐스트
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }

    // 사용자가 입장할 때
    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public ChatMessage join(ChatMessage message) {
        message.setType(ChatMessage.MessageType.JOIN);
        message.setContent(message.getSender() + "님이 입장했습니다.");
        return message;
    }

    /** 채팅 페이지 렌더링 */
    @GetMapping("/chat")
    public String chatPage() {
        // src/main/resources/templates/chat.html 을 반환합니다
        return "chat";
    }
}


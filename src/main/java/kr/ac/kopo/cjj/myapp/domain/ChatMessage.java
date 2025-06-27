package kr.ac.kopo.cjj.myapp.domain;
// ChatMessage.java

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String sender;
    private String content;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
    // getters/setters 생략
}

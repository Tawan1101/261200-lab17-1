package com.example.chat.configs;

import com.example.chat.models.ChatMessage;
import com.example.chat.models.MessageType;
import com.example.chat.services.UserCTServices;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username  != null) {
            UserCTServices.decrementUserCount();
            ChatMessage chatMessage = ChatMessage.buildChatmessage(username + "has left the chat." , username , MessageType.LEAVE);
            messagingTemplate.convertAndSend("/topic/messages" , chatMessage);
            messagingTemplate.convertAndSend("/topic/userCount", UserCTServices.getUserCount());
        }
    }
}

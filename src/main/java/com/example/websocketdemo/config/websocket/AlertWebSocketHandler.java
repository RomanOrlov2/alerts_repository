package com.example.websocketdemo.config.websocket;

import com.example.websocketdemo.domain.AlertMessage;
import com.example.websocketdemo.repository.AlertMessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RequiredArgsConstructor
public class AlertWebSocketHandler extends AbstractWebSocketHandler {

    Logger logger = getLogger(AlertWebSocketHandler.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final AlertMessageRepository alertMessageRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info("Alert text message received");
        String payload = message.getPayload();
        messagingTemplate.convertAndSend("/topic/alerts", saveMessage(payload));
        session.sendMessage(message);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        logger.info("Alert binary message received");
        String content = new String(message.getPayload().array());
        messagingTemplate.convertAndSend("/topic/alerts", saveMessage(content));
        session.sendMessage(message);
    }

    private AlertMessage saveMessage(String content) {
        AlertMessage alertMessage = new AlertMessage();
        alertMessage.setMessage(content);
        alertMessage.setDate(new Date());
        return alertMessageRepository.save(alertMessage);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    }
}

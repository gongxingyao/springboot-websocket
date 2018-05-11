package com.jexy.springbootwebsocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created At 2018/5/10 17:18
 * Author's name gongxingyao
 * email:gongxingyao@bignox.com
 */
@Service
public class SocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger;
    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
        logger = LoggerFactory.getLogger(SocketHandler.class);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("成功建立socket链接");
        users.add(session);
        logger.info("恭喜你是第"+users.size()+"的连接上的用户");
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            session.sendMessage(message);
            logger.info("发送消息"+message.getPayload());
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error(session.getAttributes().get("userId") + "连接异常");
        logger.error(exception.toString());
        users.remove(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session.getAttributes().get("userId") + "断开链接");
        users.remove(session);
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                handleTextMessage(user, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给指定的网红发送信息
     *
     * @param userIds
     * @param message
     */
    public void sendMessageToSpecifiedUser(List<String> userIds, TextMessage message) {
        for (WebSocketSession user : users) {
            int i = 0;
            if (user.getAttributes().get("userId").equals(userIds.get(i))) {
                i++;
                try {
                    handleTextMessage(user, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


}

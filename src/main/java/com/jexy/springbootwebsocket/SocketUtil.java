package com.jexy.springbootwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.Date;
import java.util.List;

/**
 * Created At 2018/5/10 17:43
 * Author's name gongxingyao
 * email:gongxingyao@bignox.com
 */
@Component
public class SocketUtil {
    @Autowired
    SocketHandler socketHandler;

    /**
     * 指定时间发送消息
     * @param message
     * @param pushDate
     */
    @Async
    public void sendDelayMessage( String message, Date pushDate) {
        try {
            long sleepTime=pushDate.getTime()-(new Date().getTime());
            Thread.sleep(sleepTime);
            socketHandler.sendMessageToUsers(new TextMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给指定的用户Id发送消息
     * @param userIds
     * @param message
     */
    public void sendMessageToSpecifiedUsers(List<String> userIds, String message) {
        socketHandler.sendMessageToSpecifiedUser(userIds,new TextMessage(message));
    }

    /**
     * 给所有用户发送消息
     *
     * @param message
     */
    public void sendMessage(String message) {
        socketHandler.sendMessageToUsers(new TextMessage(message));
    }
}

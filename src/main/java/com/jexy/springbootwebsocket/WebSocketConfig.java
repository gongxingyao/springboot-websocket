package com.jexy.springbootwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created At 2018/5/10 17:39
 * Author's name gongxingyao
 * email:gongxingyao@bignox.com
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter  implements WebSocketConfigurer {

    @Autowired
    SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //注册处理拦截器,拦截url为socketServer的请求
        webSocketHandlerRegistry.addHandler(socketHandler, "/socketServer").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
        //注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
        webSocketHandlerRegistry.addHandler(socketHandler, "/sockjs/socketServer").addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
    }
}

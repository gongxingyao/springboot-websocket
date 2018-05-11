package com.jexy.springbootwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
/**
 * Created At 2018/5/10 17:43
 * Author's name gongxingyao
 * email:gongxingyao@bignox.com
 */
@Controller
public class IndexCtrl {

    @Autowired
    SocketUtil socketUtil;

    @GetMapping("index")
    public String index(HttpServletRequest request){
        request.getSession().setAttribute("userId",UUID.randomUUID());
        return "Hello.html";
    }

    @GetMapping("send")
    @ResponseBody
    public String send(@RequestParam String message){
        socketUtil.sendMessage(message);
        return null;
    }
    @GetMapping("delayMessage")
    @ResponseBody
    public String sendDelayMessage(@RequestParam String message){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND,5000);
        socketUtil.sendDelayMessage(message,calendar.getTime());
        return null;
    }


}

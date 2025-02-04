package com.example.DrawLots.controller;

import com.example.DrawLots.service.QQLoginService;
import com.example.DrawLots.service.WeChatLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeChatLoginController
{
    private final QQLoginService qQLoginService;
    private final WeChatLoginService weChatLoginService;
    public WeChatLoginController(QQLoginService qQLoginService, WeChatLoginService weChatLoginService)
    {
        this.weChatLoginService=weChatLoginService;
        this.qQLoginService = qQLoginService;
    }

    //步骤1: 获取Authorization Code的URL
    //在微信登录中，这一步由前端完成。具体做法:
    //前端生成二维码引入链接，或者让用户点击链接： https://open.weixin.qq.com/connect/qrconnect?appid=wxca3387499a3b8185&redirect_uri=http://drawlots.billadom.top/wxMpSendMsgCallBack&response_type=code&scope=snsapi_login&state=Web request remain stable#wechat_redirect



    //后续步骤:
    //请注意:执行这个接口时，要填入参数code和state，这两个其实就是步骤一的返回结果。
    @GetMapping("/WeChatauth/login")
    public String loginByAuthUrl(@RequestParam String code, @RequestParam String state)
    {
        if(state.equals("Web request remain stable"))
        {
            return weChatLoginService.getAccessTokenAndOpenId(code);
        }
        else{
            return "State Error";
        }
    }
}

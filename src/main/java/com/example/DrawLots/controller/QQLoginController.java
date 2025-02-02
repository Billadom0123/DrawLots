package com.example.DrawLots.controller;

import com.example.DrawLots.service.QQLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QQLoginController
{
    private final QQLoginService qQLoginService;
    public QQLoginController(QQLoginService qQLoginService) {
        this.qQLoginService = qQLoginService;
    }

    // 步骤1: 获取Authorization Code的URL
    @GetMapping("/QQauth/getAuthUrl")
    public String getAuthUrl() {
        String clientId = "";  // 实际的client_id。即申请QQ登录成功后，分配给应用的appid。
        String redirectUri = "";  // 实际的redirect_uri
        String state = "Web request remain stable";
        String scope = "get_user_info";
        String display = "mobile";
        return qQLoginService.getAuthorizationCode(clientId, redirectUri, state, scope, display);
    }

    // 合并后的接口，直接根据Authorization Code的URL返回 access_token 和 openid。
    // 填写变量时，可参考上方注释部分。
    @GetMapping("/QQauth/login")
    public String loginByAuthUrl(@RequestParam String code, @RequestParam String state) {
        if(state.equals("Web request remain stable")) {
            String clientId = "";  // 实际的client_id。即申请QQ登录成功后，分配给应用的appid。
            String clientSecret = "";//申请QQ登录成功后，分配给网站的appkey。
            String redirectUri = "";
            // 获取 Access Token
            String accessToken = qQLoginService.getAccessToken(code, redirectUri, clientId, clientSecret);
            if (accessToken.startsWith("Error")) {
                return accessToken;  // 返回错误信息
            }

            // 获取 OpenID
            String openId = qQLoginService.getUserOpenId(accessToken);
            if (openId.startsWith("Error")) {
                return openId;  // 返回错误信息
            }

            // 返回 access_token 和 openid
            return "access_token=" + accessToken + "&&openid=" + openId;
        }
        else{
            return "State Error";
        }
    }
}

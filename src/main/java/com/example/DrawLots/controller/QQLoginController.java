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
    @GetMapping("/auth/getAuthUrl")
    public String getAuthUrl() {
        String clientId = "";  // 实际的client_id。即申请QQ登录成功后，分配给应用的appid。
        String redirectUri = "";  // 实际的redirect_uri
        String state = "Web request remain stable";
        String scope = "get_user_info";
        String display = "mobile";
        return qQLoginService.getAuthorizationCode(clientId, redirectUri, state, scope, display);
    }

    // 步骤2: 回调接口，获取Access Token
    @GetMapping("/auth/callback")
    public String getAccessTokenByCode(@RequestParam String code, @RequestParam String state) {
        //获取到数据了
        // 使用 code 获取 access token
        if(state.equals("Web request remain stable")) {
            String clientId = "";
            String clientSecret = "";//申请QQ登录成功后，分配给网站的appkey。
            String redirectUri = "";
            String accessToken = qQLoginService.getAccessToken(code, redirectUri, clientId, clientSecret);
            return accessToken;
        }else{
            return "State Error";
        }
    }

    // 步骤3: 获取用户的OpenID
    @GetMapping("/auth/getOpenId")
    public String getOpenId(@RequestParam String accessToken) {
        // 获取用户的 OpenID
        return qQLoginService.getUserOpenId(accessToken);
    }

    // 处理错误情况，例如用户取消授权时。出错时，QQ返回的错误信息就是这个格式。
    @GetMapping("/auth/error")
    public String handleError(@RequestParam String error, @RequestParam String error_description) {
        // 错误信息
        return "Authorization failed, error: " + error + ", description: " + error_description;
    }
}

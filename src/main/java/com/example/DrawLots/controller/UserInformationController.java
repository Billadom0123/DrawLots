package com.example.DrawLots.controller;

import com.example.DrawLots.model.vo.QQAndWeChatUserInformationShow;
import com.example.DrawLots.service.UserInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInformationController
{
    private final UserInformationService userInformationService;
    public UserInformationController(UserInformationService userInformationService)
    {
        this.userInformationService = userInformationService;
    }

    @GetMapping("/QQUserInformation")//获取用QQ登录的用户信息，包括昵称和头像
    public QQAndWeChatUserInformationShow getQQUserInformation(@RequestParam String accessToken, @RequestParam String openId)
    {
        return userInformationService.getQQUserInformation(accessToken, openId);
    }
    @GetMapping("/WeChatUserInformation")//获取用微信登录的用户信息，包括昵称和头像
    public QQAndWeChatUserInformationShow getWeChatUserInformation(@RequestParam String accessToken, @RequestParam String openId)
    {
        return userInformationService.getWeChatUserInformation(accessToken, openId);
    }
}

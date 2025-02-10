package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.User;
import com.example.DrawLots.model.vo.QQAndWeChatUserInformationShow;
import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.service.UserInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInformationController
{
    private final UserInformationService userInformationService;
    private final UserMapper userMapper;

    public UserInformationController(UserInformationService userInformationService, UserMapper userMapper)
    {
        this.userInformationService = userInformationService;
        this.userMapper = userMapper;
    }

    //获取用户信息.完整信息
    @GetMapping("/getUserInformation")
    public User getUserInformation(@RequestParam int uid)//这里返回的user对象可能要json包裹一下
    {
        return userMapper.getUserByUid(uid);
    }

    //更改用户昵称
    @GetMapping("/updateUserNickname")
    public Response updateUserNickname(@RequestParam int uid,@RequestParam String nickname)
    {
        userMapper.updateNickname(nickname, uid);
        return Response.success("changed nickname successfully");
    }

    //以下两种方法是从腾讯和微信的接口获取的，不是从数据库，前端可能无需调用
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

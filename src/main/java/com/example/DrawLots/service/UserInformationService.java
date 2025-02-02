package com.example.DrawLots.service;

import com.example.DrawLots.model.po.QQUserInformation;
import com.example.DrawLots.model.po.WeChatUserInformation;
import com.example.DrawLots.model.vo.QQAndWeChatUserInformationShow;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserInformationService
{
    public QQAndWeChatUserInformationShow getQQUserInformation(String accessToken, String openId)
    {
        String appId = "";//待填写
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://graph.qq.com/user/get_user_info?"
                + "access_token="+accessToken+"&"
                + "oauth_consumer_key="+appId+"&"
                + "openid="+openId;

        String encodedUrl = UriUtils.encode(url, StandardCharsets.UTF_8);

        // 发送 GET 请求并获取返回结果
        QQUserInformation response = restTemplate.getForObject(encodedUrl, QQUserInformation.class);
        if(response.getRet() == 0){
            return new QQAndWeChatUserInformationShow(response);
        }else{
            return new QQAndWeChatUserInformationShow();
        }
    }

    public QQAndWeChatUserInformationShow getWeChatUserInformation(String accessToken, String openId)
    {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId;

        String encodedUrl = UriUtils.encode(url, StandardCharsets.UTF_8);

        // 发送 GET 请求并获取返回结果
        WeChatUserInformation response = restTemplate.getForObject(encodedUrl, WeChatUserInformation.class);
        if(response.getUnionid()!=null) {
            return new QQAndWeChatUserInformationShow(response);
        }
        else{
            return new QQAndWeChatUserInformationShow();
        }
    }
}

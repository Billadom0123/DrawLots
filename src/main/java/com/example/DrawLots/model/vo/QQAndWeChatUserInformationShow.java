package com.example.DrawLots.model.vo;

import com.example.DrawLots.model.po.QQUserInformation;
import com.example.DrawLots.model.po.WeChatUserInformation;

public class QQAndWeChatUserInformationShow
{
    private String nickname;
    private String faceImage;

    public QQAndWeChatUserInformationShow(QQUserInformation qqUserInformation)//将QQ用户信息包装返回给前端
    {
        this.nickname = qqUserInformation.getNickname();
        this.faceImage = qqUserInformation.getFigureurl_qq_1();
    }

    public QQAndWeChatUserInformationShow(WeChatUserInformation weChatUserInformation)//将WeChat用户信息包装返回给前端
    {
        this.nickname = weChatUserInformation.getNickname();
        this.faceImage = weChatUserInformation.getHeadimgurl();
    }

    public QQAndWeChatUserInformationShow()
    {
        this.nickname = "Error when getting user information";
        this.faceImage = "Error when getting user information";
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getFaceImage()
    {
        return faceImage;
    }

    public void setFaceImage(String faceImage)
    {
        this.faceImage = faceImage;
    }
}

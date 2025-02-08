package com.example.DrawLots.model.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    private Integer uid;// 用户id
    private String sid;// 学号
    private String name;// 姓名
    private String nickname;// 昵称
    private String face;// 头像
    private String password;// 密码

    private String unionid;//微信用户的唯一id


    public User(String sid, String name, String password) // sdu用户
    {
        this.sid = sid;
        this.name = name;
        this.password = password;
        this.nickname = "u_" + sid;
    }

    public User(String nickname,String face)//QQ或WeChat用户
    {
        this.nickname=nickname;
        this.face=face;
    }
}

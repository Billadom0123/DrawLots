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
    private Integer uid;
    private String sid;
    private String name;
    private String password;
    private String nickname;

    public User(String sid, String name, String password) {
        this.sid = sid;
        this.name = name;
        this.password = password;
        nickname = "u_" + sid;
    }
}

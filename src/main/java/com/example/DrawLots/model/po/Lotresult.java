package com.example.DrawLots.model.po;

import java.sql.Timestamp;

public class Lotresult {//已开奖的抽奖结果
    private int lotsId;
    private int uid;
    private String nickname;
    private Timestamp time;
    private Prize prize;

    // Getter 和 Setter 方法

    public int getLotsId()
    {
        return lotsId;
    }

    public void setLotsId(int lotsId)
    {
        this.lotsId = lotsId;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public void setTime(Timestamp time)
    {
        this.time = time;
    }

    public Prize getPrize()
    {
        return prize;
    }

    public void setPrize(Prize prize)
    {
        this.prize = prize;
    }
}

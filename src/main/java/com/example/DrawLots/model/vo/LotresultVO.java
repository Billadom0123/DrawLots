package com.example.DrawLots.model.vo;

import com.example.DrawLots.model.po.Lotresult;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class LotresultVO {
    private int uid;
    private String nickname;
    private Timestamp time;
    private String prize;

    public LotresultVO(Lotresult lotresult) {
        this.uid = lotresult.getUid();
        this.nickname = lotresult.getNickname();
        this.time = lotresult.getTime();
        if (lotresult.getPrize() != null) {
            this.prize = lotresult.getPrize().getName();
        } else {
            this.prize = "";
        }

    }
}

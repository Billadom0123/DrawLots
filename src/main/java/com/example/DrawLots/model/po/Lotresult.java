package com.example.DrawLots.model.po;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Lotresult {//已开奖的抽奖结果
    private int id;
    private int lotsId;
    private int uid;
    private String nickname;
    private Timestamp time;
    private Integer prizeId;//这里的prizeId对应的是奖品的id。

}

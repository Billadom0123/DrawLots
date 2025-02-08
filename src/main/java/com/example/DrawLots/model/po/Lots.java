package com.example.DrawLots.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Lots {//已发布的抽奖
    private Integer id;
    private int uid;
    private String nickname;

    private int type;//抽奖模式: 1、通用抽奖且按时间开奖。2、通用抽奖且按人数开奖。3、即开即抽

    private Timestamp startTime;
    private Timestamp endTime;
    private int joinLimit;
    private int joinMethod;
    private int joinedNumber;

    private int choice;//即开即抽里面，选择转盘，翻牌，随机数的选项

    private int randomRangeMin;
    private int randomRangeMax;
    private int randomNumber;

    private String textNotice;
    private String imageNotice;

    private boolean isFinished;//是否已开奖

}

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
    private int id;
    private int uid;
    private String nickname;
    private int type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int joinLimit;
    private int joinMethod;
    private int joinedNumber;
    private int choice;
    private int randomRangeMin;
    private int randomRangeMax;
    private int randomNumber;
}

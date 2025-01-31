package com.example.DrawLots.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Lots {
    private int id;
    private int uid;
    private String nickname;
    private String prizes;
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

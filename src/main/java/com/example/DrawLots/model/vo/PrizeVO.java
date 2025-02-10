package com.example.DrawLots.model.vo;

import com.example.DrawLots.model.po.Prize;
import lombok.Getter;

//为了实现json形式的数据返回
@Getter
public class PrizeVO {
    private String type;
    private String name;
    private int number;
    private String picture;
    private String description;

    public PrizeVO(Prize prize) {
        this.type = prize.getType();
        this.name = prize.getName();
        this.number = prize.getNumber();
        this.picture = prize.getPictureUrl();
        this.description = prize.getDescription();

    }
}

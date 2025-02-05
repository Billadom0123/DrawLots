package com.example.DrawLots.model.vo;

import com.example.DrawLots.model.po.Lots;
import lombok.Getter;

import java.sql.Timestamp;

//仅供实现json形式数据返回用
@Getter
public class LotsBriefVO {
    private int id;
    private Timestamp start;
    private Timestamp end;
    private int number;
    private String picture;

    public LotsBriefVO(Lots lots) {
        this.id = lots.getId();
        this.start = lots.getStartTime();
        this.end = lots.getEndTime();
        this.number = lots.getJoinedNumber();
        this.picture = lots.getImageNotice();
    }
}

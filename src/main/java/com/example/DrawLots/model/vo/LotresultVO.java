package com.example.DrawLots.model.vo;

import com.example.DrawLots.mapper.LotresultMapper;
import com.example.DrawLots.model.po.Lotresult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

@Getter
@Setter
public class LotresultVO {
    private int uid;
    private String nickname;
    private Timestamp time;
    private String prize;

    public LotresultVO(Lotresult lotresult) {

        this.uid = lotresult.getUid();
        this.nickname = lotresult.getNickname();
        this.time = lotresult.getTime();

    }
}

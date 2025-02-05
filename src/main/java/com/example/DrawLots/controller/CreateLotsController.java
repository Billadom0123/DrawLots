package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.vo.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Random;

@RestController
public class CreateLotsController {

    private final LotsMapper lotsMapper;
    public CreateLotsController(LotsMapper lotsMapper) {
        this.lotsMapper = lotsMapper;
    }
    @PostMapping("/lots/create/typical")
    public Response createTypicalLots(
            @RequestParam("uid") Integer uid,
            @RequestParam("nickname") String nickname,
            @RequestParam("type") Integer type,
            @RequestParam("endTime") Timestamp endTime,
            @RequestParam("joinLimit") Integer joinLimit,
            @RequestParam("joinMethod") Integer joinMethod,
            @RequestParam("textNotice") String textNotice,
            @RequestParam("imageNotice") String imageNotice) {

        Lots lots = new Lots();
        lots.setUid(uid);
        lots.setNickname(nickname);
        lots.setType(type);
        lots.setEndTime(endTime);
        lots.setJoinLimit(joinLimit);
        lots.setJoinMethod(joinMethod);
        lots.setJoinedNumber(0);
        lots.setChoice(0);
        lots.setRandomRangeMin(0);
        lots.setRandomRangeMax(1);
        lots.setRandomNumber(1);
        lots.setTextNotice(textNotice);
        lots.setImageNotice(imageNotice);
        lotsMapper.addNewLots(lots);
        return Response.success(lots);
    }
}

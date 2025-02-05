package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.vo.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

public class CreateLotsController {

    private final LotsMapper lotsMapper;
    public CreateLotsController(LotsMapper lotsMapper) {
        this.lotsMapper = lotsMapper;
    }
    @PostMapping("/lots/create")
    public Response joinLots(
            @RequestParam("uid") Integer uid,
            @RequestParam("nickname") String nickname,
            @RequestParam("type") Integer type,
            @RequestParam("startTime") Timestamp startTime,
            @RequestParam("endTime") Timestamp endTime,
            @RequestParam("joinLimit") Integer joinLimit,
            @RequestParam("joinMethod") Integer joinMethod,
            @RequestParam("joinedNumber") Integer joinedNumber,
            @RequestParam("choice") Integer choice,
            @RequestParam("randomRangeMin") Integer randomRangeMin,
            @RequestParam("randomRangeMax") Integer randomRangeMax,
            @RequestParam("randomNumber") Integer randomNumber,
            @RequestParam("textNotice") String textNotice,
            @RequestParam("imageNotice") String imageNotice) {

        Lots lots = new Lots();
        lots.setUid(uid);
        lots.setNickname(nickname);
        lots.setType(type);
        lots.setStartTime(startTime);
        lots.setEndTime(endTime);
        lots.setJoinLimit(joinLimit);
        lots.setJoinMethod(joinMethod);
        lots.setJoinedNumber(joinedNumber);
        lots.setChoice(choice);
        lots.setRandomRangeMin(randomRangeMin);
        lots.setRandomRangeMax(randomRangeMax);
        lots.setRandomNumber(randomNumber);
        lots.setTextNotice(textNotice);
        lots.setImageNotice(imageNotice);

        lotsMapper.addNewLots(lots);

        return Response.success(lots);
    }
}

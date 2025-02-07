package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.Prize;
import com.example.DrawLots.model.vo.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Random;

@RestController
public class CreateLotsController {

    private final LotsMapper lotsMapper;
    private final UserMapper userMapper;
    private final PrizeMapper prizeMapper;

    public CreateLotsController(LotsMapper lotsMapper, UserMapper userMapper, PrizeMapper prizeMapper) {
        this.lotsMapper = lotsMapper;
        this.userMapper = userMapper;
        this.prizeMapper = prizeMapper;
    }
    @PostMapping("/lots/create")
    public Response createTypicalLots(
            @RequestParam("uid") Integer uid,
            @RequestParam("type") Integer type,
            @RequestParam("endTime") Timestamp endTime,
            @RequestParam("joinLimit") Integer joinLimit,
            @RequestParam("joinMethod") Integer joinMethod,
            @RequestParam("textNotice") String textNotice,
            @RequestParam("imageNotice") String imageNotice,
            @RequestParam("prize") Prize [] prize) {

        Lots lots = new Lots();
        lots.setUid(uid);
        lots.setNickname(userMapper.getUserByUid(uid).getNickname());
        lots.setType(type);
        lots.setStartTime(new Timestamp(System.currentTimeMillis()));
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
        lots.setFinished(false);
        lotsMapper.addNewLots(lots);

        for(Prize p:prize)
        {
            prizeMapper.addNewPrize(p);
        }

        return Response.success(lots);
    }
}

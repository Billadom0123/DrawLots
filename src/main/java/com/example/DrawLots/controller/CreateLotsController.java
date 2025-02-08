package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.Prize;
import com.example.DrawLots.model.vo.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.DrawLots.service.ShortLinkService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Random;

@RestController
public class CreateLotsController {

    private final LotsMapper lotsMapper;
    private final UserMapper userMapper;
    private final PrizeMapper prizeMapper;
    private final ShortLinkService shortLinkService;

    public CreateLotsController(LotsMapper lotsMapper, UserMapper userMapper, PrizeMapper prizeMapper,ShortLinkService shortLinkService) {
        this.lotsMapper = lotsMapper;
        this.userMapper = userMapper;
        this.prizeMapper = prizeMapper;
        this.ShortLinkService = shortLinkService;
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
            @RequestBody Prize [] prize) {

        Lots lots = new Lots();
        lots.setUid(uid);
        lots.setNickname(userMapper.getUserByUid(uid).getNickname());
        lots.setType(type);
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        lots.setStartTime(startTime);
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

        Integer id = lotsMapper.getLotsIdByStartTimeAndUid(startTime,uid);
        for(Prize p:prize)
        {
            p.setLotsId(id);
            prizeMapper.addNewPrize(p);
        }

        return Response.success(lotsMapper.getLotsById(id));
    }

    @GetMapping("/lots/glink")
    public Response glink(@RequestParam("uid") Integer uid,
                          @RequestParam("id") Integer id) {
        return shortLinkService.genShortLink(uid, id);
    }

    @GetMapping("/{shortcode}")
    public Response getLots(@PathVariable("shortcode") String shortcode) {
        return shortLinkService.anaShortLink(shortcode);
    }
}

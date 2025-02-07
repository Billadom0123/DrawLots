package com.example.DrawLots.controller;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.Prize;
import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.service.ShortLinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Random;

@RestController
public class CreateLotsController {

    @Resource
    ShortLinkService shortLinkService;

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

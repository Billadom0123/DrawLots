package com.example.DrawLots.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        this.shortLinkService = shortLinkService;
    }
    @PostMapping("/lots/create")
    public Response createTypicalLots(
            @RequestParam(value = "uid") Integer uid,
            @RequestParam(value = "type") Integer type,
            @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "joinLimit") Integer joinLimit,
            @RequestParam(value = "joinMethod") Integer joinMethod,
            @RequestParam(value = "textNotice") String textNotice,
            @RequestParam(value = "imageNotice") String imageNotice,
            @RequestParam(value = "prize") String prize) {

        Lots lots = new Lots();
        lots.setUid(uid);
        lots.setNickname(userMapper.getUserByUid(uid).getNickname());
        lots.setType(type);
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        startTime.setNanos(0);
        lots.setStartTime(startTime);
        lots.setEndTime(new Timestamp(Long.parseLong(endTime)));
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
        System.out.println(startTime);
        Integer id = lotsMapper.getLotsIdByStartTimeAndUid(startTime,uid);
        JSONArray ja = JSONArray.parseArray(prize);

        for(int i = 0;i < ja.size();i++)
        {
            JSONObject jo = ja.getJSONObject(i);
            Prize p = new Prize();
            p.setLotsId(id);
            p.setType(jo.getInteger("type"));
            p.setName(jo.getString("name"));
            p.setNumber(jo.getInteger("number"));
            p.setPictureUrl(jo.getString("picture"));
            p.setDescription(jo.getString("description"));
            prizeMapper.addNewPrize(p);
        }

        return Response.success(id);
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

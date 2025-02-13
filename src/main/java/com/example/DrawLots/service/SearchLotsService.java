package com.example.DrawLots.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.DrawLots.mapper.LotresultMapper;
import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.model.po.Lotresult;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.Prize;
import com.example.DrawLots.model.vo.LotresultVO;
import com.example.DrawLots.model.vo.LotsBriefVO;
import com.example.DrawLots.model.vo.PrizeVO;
import com.example.DrawLots.model.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchLotsService {
    @Autowired
    LotsMapper lotsMapper;
    @Autowired
    LotresultMapper lotresultMapper;
    @Autowired
    PrizeMapper prizeMapper;
    @Autowired
    private DrawLotsService drawLotsService;

    public Response searchJoinedLots(Integer uid) {
        int[] JoinedLotsIds = lotresultMapper.getLotsIdbyUid(uid);
        List<LotsBriefVO> JoinedLots= new ArrayList<>();
        for (int i = 0; i < JoinedLotsIds.length; i++) {
            JoinedLots.add(new LotsBriefVO(lotsMapper.getLotsById(JoinedLotsIds[i])));
        }
        JSONArray JoinedLotsBriefVOs = JSONArray.parseArray(JSONObject.toJSONString(JoinedLots));
        return Response.success(JSONObject.toJSONString(JoinedLotsBriefVOs));
    }

    public Response searchCreatedLots(Integer uid) {
        List<Lots> CreatedLots = lotsMapper.getLotsByUid(uid);
        ArrayList<LotsBriefVO> CreatedLotsBriefVOs = new ArrayList<>();
        for (Lots createdLots : CreatedLots) {
            CreatedLotsBriefVOs.add(new LotsBriefVO(createdLots));
        }
        JSONArray CreatedLotsJA = JSONArray.parseArray(JSONObject.toJSONString(CreatedLotsBriefVOs));
        return Response.success(JSONObject.toJSONString(CreatedLotsJA));
    }

    public Response searchLotsDetails(Integer id) {//这里是lotId
        Lots lots = lotsMapper.getLotsById(id);

        JSONObject LotsFullVO = new JSONObject();


        JSONObject userJO = new JSONObject();
        userJO.put("uid", lots.getUid());
        userJO.put("nickname", lots.getNickname());
        LotsFullVO.put("user", userJO);


        List<Prize> prizes = prizeMapper.getPrizeByLotsId(lots.getId());
        ArrayList<PrizeVO> prizeVOs= new ArrayList<>();
        for (Prize prize : prizes) {
            prizeVOs.add(new PrizeVO(prize));
        }
        JSONArray prizesJA = JSONArray.parseArray(JSONObject.toJSONString(prizeVOs));
        LotsFullVO.put("prizes", prizesJA);


        JSONObject infoVO = new JSONObject();
        infoVO.put("type", lots.getType());

        JSONObject time = new JSONObject();
        time.put("start", lots.getStartTime());
        time.put("end", lots.getEndTime());
        infoVO.put("time", time);

        JSONObject join = new JSONObject();
        join.put("number",lots.getJoinLimit());
        join.put("method",lots.getJoinMethod());
        infoVO.put("join", join);

        infoVO.put("choice",lots.getChoice());

        JSONObject random = new JSONObject();
        random.put("range",JSONArray.parseArray(JSONObject.toJSONString(new int[]{lots.getRandomRangeMin(),lots.getRandomRangeMax()})));
        random.put("number",lots.getRandomNumber());
        infoVO.put("random", random);

        infoVO.put("text_notice",lots.getTextNotice());
        infoVO.put("image_notice",lots.getImageNotice());

        LotsFullVO.put("info", infoVO);

        //此时检测是否还没开奖，可能要执行开奖
        if(!lots.isFinished())
        {
            //按时间开奖，到达抽奖截止时间了，停止抽奖
            if (lots.getEndTime().getTime() <= System.currentTimeMillis() && lots.getType() == 1)
            {
                //开奖
                Response response =drawLotsService.finishLots(id);

            }

            //按人数开奖，到达抽奖最大人数了，停止抽奖
            if (lots.getJoinedNumber() >= lots.getJoinLimit() && lots.getType() == 2)
            {
                //开奖
                Response response =drawLotsService.finishLots(id);
            }

            //开奖之后，标记抽奖状态为"true"，即已开奖
            lotsMapper.updateIsFinished(id);
        }

        List<Lotresult> Lotresults = lotresultMapper.getLotresults(id);
        List<LotresultVO> LotresultVOs = new ArrayList<>();
        for (Lotresult lotresult : Lotresults) {
            LotresultVO lotresultVO = new LotresultVO(lotresult);
            if(lotresult.getPrizeId() != 0) {
                lotresultVO.setPrize(prizeMapper.getPrizeById(lotresult.getPrizeId()).getName());
            } else { lotresultVO.setPrize("");}
            LotresultVOs.add(lotresultVO);
        }
        JSONArray LotresultJA = JSONArray.parseArray(JSONObject.toJSONString(LotresultVOs));
        LotsFullVO.put("results", LotresultJA);

        return Response.success(JSONObject.toJSONString(LotsFullVO));
    }
}

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

    public Response searchLotsDetails(Integer sid) {
        Lots lots = lotsMapper.getLotsById(sid);

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

        random.put("text_notice",lots.getTextNotice());
        random.put("image_notice",lots.getImageNotice());
        infoVO.put("random", random);

        LotsFullVO.put("info", infoVO);


        List<Lotresult> Lotresults = lotresultMapper.getLotresults(lots.getId());
        List<LotresultVO> LotresultVOs = new ArrayList<>();
        for (Lotresult lotresult : Lotresults) {
            LotresultVOs.add(new LotresultVO(lotresult));
        }
        JSONArray LotresultJA = JSONArray.parseArray(JSONObject.toJSONString(LotresultVOs));
        LotsFullVO.put("results", LotresultJA);

        return Response.success(JSONObject.toJSONString(LotsFullVO));
    }
}

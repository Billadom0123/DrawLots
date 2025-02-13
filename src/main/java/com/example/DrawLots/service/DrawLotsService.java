package com.example.DrawLots.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.DrawLots.mapper.LotresultMapper;
import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lotresult;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.Prize;
import com.example.DrawLots.model.vo.PrizeVO;
import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class DrawLotsService {

    @Autowired
    private LotsMapper lotsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LotresultMapper lotresultMapper;
    @Autowired
    private PrizeMapper prizeMapper;

    public Response getLotsInfo(Integer id) {
        Lots lots = lotsMapper.getLotsById(id);
        if (lots == null) {
            return Response.failure(500,"dont find any");
        }

        //直接创建json形式的LotsVO，参考接口文档
        JSONObject LotsVO = new JSONObject();


        JSONObject userJO = new JSONObject();
        userJO.put("uid", lots.getUid());
        userJO.put("nickname", lots.getNickname());
        LotsVO.put("user", userJO);


        List<Prize> prizes = prizeMapper.getPrizeByLotsId(lots.getId());
        ArrayList<PrizeVO> prizeVOs= new ArrayList<>();
        for (Prize prize : prizes) {
            prizeVOs.add(new PrizeVO(prize));
        }
        JSONArray prizesJA = JSONArray.parseArray(JSONObject.toJSONString(prizeVOs));
        LotsVO.put("prizes", prizesJA);


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

        LotsVO.put("info", infoVO);


        return Response.success(JSONObject.toJSONString(LotsVO));
    }

    public Response joinLots(Integer id,Integer uid) {
        Lots lots = lotsMapper.getLotsById(id);
        if (lots == null) {
            return Response.failure(500,"dont find any");
        }
        if (new Timestamp(System.currentTimeMillis()).compareTo(lots.getEndTime()) >= 0) {
            return Response.failure(500,"time's up.");
        }
        if (lots.getJoinedNumber() == lots.getJoinLimit()) {
            return Response.failure(500,"lots is full");
        }

        //不能重复加入抽奖
        int count = lotresultMapper.checkIfRecordExists(id,uid);
        if(count>0){
            return Response.failure(500,"You have already joined");
        }

        Lotresult lotresult = new Lotresult();

        lotresult.setLotsId(id);
        lotresult.setUid(uid);
        lotresult.setNickname(userMapper.getUserByUid(uid).getNickname());
        lotresult.setTime(new Timestamp(System.currentTimeMillis()));
        lotresult.setPrizeId(0);
        lotresultMapper.addNewLotresult(lotresult);//成功加入此次抽奖

        lots.setJoinedNumber(lots.getJoinedNumber() + 1);
        lotsMapper.updateJoinedNumber(lots.getJoinedNumber(), id);

        /*if(lots.getJoinedNumber()==lots.getJoinLimit())//到达抽奖最大人数了，可能要停止抽奖
        {

        }*/

        return Response.success("Successfully joined");
    }

    public Response finishLots(Integer lotId) //为lotsId这次抽奖开奖，并获取开奖结果,这里是通用抽奖
    {
        Lots lots = lotsMapper.getLotsById(lotId);

        int [] prizeList = new int[lots.getJoinedNumber()];

        for(int i=0;i<lots.getJoinedNumber();i++)
        {
            prizeList[i] = 0;
        }

        for(int i=0;i<lots.getJoinedNumber();i++)//设置奖项序号列，0表示未中奖
        {

            for(int j=1;j<=prizeMapper.getPrizeTypes(lotId);j++)
            {
                for(int k=0;k<prizeMapper.getPrizeNumber(lotId,j);k++)
                {
                    prizeList[i] = j;
                }
            }

        }
        //设置uid序号列，打乱，然后与各个奖项配对
        int [] uidList = lotresultMapper.getLotresultUid(lotId);

        //打乱顺序。
            try
            {
                ArrayUtils.shuffleArray(uidList);

            }catch(Exception e)
            {
                return Response.failure(500,"error");
            }

        for(int i=0;i<lots.getJoinedNumber();i++)
        {
            lotresultMapper.updatePrizeId(prizeList[i],lotId,uidList[i]);
        }

        return Response.ok();

    }
}

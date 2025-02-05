package com.example.DrawLots.service;

import com.example.DrawLots.mapper.LotresultMapper;
import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.PrizeMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lotresult;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.vo.LotsVO;
import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Random;

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
        return Response.success(new LotsVO(lots));
    }

    public Response joinLots(Integer id,Integer uid) {
        Lots lots = lotsMapper.getLotsById(id);
        if (lots == null) {
            return Response.failure(500,"dont find any");
        }
        if (lots.getJoinedNumber() >= lots.getJoinLimit()) {
            return Response.failure(500,"lots is full");
        }

        Lotresult lotresult = new Lotresult();

        lotresult.setLotsId(id);
        lotresult.setUid(uid);
        lotresult.setNickname(userMapper.getUserByUid(uid).getNickname());
        lotresult.setTime(new Timestamp(System.currentTimeMillis()));
        lotresult.setPrize(null);
        lotresultMapper.addNewLotresult(lotresult);//成功加入此次抽奖

        lots.setJoinedNumber(lots.getJoinedNumber() + 1);
        lotsMapper.updateJoinedNumber(lots.getJoinedNumber(), id);

        /*if(lots.getJoinedNumber()==lots.getJoinLimit())//到达抽奖最大人数了，可能要停止抽奖
        {

        }*/

        return Response.success(new LotsVO(lots));
    }

    public Response getLotsResult(Integer lotId) //为lotsId这次抽奖开奖，并获取开奖结果,这里是通用抽奖
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

        for(int i=0;i<lots.getJoinedNumber();i++)//打乱顺序。为了防止人数不匹配，要用错误检测
        {
            try
            {
                ArrayUtils.shuffleArray(uidList);

            }catch(Exception e)
            {
                return Response.failure(500,"error");
            }
        }

        for(int i=0;i<lots.getJoinedNumber();i++)
        {
            lotresultMapper.updatePrizeId(prizeList[i],lotId,uidList[i]);
        }

        return Response.ok();

    }
}

package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lotresult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LotresultMapper
{

    //按照lotsId和uid查询Lotresult
    @Select("SELECT * FROM `results` WHERE `lots_id` = #{lotsId} AND `uid` = #{uid}")
    Lotresult getLotresultByLotsIdAndUid(Integer lotsId, Integer uid);


    //为uid这个用户的lots_id这次抽奖记录下抽奖结果Lotresult,注意此时并没有开奖,设置prize_id为0
    @Insert("INSERT INTO `results`(`lots_id`,`uid`,`nickname`,`join_time`,`prize_id`) VALUES(#{lotsId},#{uid},#{nickname},#{time},0)")
    void addNewLotresult(Lotresult lotresult);

    //返回所有参与了这次抽奖的结果
    @Select("SELECT * FROM `results` WHERE `lots_id` = #{lotsId}")
    List<Lotresult> getLotresults(Integer lotsId);


    //返回所有参与了这次抽奖的uid
    @Select("SELECT `uid` FROM `results` WHERE `lots_id` = #{lotsId}")
    int [] getLotresultUid(Integer lotsId);

    //为uid这个用户的lots_id这次抽奖记录下开奖之后的抽奖结果Lotresult,记录prize_id
    @Update("UPDATE `results` SET `prize_id`=#{prizeId} WHERE `lots_id` = #{lotsId} AND `uid` = #{uid}")
    void updatePrizeId(Integer prizeId, Integer lotsId, Integer uid);

    //返回uid对应用户参与的所有抽奖id
    @Select("SELECT `lots_id` FROM `results` WHERE `uid` = #{uid}")
    int [] getLotsIdbyUid(Integer uid);

    @Select("SELECT COUNT(*) FROM `results` WHERE `lots_id` = #{lotsId} AND `uid` = #{uid}")
    int checkIfRecordExists(Integer lotsId, Integer uid);

}
package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lotresult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LotresultMapper
{

    //按照id查询Lotresult
    @Select("SELECT * FROM `results` WHERE `id`=#{id}")
    Lotresult getLotresultByLotsId(int lotsId);

    //为uid这个用户的lots_id这次抽奖开奖，记录下抽奖结果Lotresult
    @Insert("INSERT INTO `results`(`lots_id`,`uid`,`nickname`,`join_time`,`prize_id`) VALUES(#{lotsId},#{uid},#{nickname},#{time},#{endTime},#{prize.number})")
    void addNewLotresult(Lotresult lotresult);

    //查询数据库中发布的抽奖数量
    @Select("SELECT COUNT(1) FROM `results`")
    int getLotresultNum();


}

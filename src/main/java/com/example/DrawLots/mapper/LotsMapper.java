package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LotsMapper {

    //按照id查询lots
    @Select("SELECT * FROM `lots` WHERE `id`=#{id}")
    Lots getLotsById(int id);

    //创造一个lots,uid表示发布者
    @Insert("INSERT INTO `lots`(`uid`,`nickname`,`type`,`start_time`,`end_time`,`join_limit`,`join_method`,`joined_number`,`choice`,`random_range_min`,`random_range_max`,`random_number`,`text_notice`,`image_notice`) VALUES(#{uid},#{nickname},#{type}),#{startTime},#{endTime}),#{joinLimit},#{joinMethod}),#{joinedNumber},#{choice}),#{randomRangeMin},#{randomRangeMax}),#{randomNumber},#{textNotice},#{imageNotice})")
    void addNewLots(Lots lots);

    //查询数据库中发布的抽奖数量
    @Select("SELECT COUNT(1) FROM `lots`")
    int getLotsNum();

    //删除对应抽奖
    @Delete("DELETE FROM `lots` WHERE `id`=#{id}")
    void deleteLot(Integer id);

}

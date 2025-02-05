package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LotsMapper {

    //按照id查询lots
    @Select("SELECT * FROM `lots` WHERE `id`=#{id}")
    Lots getLotsById(int id);

    //返回uid创建的所有抽奖
    @Select("SELECT * FROM `lots` WHERE `uid` = #{uid}")
    List<Lots> getLotsByUid(Integer uid);

    //创造一个lots,uid表示发布者
    @Insert("INSERT INTO `lots`(`uid`,`nickname`,`type`,`start_time`," +
            "`end_time`,`join_limit`,`join_method`,`joined_number`," +
            "`choice`,`random_range_min`,`random_range_max`," +
            "`random_number`,`text_notice`,`image_notice`) " +
            "VALUES(#{uid},#{nickname},#{type}),#{startTime}," +
            "#{endTime}),#{joinLimit},#{joinMethod}),#{joinedNumber}," +
            "#{choice}),#{randomRangeMin},#{randomRangeMax})," +
            "#{randomNumber},#{textNotice},#{imageNotice})")
    void addNewLots(Lots lots);

    //查询数据库中发布的抽奖数量
    @Select("SELECT COUNT(1) FROM `lots`")
    int getLotsNum();

    //更新参与人数
    @Update("UPDATE `lots` SET `joined_number`=#{joinedNumber} WHERE `id` = #{lotsId}")
    void updateJoinedNumber(int joinedNumber,int lotsId);

    //删除对应抽奖
    @Delete("DELETE FROM `lots` WHERE `id`=#{id}")
    void deleteLot(Integer id);


    // 更新nickname
    @Update("UPDATE `lots` SET `nickname`=#{nickname} WHERE `id`=#{id}")
    void updateNickname(@Param("id") Integer id, @Param("nickname") String nickname);

    // 更新join_limit
    @Update("UPDATE `lots` SET `join_limit`=#{joinLimit} WHERE `id`=#{id}")
    void updateJoinLimit(@Param("id") Integer id, @Param("joinLimit") Integer joinLimit);

    // 更新text_notice
    @Update("UPDATE `lots` SET `text_notice`=#{textNotice} WHERE `id`=#{id}")
    void updateTextNotice(@Param("id") Integer id, @Param("textNotice") String textNotice);

    // 更新image_notice
    @Update("UPDATE `lots` SET `image_notice`=#{imageNotice} WHERE `id`=#{id}")
    void updateImageNotice(@Param("id") Integer id, @Param("imageNotice") String imageNotice);
}

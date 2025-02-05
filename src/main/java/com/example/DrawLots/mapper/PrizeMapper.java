package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lotresult;
import com.example.DrawLots.model.po.Prize;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PrizeMapper
{
    //按照lots_id查询
    @Select("SELECT * FROM `prizes` WHERE `lots_id`=#{lotsId}")
    Lotresult getPrizeByLotsId(int lotsId);

    //添加lots_id这次抽奖的一项奖项
    @Insert("INSERT INTO `prizes`(`lots_id`,`type`,`name`,`number`,`picture_url`,`description`) VALUES(#{lotsId},#{type},#{name},#{number},#{pictureUrl},#{description})")
    void addNewPrize(Prize prize);

    //查询数据库中发布的抽奖数量
    @Select("SELECT COUNT(1) FROM `prizes`")
    int getPrizeNum();
}

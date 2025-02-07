package com.example.DrawLots.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;

@Mapper
public interface ShortLinkMapper {

    @Insert("INSERT INTO `slink`(`string`,`gen_time`,`link`) VALUES(#{string},#{time},#{link})")
    void insertShortLink(String string, Timestamp time,String link);

    @Select("SELECT `link` FROM `slink` WHERE `string`=#{string}")
    String selectShortLink(String string);
}

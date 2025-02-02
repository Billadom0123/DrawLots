package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.Lots;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LotsMapper {
    @Select("SELECT * FROM `lots` WHERE `id`=#{id}")
    Lots getLotsById(int id);
}

package com.example.DrawLots.mapper;

import com.example.DrawLots.model.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //新增一条sdu_user记录
    @Insert("INSERT INTO `user`(`sid`,`name`,`nickname`,`password`) VALUES(#{sid},#{name},#{nickname},#{password})")
    void addNewSDUUser(User user);

    //新增一条qq_user记录
    @Insert("INSERT INTO `user`(`nickname`,`face`) VALUES(#{nickname},#{face})")
    void addNewQQUser(User user);

    //新增一条wechat_user记录
    @Insert("INSERT INTO `user`(`nickname`,`face`,`unionid`) VALUES(#{nickname},#{face},#{unionid})")
    void addNewWeChatUser(User user);

    //按uid查找对应用户
    @Select("SELECT * FROM `user` WHERE `uid`=#{uid}")
    User getUserByUid(Integer uid);

    //按sid查找对应的sdu用户
    @Select("SELECT * FROM `user` WHERE `sid`=#{sid}")
    User getUserBySid(String sid);

    //查询数据库中用户数量
    @Select("SELECT COUNT(1) FROM `user`")
    int getUserNum();

    //查询所有用户
    @Select("SELECT * FROM `user`")
    List<User> getAllUser();

    //按unionid查找对应的微信用户
    @Select("SELECT * FROM `user` WHERE `unionid`=#{unionid}")
    User getUserByUnionid(String unionid);

    //更新用户昵称
    @Update("UPDATE `user` SET `nickname`=#{nickname} WHERE `uid`=#{uid}")
    void updateNickname(String nickname, Integer uid);

    //判断昵称是否存在
    @Select("SELECT EXISTS(SELECT 1 FROM `user` WHERE `nickname`=#{nickname})")
    boolean judgeExistsOfNickname(String nickname);

    //判断unionid是否存在(微信用户唯一标识)
    @Select("SELECT EXISTS(SELECT 1 FROM `user` WHERE `unionid`=#{unionid})")
    boolean judgeExistsOfUnionid(String unionid);

    //更新用户头像
    @Update("UPDATE `user` SET `face`=#{face} WHERE `uid`=#{uid}")
    void updateFace(String face, Integer uid);

    //删除对应用户
    @Delete("DELETE FROM `user` WHERE `uid`=#{uid}")
    void deleteUser(Integer uid);
}

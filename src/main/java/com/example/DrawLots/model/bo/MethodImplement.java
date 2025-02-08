package com.example.DrawLots.model.bo;

import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.User;

public class MethodImplement implements SDULoginBO.Method
{
    private final UserMapper userMapper;
    public MethodImplement(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    @Override
    public User tryGetUser(String sid)
    {
        return userMapper.getUserBySid(sid);
    }

    @Override
    public User register(User newUser)
    {
        newUser.setNickname(newUser.getName());
        userMapper.addNewSDUUser(newUser);
        return userMapper.getUserBySid(newUser.getSid());
    }

}

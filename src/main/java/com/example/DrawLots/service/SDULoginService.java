package com.example.DrawLots.service;

import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.bo.MethodImplement;
import com.example.DrawLots.model.bo.SDULoginBO;
import com.example.DrawLots.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SDULoginService
{
    private final UserMapper userMapper;
    public SDULoginService(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    public User login(String sid, String password)
    {
        SDULoginBO sduLoginBO = new SDULoginBO();
        sduLoginBO.setSid(sid);
        sduLoginBO.setPassword(password);
        SDULoginBO.Method method = new MethodImplement(userMapper);
        return sduLoginBO.login(method);
    }
}

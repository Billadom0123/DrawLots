package com.example.DrawLots.service;

import com.example.DrawLots.mapper.LotsMapper;
import com.example.DrawLots.mapper.UserMapper;
import com.example.DrawLots.model.po.Lots;
import com.example.DrawLots.model.vo.LotsVO;
import com.example.DrawLots.model.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrawLotsService {

    @Autowired
    private LotsMapper lotsMapper;
    @Autowired
    private UserMapper userMapper;

    public Response getLotsInfo(Integer id) {
        Lots lots = lotsMapper.getLotsById(id);
        if (lots == null) {
            return Response.failure(500,"dont find any");
        }
        return Response.success(new LotsVO(lots));
    }

    public Response joinLots(Integer id,Integer uid) {
        Lots lots = lotsMapper.getLotsById(id);
        return Response.success(new LotsVO(lots));
    }
}

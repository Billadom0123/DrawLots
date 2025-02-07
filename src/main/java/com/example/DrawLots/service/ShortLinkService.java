package com.example.DrawLots.service;

import com.example.DrawLots.mapper.ShortLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import com.example.DrawLots.model.vo.Response;
import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ShortLinkService {

    @Autowired
    private ShortLinkMapper shortLinkMapper;

    public Response genShortLink(Integer uid,Integer id) {
        String uidmd5 = DigestUtils.md5DigestAsHex(String.valueOf(uid).getBytes());
        uidmd5 = DigestUtils.md5DigestAsHex(uidmd5.getBytes());
        String idmd5 = DigestUtils.md5DigestAsHex(String.valueOf(id).getBytes());
        idmd5 = DigestUtils.md5DigestAsHex(idmd5.getBytes());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String result = DigestUtils.md5DigestAsHex(uidmd5.concat(timestamp.toString()).concat(idmd5).getBytes()).substring(0, 8);
        shortLinkMapper.insertShortLink(result,timestamp,"http://drawlots.billadom.top/lots/info?id="+id);
        return Response.success("http://drawlots.billadom.top/"+result);

    }

    public Response anaShortLink(String code) {
        return Response.success(shortLinkMapper.selectShortLink(code));
    }
}
package com.example.DrawLots.controller;

import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.service.DrawLotsService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrawLotsController {

    @Resource
    private DrawLotsService drawLotsService;

    @GetMapping("/lots/info")
    public Response getLotsInfo(@RequestParam("id") Integer id)
    {
        return drawLotsService.getLotsInfo(id);
    }

    @PostMapping("/lots/join")
    public Response joinLots(@RequestParam("id") Integer id,
                             @RequestParam("uid") Integer uid)
    {
        return drawLotsService.joinLots(id,uid);
    }

    //给id这次抽奖开奖
    @GetMapping("/lots/finish")
    public Response finishLots(@RequestParam("id") Integer id)
    {

    }
}

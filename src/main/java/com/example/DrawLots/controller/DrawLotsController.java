package com.example.DrawLots.controller;

import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.service.DrawLotsService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrawLotsController {

    @Resource
    private DrawLotsService drawLotsService;

    /*@GetMapping("/lots/info")
    public Response getLotsInfo(@RequestParam("sid") String sid) {
        return drawLotsService.getLotsInfo(sid);
    }*/

}

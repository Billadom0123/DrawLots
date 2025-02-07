package com.example.DrawLots.controller;

import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.service.SearchLotsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchLotsController {
    @Resource
    private SearchLotsService searchLotsService;

    @GetMapping("/lots/joined")
    public Response searchJoinedLots(@RequestParam("uid") Integer uid){
        return searchLotsService.searchJoinedLots(uid);
    }

    @GetMapping("/lots/created")
    public Response searchCreateLots(@RequestParam("uid") Integer uid){
        return searchLotsService.searchCreatedLots(uid);
    }

    @GetMapping("/lots/detailedinfo")
    public Response searchLotsDetail(@RequestParam("id") Integer id){
        return searchLotsService.searchLotsDetails(id);
    }
}

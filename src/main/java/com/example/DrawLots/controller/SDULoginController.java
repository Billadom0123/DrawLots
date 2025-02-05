package com.example.DrawLots.controller;

import com.example.DrawLots.model.po.User;
import com.example.DrawLots.service.SDULoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SDULoginController
{
    private final SDULoginService sduLoginService;
    public SDULoginController(SDULoginService sduLoginService)
    {
        this.sduLoginService = sduLoginService;
    }
    @PostMapping("/SDULogin")
    public User getAuthUrl(@RequestParam String sid, @RequestParam String password)
    {
        return sduLoginService.login(sid, password);
    }
}

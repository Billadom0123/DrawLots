package com.example.DrawLots.controller;

import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.util.wxcallback.AesException;
import com.example.DrawLots.util.wxcallback.WXPublicUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*仅供实现回调功能授权使用，请勿更改*/

@RestController
public class wxController {
    /**
     * 微信公众号回调地址
     */
    @GetMapping("/wxMpSendMsgCallBack")
    public String shareTestCallBack(HttpServletRequest request) throws AesException {

        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }

}

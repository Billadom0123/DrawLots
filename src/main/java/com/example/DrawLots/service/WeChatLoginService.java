package com.example.DrawLots.service;

import com.example.DrawLots.model.po.WeChatLoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class WeChatLoginService
{
    private final RestTemplate restTemplate;
    public WeChatLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String getAccessTokenAndOpenId(String code)
    {
        String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String appId="wxca3387499a3b8185";
        String appSecret="89f7ed51173a54a0ab88acffbe9893e8";

        // 构建请求URL
        String url = String.format("%s?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                tokenUrl, appId, appSecret, code);

        String encodedUrl = UriUtils.encode(url, StandardCharsets.UTF_8);

        // 返回的json会自动转化为WeChatLoginResponse对象
        ResponseEntity<WeChatLoginResponse> response = restTemplate.getForEntity(encodedUrl, WeChatLoginResponse.class);

        return parseResponse(response.getBody());
    }

    public String parseResponse(WeChatLoginResponse response)
    {
        // 提取各个参数
        String accessToken = response.getAccess_token();
        int expiresIn = response.getExpires_in();
        String refreshToken = response.getRefresh_token();
        String openId = response.getOpenid();
        String scope = response.getScope();
        String unionid = response.getUnionid();

        if(accessToken != null&&unionid!=null)
        {
            return "accessToken:"+accessToken+"&&refreshToken:"+refreshToken+"&&openid:"+openId;
        }
        else {
            return "Error when getting access token and openid";
        }
    }

    //刷新access_token
    public String refreshAccessToken(String refreshToken)
    {
        String appId="wxca3387499a3b8185";
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appId+"&grant_type=refresh_token&refresh_token="+refreshToken;

        String encodedUrl = UriUtils.encode(url, StandardCharsets.UTF_8);
        ResponseEntity<WeChatLoginResponse> response = restTemplate.getForEntity(encodedUrl, WeChatLoginResponse.class);

        return parseRefreshResponse(response.getBody()); // 返回更新后的WeChatAccessTokenResponse对象
    }

    public String parseRefreshResponse(WeChatLoginResponse response)
    {
        // 提取各个参数
        String accessToken = response.getAccess_token();
        int expiresIn = response.getExpires_in();
        String refreshToken = response.getRefresh_token();
        String openId = response.getOpenid();
        String scope = response.getScope();

        if(accessToken != null)
        {
            return "accessToken:"+accessToken+"&&refreshToken:"+refreshToken+"&&openid:"+openId;
        }
        else {
            return "Error when refreshing access token and openid";
        }
    }
}

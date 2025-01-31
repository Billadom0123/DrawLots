package com.example.DrawLots.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class QQLoginService
{

    private final RestTemplate restTemplate;
    public QQLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAuthorizationCode(String clientId, String redirectUri, String state, String scope, String display) {
        // 创建GET请求的URL
        String url = "https://graph.qq.com/oauth2.0/authorize";
        String encodedRedirectUri = UriUtils.encode(redirectUri, StandardCharsets.UTF_8);//先把回调地址编码
        //String state="Web request remain stable";String scope="get_user_info";String display="mobile";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)//申请QQ登录成功后，分配给应用的appid。
                .queryParam("redirect_uri", encodedRedirectUri)//成功授权后的回调地址，必须是注册appid时填写的主域名下的地址.
                .queryParam("state", state)
                .queryParam("scope", scope)
                .queryParam("display", display);

        // 发起GET请求
        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        return response.getBody();
    }

    // 获取 Access Token 的请求
    public String getAccessToken(String code, String redirectUri, String clientId, String clientSecret) {
        String tokenUrl = "https://graph.qq.com/oauth2.0/token";

        // 构建请求参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", code)  // 使用收到的 code
                .queryParam("redirect_uri", redirectUri)  // 必须与注册的回调地址一致
                .queryParam("client_id", clientId)  // 应用appid
                .queryParam("client_secret", clientSecret);  // 应用app密钥

        // 发起GET请求，获取Access Token
        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        return(parseAccessTokenResponse(response.getBody()));
    }

    public String parseAccessTokenResponse(String response) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("?" + response);
        MultiValueMap<String, String> queryParams = builder.build().getQueryParams();

        // 从查询参数中提取各个参数
        String accessToken = queryParams.getFirst("access_token");
        String expiresIn = queryParams.getFirst("expires_in");//可用于功能改进，避免用户每隔60天都要重新授权。但是我先不改
        String refreshToken = queryParams.getFirst("refresh_token");//可用于功能改进，避免用户每隔60天都要重新授权。但是我先不改

        if(accessToken != null) {
            return accessToken;  // 返回 Access Token
        }
        else {
            return "Error when getting access token";
        }
    }

    // 使用 access_token 获取用户的 OpenID
    public String getUserOpenId(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me";

        // 构建请求参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_token", accessToken)
                .queryParam("fmt", "json"); // 请求返回 JSON 格式的数据

        // 发起 GET 请求，获取用户的 OpenID
        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        // 解析返回的结果
        String responseBody = response.getBody();

        // 返回用户的 OpenID
        return parseOpenId(responseBody);
    }

    // 解析返回的 JSON 数据，提取 openid
    private String parseOpenId(String responseBody) {
        try {
            // 使用 Jackson 解析 JSON
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("openid").asText();
        } catch (Exception e) {
            return "Error when getting OpenId";
        }
    }
}

package com.example.DrawLots.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private int code;
    private String message;
    private Object data;

    public static Response ok() {  //告知响应成功
        return new Response(200,"ok",null);
    }

    //success:成功且返回单个数据
    public static Response success(Object data) {  //返回响应参数
        return new Response(200,"success",data);
    }


    //failure:失败且无返回
    public static Response failure(int code, String msg) {  //告知操作失败，并返回原因
        return new Response(code, msg,null);
    }

    //常用失败返回
    public static Response failure(CommonErr commonErr) {
        return new Response(commonErr.getCode(),commonErr.getMessage(),null);
    }
}

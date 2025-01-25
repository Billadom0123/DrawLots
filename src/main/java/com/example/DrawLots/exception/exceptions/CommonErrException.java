package com.example.DrawLots.exception.exceptions;

import com.example.DrawLots.model.vo.CommonErr;
import com.example.DrawLots.model.vo.Response;
import com.example.DrawLots.exception.ExceptionReturn;

public class CommonErrException extends RuntimeException implements ExceptionReturn {
    protected final CommonErr ERROR;

    public CommonErrException(CommonErr err) {
        ERROR = err;
    }

    //静态构造器
    public static CommonErrException raise(CommonErr err) {
        return new CommonErrException(err);
    }

    @Override
    public String getMessage() {
        return ERROR.getMessage();
    }

    @Override
    public Response toResponse() {
        return Response.failure(ERROR);
    }
}

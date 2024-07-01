package com.riteny.exception;

import com.riteny.util.exception.core.api.CommonApiExceptionHandler;
import com.riteny.util.exception.core.exception.CommonExceptionStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TestApiCommonApiExceptionHandler implements CommonApiExceptionHandler<TestApiCommonApiException, JSONObject> {

    @Override
    public JSONObject handler(TestApiCommonApiException e, HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("resultCode", CommonExceptionStatus.STATUS_ERROR);
        jsonObject.put("resultMsg", e.getMessage());
        jsonObject.put("data", e.getClass() + ":" + e.getErrorMsg());

        return jsonObject;
    }
}

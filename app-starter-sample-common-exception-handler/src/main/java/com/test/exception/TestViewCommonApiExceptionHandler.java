package com.test.exception;

import com.riteny.util.exception.core.view.CommonViewExceptionHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class TestViewCommonApiExceptionHandler implements CommonViewExceptionHandler<TestViewCommonApiException> {

    @Override
    public void handler(TestViewCommonApiException e, HttpServletRequest request, HttpServletResponse response) {

        try {
            response.getWriter().write("Jump to the other page .");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}

package com.riteny.exception;

import com.riteny.util.exception.core.exception.CommonApiException;

public class TestApiCommonApiException extends CommonApiException {

    public TestApiCommonApiException(String errorCode, String errorMsg, String langType) {
        super(errorCode, errorMsg, langType);
    }
}

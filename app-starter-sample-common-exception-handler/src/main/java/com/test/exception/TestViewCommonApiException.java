package com.test.exception;

import com.riteny.util.exception.core.exception.CommonApiException;
import com.riteny.util.exception.core.exception.CommonViewException;

public class TestViewCommonApiException extends CommonViewException {
    public TestViewCommonApiException(String errorCode, String errorMsg, String langType) {
        super(errorCode, errorMsg, langType);
    }
}

package com.blackdog.onetwo.adoc;

import com.blackdog.onetwo.configuration.response.error.ErrorCode;
import com.blackdog.onetwo.domain.common.enums.BaseEnum;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.user.enums.*;

public class CodeContentGenerator {

    public static void main(String[] args) {
        System.out.println(getCodeAdocContentString(ErrorCode.class));
    }

    public static String getCodeAdocContentString(Class<? extends BaseEnum> target) {
        StringBuilder sb = new StringBuilder();

        for (BaseEnum baseEnum : target.getEnumConstants()) {
            sb.append("|").append(baseEnum.getType()).append("\n");
            sb.append("|").append(baseEnum.getName()).append("\n");
        }
        return sb.toString();
    }

}

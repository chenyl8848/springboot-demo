package com.codechen.desensitized.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author：Java陈序员
 * @date 2025/10/26 15:26
 * @description 脱敏工具类
 */
public class DesensitizedUtil {

    public static String densenValue(String value, int prefix, int suffix, String maskChar) {
        if (StringUtils.isNoneBlank(value)) {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < value.length(); i++) {
                if (i < prefix || i >= value.length() - suffix) {
                    result.append(value.charAt(i));
                } else {
                    result.append(maskChar);
                }
            }

            return result.toString();

        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        String densenValue = densenValue("1234567890", 3, 4, "*");
        String chineseName = chineseName("迪丽热巴");
        String mobilePhone = mobilePhone("12345678901");
        String idCard = idCard("110123456789012345");
        String bankCard = bankCard("6222020200020200200");
        System.out.printf("densenValue = %s", densenValue);
        System.out.println();
        System.out.printf("chineseName = %s", chineseName);
        System.out.println();
        System.out.printf("mobilePhone = %s", mobilePhone);
        System.out.println();
        System.out.printf("idCard = %s", idCard);
        System.out.println();
        System.out.printf("bankCard = %s", bankCard);
    }

    public static String chineseName(String chineseName) {
        return densenValue(chineseName, 1, 0, "*");
    }

    public static String mobilePhone(String mobilePhone) {
       return densenValue(mobilePhone, 3, 4, "*");
    }

    public static String idCard(String idCard) {
        return densenValue(idCard, 4, 4, "*");
    }

    public static String bankCard(String bankCard) {
        return densenValue(bankCard, 6, 4, "*");
    }
}

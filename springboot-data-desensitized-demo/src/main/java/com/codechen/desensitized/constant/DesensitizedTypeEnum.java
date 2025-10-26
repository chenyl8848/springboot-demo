package com.codechen.desensitized.constant;

/**
 * @author：Java陈序员
 * @date 2025/10/26 15:05
 * @description 敏感信息类型枚举类
 */
public enum DesensitizedTypeEnum {

    /** 自定义 */
    CUSTOMER,
    /** 姓名 李* */
    CHINESE_NAME,
    /** 手机号 138****9999 */
    MOBILE,
    /** 身份证 110************1234 */
    ID_CARD,
    /** 银行卡 622202************1234 */
    BANK_CARD,
}

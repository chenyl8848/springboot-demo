package com.codechen.desensitized.entity;

import com.codechen.desensitized.annotation.Desensitized;
import com.codechen.desensitized.constant.DesensitizedTypeEnum;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:02
 * @description 用户信息
 */
@Data
public class User extends BaseEntity {

    /** 用户名 */
    private String username;
    /** 密码 */
    @Desensitized(type = DesensitizedTypeEnum.CUSTOMER, prefix = 1, suffix = 2)
    private String password;
    /** 昵称 */
    @Desensitized(type = DesensitizedTypeEnum.CHINESE_NAME)
    private String nickName;
    /** 手机号 */
    @Desensitized(type = DesensitizedTypeEnum.MOBILE)
    private String phone;
    /** 身份证号码 */
    @Desensitized(type = DesensitizedTypeEnum.ID_CARD)
    private String idCard;
    /** 银行卡号 */
    @Desensitized(type = DesensitizedTypeEnum.BANK_CARD)
    private String bankCard;
}

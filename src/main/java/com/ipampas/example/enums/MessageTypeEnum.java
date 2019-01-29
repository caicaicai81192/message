package com.ipampas.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息类型枚举
 * @date 2019/1/24 3:25 PM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MessageTypeEnum {

    NULL("", ""),
    SHORT_MESSAGE("shortMessage", "短信"),
    MAIL("mail", "邮件"),
    WECHAT_NOTICE("weChatNotice", "微信通知"),;

    private String code;
    private String name;

    public static MessageTypeEnum getMessageTypeEnumByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return NULL;
        }
        for (MessageTypeEnum item : MessageTypeEnum.values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        return NULL;
    }
}

package com.ipampas.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息状态枚举
 * @date 2019/1/24 3:25 PM
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MessageStatusEnum {

    UNSENT("unsent", "未发送"),
    SENDING("sending", "发送中"),
    SENT("sent", "已发送"),
    FAIL("fail", "发送失败"),;

    private String code;
    private String name;
}

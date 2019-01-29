package com.ipampas.example.model.entity;


import lombok.*;

import java.util.Date;

/**
 * @author caizj
 * @version V1.0
 * @Description: 需要发送的消息
 * @date 2019/1/24 2:56 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Message {

    private Long id;

    /**
     * 消息类型
     * 如果变量值仅在一个固定范围内变化用 enum 类型来定义,相对变动不大的时候
     *
     * @see com.ipampas.example.enums.MessageTypeEnum
     */
    private String type;

    /**
     * 发送目标
     */
    private String targets;

    /**
     * 消息模板
     *
     * @see com.ipampas.example.constant.MessageTemplateConst
     */
    private String template;

    /**
     * 主题
     */
    private String subject;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 状态
     *
     * @see com.ipampas.example.enums.MessageStatusEnum
     */
    private String status;

    /**
     * 发送人
     */
    private String operateName;

    /**
     * 发送人id
     */
    private String operateId;

    /**
     * 失败原因
     */
    private String sendFailReason;

    /**
     * 扩展字段
     */
    private String extension;

    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

}

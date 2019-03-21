package com.ipampas.example.dao.entity;

import lombok.*;

import java.util.Date;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息模版
 * @date 2019/1/24 7:42 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MessageTemplate {

    private Long id;

    /**
     * code
     */
    private String code;

    /**
     * 主题
     */
    private String subject;

    /**
     * 类型
     *
     * @see com.ipampas.example.enums.MessageTemplateTypeEnum
     */
    private String type;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否需要渲染
     */
    private Boolean needRender;

    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String extension;
}

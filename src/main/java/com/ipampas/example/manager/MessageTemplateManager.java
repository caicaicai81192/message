package com.ipampas.example.manager;


import com.ipampas.example.model.entity.MessageTemplate;

import java.util.Map;

/**
 * @author caizj
 * @version V1.0
 * @description: 消息模版服务
 * @date 2019/1/25 9:59 AM
 */
public interface MessageTemplateManager {


    /**
     * 根据code查询模版
     *
     * @param code
     * @return
     */
    MessageTemplate findMessageTemplateByCode(String code);

    /**
     * 渲染模版
     *
     * @param messageTemplateContent
     * @param param
     * @return
     */
    String render(String messageTemplateContent, Map<String, Object> param);

}

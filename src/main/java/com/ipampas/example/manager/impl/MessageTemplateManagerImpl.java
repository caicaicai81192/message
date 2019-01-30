package com.ipampas.example.manager.impl;


import com.ipampas.example.dao.mapper.MessageTemplateMapper;
import com.ipampas.example.manager.MessageTemplateManager;
import com.ipampas.example.model.entity.MessageTemplate;
import com.ipampas.framework.mybatis.builder.OrderBy;
import com.ipampas.framework.support.template.string.SpelTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageTemplateManagerImpl implements MessageTemplateManager {
    @Resource
    private MessageTemplateMapper messageTemplateMapper;

    @Override
    public MessageTemplate findMessageTemplateByCode(String code) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setCode(code);
        messageTemplate.setIsDeleted(false);
        List<MessageTemplate> messageTemplates = messageTemplateMapper.selectListWithOrder(messageTemplate, OrderBy.builder().desc("id"));
        return CollectionUtils.isEmpty(messageTemplates) ? null : messageTemplates.get(0);
    }

    @Override
    public String render(String messageTemplateContent, Map<String, Object> param) {
        //
        Assert.hasText(messageTemplateContent, "messageTemplateContent must not be null or empty");
        //
        ByteArrayResource byteArrayResource = new ByteArrayResource(messageTemplateContent.getBytes());
        SpelTemplate spelTemplate = new SpelTemplate(byteArrayResource);
        return spelTemplate.process(param);
    }

}

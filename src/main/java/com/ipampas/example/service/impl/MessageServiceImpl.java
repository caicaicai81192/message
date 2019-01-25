package com.ipampas.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.ipampas.example.manager.MessageManager;
import com.ipampas.example.manager.MessageTemplateManager;
import com.ipampas.example.model.dto.MessageDto;
import com.ipampas.example.model.entity.Message;
import com.ipampas.example.model.entity.MessageTemplate;
import com.ipampas.example.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/24 4:00 PM
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageManager messageManager;
    @Resource
    private MessageTemplateManager messageTemplateManager;

    @Override
    public void send(MessageDto messageDto) {
        //
        if (Objects.isNull(messageDto)) {
            log.info("messageDto is null");
            return;
        }
        if (StringUtils.isBlank(messageDto.getType())) {
            log.info("type is null");
            return;
        }
        if (CollectionUtils.isEmpty(messageDto.getTargetList())) {
            log.info("targetList is null");
            return;
        }
        if (StringUtils.isBlank(messageDto.getTemplate())) {
            log.info("template is null");
            return;
        }
        if (Objects.isNull(messageDto.getSendTime())) {
            messageDto.setSendTime(new Date());
        }
        //
        MessageTemplate messageTemplate = messageTemplateManager.findMessageTemplateByCode(messageDto.getTemplate());
        Assert.notNull(messageTemplate, "messageTemplate is null");
        //
        String content = messageTemplateManager.render(messageTemplate.getContent(), messageDto.getParam());
        //
        Message message = new Message();
        BeanUtils.copyProperties(messageDto, message);
        message.setTargets(JSON.toJSONString(messageDto.getTargetList()));
        message.setContent(content);
        messageManager.create(message);

    }
}

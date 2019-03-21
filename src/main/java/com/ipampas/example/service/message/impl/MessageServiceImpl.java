package com.ipampas.example.service.message.impl;

import com.alibaba.fastjson.JSON;
import com.ipampas.example.enums.MessageStatusEnum;
import com.ipampas.example.enums.MessageTypeEnum;
import com.ipampas.example.manager.MessageManager;
import com.ipampas.example.manager.MessageTemplateManager;
import com.ipampas.example.service.message.dto.MessageDto;
import com.ipampas.example.dao.entity.Message;
import com.ipampas.example.dao.entity.MessageTemplate;
import com.ipampas.example.service.message.qo.MessageListQo;
import com.ipampas.example.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public Integer send(MessageDto messageDto) {
        //
        Assert.notNull(messageDto, "messageDto must not be null");
        Assert.notNull(messageDto.getMessageTypeEnum(), "type must not be null");
        Assert.notEmpty(messageDto.getTargetList(), "targetList must not be empty");
        Assert.hasText(messageDto.getTemplate(), "template must not be null or empty");
        //
        String content = this.createMessageContent(messageDto);
        //
        Message message = new Message();
        BeanUtils.copyProperties(messageDto, message);
        message.setTargets(JSON.toJSONString(messageDto.getTargetList()));
        message.setContent(content);
        message.setStatus(MessageStatusEnum.UNSENT.getCode());
        message.setType(messageDto.getMessageTypeEnum().getCode());
        message.setSendTime(Optional.ofNullable(messageDto.getSendTime()).orElse(new Date()));
        //
        return messageManager.create(message);
    }

    @Override
    public void sendMessageTask() {
        //
        MessageListQo qo = new MessageListQo();
        qo.setStatus(MessageStatusEnum.UNSENT.getCode());
        List<Message> messages = messageManager.list(qo);
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        messages.forEach(message -> {
            //
            Message modifyMessage = new Message();
            modifyMessage.setId(message.getId());
            modifyMessage.setStatus(MessageStatusEnum.SENDING.getCode());
            messageManager.modify(modifyMessage);
            //
            switch (MessageTypeEnum.getMessageTypeEnumByCode(message.getType())) {
                case SHORT_MESSAGE:
                    sendShortMessage(message);
                    break;
                case MAIL:
                    sendMail(message);
                    break;
                case WECHAT_NOTICE:
                    sendWeChatNotice(message);
                    break;
                default:
                    log.info("不支持的消息类型：【{}】", message.getType());
                    break;
            }
            //
            modifyMessage.setStatus(message.getStatus());
            modifyMessage.setSendFailReason(message.getSendFailReason());
            messageManager.modify(modifyMessage);
        });

    }

    /**
     * 发送微信通知
     *
     * @param message
     */
    private void sendWeChatNotice(Message message) {
    }

    /**
     * 发送短信
     *
     * @param message
     */
    private void sendShortMessage(Message message) {
    }

    /**
     * 发送邮件
     *
     * @param message
     */
    private void sendMail(Message message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            javaMailSender.send(mimeMessage);
            message.setStatus(MessageStatusEnum.SENT.getCode());
        } catch (MailException e) {
            log.warn(e.getMessage(), e);
            message.setSendFailReason(e.getMessage());
            message.setStatus(MessageStatusEnum.FAIL.getCode());
        }
    }

    /**
     * 渲染内容
     *
     * @param messageDto
     * @return
     */
    private String createMessageContent(MessageDto messageDto) {
        //
        MessageTemplate messageTemplate = messageTemplateManager.findMessageTemplateByCode(messageDto.getTemplate());
        Assert.notNull(messageTemplate, "messageTemplate must not be null");
        //
        String content;
        if (messageTemplate.getNeedRender()) {
            content = messageTemplateManager.render(messageTemplate.getContent(), messageDto.getParam());
        } else {
            content = JSON.toJSONString(messageDto.getParam());
        }
        return content;
    }
}

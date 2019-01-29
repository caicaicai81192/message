package com.ipampas.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.ipampas.example.enums.MessageStatusEnum;
import com.ipampas.example.enums.MessageTypeEnum;
import com.ipampas.example.manager.MessageManager;
import com.ipampas.example.manager.MessageTemplateManager;
import com.ipampas.example.model.dto.MessageDto;
import com.ipampas.example.model.entity.Message;
import com.ipampas.example.model.entity.MessageTemplate;
import com.ipampas.example.model.qo.MessageListQo;
import com.ipampas.example.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void send(MessageDto messageDto) {
        //
        if (Objects.isNull(messageDto)) {
            log.error("messageDto is null");
            return;
        }
        if (StringUtils.isBlank(messageDto.getType())) {
            log.error("type is null");
            return;
        }
        if (CollectionUtils.isEmpty(messageDto.getTargetList())) {
            log.error("targetList is null");
            return;
        }
        if (StringUtils.isBlank(messageDto.getTemplate())) {
            log.error("template is null");
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
        message.setStatus(MessageStatusEnum.UNSENT.getCode());
        messageManager.create(message);

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
            Message modify = new Message();
            modify.setId(message.getId());
            modify.setStatus(MessageStatusEnum.SENDING.getCode());
            messageManager.modify(modify);
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
            messageManager.modify(modify);
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
}

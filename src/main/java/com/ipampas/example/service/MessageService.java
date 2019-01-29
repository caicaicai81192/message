package com.ipampas.example.service;

import com.ipampas.example.model.dto.MessageDto;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息相关接口
 * @date 2019/1/24 4:00 PM
 */
public interface MessageService {

    /**
     * 发送信息（微信 短信 邮件）
     *
     * @param messageDto
     */
    void send(MessageDto messageDto);

    /**
     * 定时任务事实发送信息
     */
    void sendMessageTask();
}

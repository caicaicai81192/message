package com.ipampas.example.service.message;

import com.ipampas.example.service.message.dto.MessageDto;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息相关接口
 * @date 2019/1/24 4:00 PM
 */
public interface MessageService {

    /**
     * 发送信息
     *
     * @param messageDto
     * @return
     */
    Integer send(MessageDto messageDto);

    /**
     * 定时任务事实发送信息
     */
    void sendMessageTask();
}

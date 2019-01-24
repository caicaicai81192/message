package com.ipampas.example.service.impl;

import com.ipampas.example.manager.MessageManager;
import com.ipampas.example.model.dto.MessageDto;
import com.ipampas.example.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/24 4:00 PM
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageManager messageManager;

    @Override
    public void send(MessageDto messageDto) {

    }
}

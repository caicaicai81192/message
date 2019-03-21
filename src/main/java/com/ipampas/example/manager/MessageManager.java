package com.ipampas.example.manager;

import com.ipampas.example.dao.entity.Message;
import com.ipampas.example.model.qo.MessageListQo;
import com.ipampas.example.model.qo.MessagePageListQo;
import com.ipampas.framework.model.Page;

import java.util.List;

/**
 * @author caizj
 * @version V1.0
 * @Description: 消息相关接口
 * @date 2019/1/24 3:44 PM
 */
public interface MessageManager {

    int create(Message message);

    int remove(Long id);

    int modify(Message message);

    Message find(Long id);

    List<Message> list(MessageListQo messageListQo);

    Page<Message> pageList(MessagePageListQo messagePageListQo);
}

package com.ipampas.example.manager.impl;

import com.github.pagehelper.PageHelper;
import com.ipampas.example.dao.mapper.MessageMapper;
import com.ipampas.example.manager.MessageManager;
import com.ipampas.example.dao.entity.Message;
import com.ipampas.example.service.message.qo.MessageListQo;
import com.ipampas.example.service.message.qo.MessagePageListQo;
import com.ipampas.example.util.PageUtils;
import com.ipampas.framework.model.Page;
import com.ipampas.framework.mybatis.builder.OrderBy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/24 3:45 PM
 */
@Service
public class MessageManagerImpl implements MessageManager {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public int create(Message message) {
        return messageMapper.insertSelective(message);
    }

    @Override
    public int remove(Long id) {
        Message message = new Message();
        message.setId(id);
        message.setIsDeleted(true);
        return messageMapper.updateSelective(message);
    }

    @Override
    public int modify(Message message) {
        return messageMapper.updateSelective(message);
    }

    @Override
    public Message find(Long id) {
        return messageMapper.selectById(id);
    }

    @Override
    public List<Message> list(MessageListQo messageListQo) {
        messageListQo.setIsDeleted(false);
        return messageMapper.selectListWithOrder(messageListQo, OrderBy.builder().desc("id"));
    }

    @Override
    public Page<Message> pageList(MessagePageListQo messagePageListQo) {
        PageHelper.startPage(messagePageListQo.getPageNo(), messagePageListQo.getPageSize());
        messagePageListQo.setIsDeleted(false);
        List<Message> pageList = messageMapper.selectListWithOrder(messagePageListQo, OrderBy.builder().desc("id"));
        return PageUtils.transToPage(pageList);
    }
}

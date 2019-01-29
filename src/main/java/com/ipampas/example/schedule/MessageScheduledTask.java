package com.ipampas.example.schedule;

import com.ipampas.example.service.MessageService;
import com.ipampas.framework.redisson.annotation.DistributedLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author caizj
 * @version V1.0
 * @Description: 发送信息定时任务
 * @date 2019/1/29 1:44 PM
 */
@Component
public class MessageScheduledTask {

    @Resource
    MessageService messageService;

    @Scheduled(cron = "${message.scheduled-time: 0/30 * * * * ? }")
    @DistributedLock(leaseTime = 60)
    public void scheduledMessageTask() {
        messageService.sendMessageTask();
    }
}

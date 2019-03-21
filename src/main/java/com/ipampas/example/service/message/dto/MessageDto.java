package com.ipampas.example.service.message.dto;

import com.ipampas.example.enums.MessageTypeEnum;
import com.ipampas.example.dao.entity.Message;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/24 5:11 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MessageDto extends Message {

    private Map<String, Object> param;

    private List<String> targetList;

    private MessageTypeEnum messageTypeEnum;
}

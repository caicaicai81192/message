package com.ipampas.example.model.qo;

import lombok.*;

/**
 * @author caizj
 * @version V1.0
 * @Description: 列表查询对象
 * @date 2019/1/24 5:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MessageListQo {

    private Boolean isDeleted;

    /**
     * 状态
     *
     * @see com.ipampas.example.enums.MessageStatusEnum
     */
    private String status;
}

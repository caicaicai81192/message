package com.ipampas.example.service.message.qo;

import com.ipampas.framework.model.Page;
import lombok.*;

/**
 * @author caizj
 * @version V1.0
 * @Description: 分页查询对象
 * @date 2019/1/24 5:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MessagePageListQo extends Page {

    private Boolean isDeleted;
}

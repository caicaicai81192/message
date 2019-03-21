package com.ipampas.example.dao.mapper;

import com.ipampas.example.dao.entity.Message;
import com.ipampas.framework.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/1/24 3:24 PM
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}

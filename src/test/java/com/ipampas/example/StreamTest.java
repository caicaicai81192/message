package com.ipampas.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * @author caizj
 * @version V1.0
 * @date 2019/4/18 2:53 PM
 */
@Slf4j
public class StreamTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        private String name;
        private Integer age;
        private String address;
    }

    @Test
    public void empty() {
    }


}

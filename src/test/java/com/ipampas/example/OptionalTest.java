package com.ipampas.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/4/16 10:50 AM
 */
@Slf4j
public class OptionalTest {

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
        //java.util.NoSuchElementException: No value present
        Optional<User> empty = Optional.empty();
        empty.get();
    }

    @Test
    public void of() {
        //java.lang.NullPointerException
        User user = null;
        Optional<User> opt = Optional.of(user);
    }

    @Test
    public void ofNullable() {
        User user = new User();
        user.setAddress("hangzhou");
        Optional<User> opt = Optional.ofNullable(user);
        if (opt.isPresent()) {
            log.debug(opt.get().address);
        } else {
            log.debug(opt.toString());
        }
    }

    @Test
    public void orElse() {
        //orElseGet 当Optional 不为null时 不会去new orElse 中的对象
        User user = null;
        log.debug("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        log.debug("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(this::createNewUser);

        log.info("------------------");

        User user2 = new User("bob", 20, "杭州");
        log.info("Using orElse");
        User result3 = Optional.ofNullable(user2).orElse(createNewUser());
        log.info("Using orElseGet");
        User result4 = Optional.ofNullable(user2).orElseGet(this::createNewUser);

        Optional.ofNullable(user).orElseThrow(() -> new IllegalArgumentException());

    }

    private User createNewUser() {
        log.debug("Creating New User");
        return new User("bob", 20, "杭州");
    }

    @Test
    public void map() {
        User user = new User("bob", 20, "杭州");
        String address = Optional.ofNullable(user).map(User::getAddress).orElse("");
        log.debug(address);

    }

}

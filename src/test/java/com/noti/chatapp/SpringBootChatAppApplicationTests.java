package com.noti.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringBootChatAppApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;

/*@AfterEach
    public void tearDown() throws Exception {
        //pointRedisRepository.deleteAll();
    }*/

    @Test
    void contextLoads() {
        final String key = "testString";
        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1");
        final String result_1 = stringStringValueOperations.get(key);

        System.out.println("result_1 = " + result_1);

        stringStringValueOperations.increment(key);
        final String result_2 = stringStringValueOperations.get(key);

        System.out.println("result_2 = " + result_2);
    }

}

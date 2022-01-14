package com.wx.response;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : HuangFu
 * @Description : WxAutoResponse
 * @date : 2022-01-10 11:23
 **/
@SpringBootApplication
@MapperScan("com.wx.response.mapper")
public class WxAutoResponseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxAutoResponseApplication.class, args);
    }
}

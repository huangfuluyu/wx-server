package com.wx.response.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : HuangFu
 * @Description : responseController
 * @date : 2022-01-10 15:17
 **/
@RestController
public class ResponseController {

    @GetMapping("/wx")
    public String test(HttpServletRequest request){

        return "Hello,World!!";
    }

}

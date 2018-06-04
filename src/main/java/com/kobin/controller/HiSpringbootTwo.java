package com.kobin.controller;

import com.kobin.entity.ResMsg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author shibinbin on 2018/6/4.
 */
@RestController
public class HiSpringbootTwo {

    @GetMapping("/app/hi")
    public ResMsg hi(){
        return ResMsg.succ();
    }
}

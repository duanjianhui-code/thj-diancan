package com.qcl.controller;

import com.qcl.bean.Food;
import com.qcl.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * desc:验证能不能运行
 */
@RestController
public class HelloController {
    @Autowired
    FoodRepository foodRepository;


    @GetMapping("/")
    public String hello() {
        return "项目可以正常的跑起来了";
    }

    @PostMapping("/")
    public List helloPost(){

        List<Food> all = foodRepository.findAll();
        return all;
    }

}

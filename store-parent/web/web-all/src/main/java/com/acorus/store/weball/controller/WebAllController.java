package com.acorus.store.weball.controller;

import com.acorus.store.weball.service.WebAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-22 11:09
 */
@Controller
public class WebAllController {

    @Autowired
    private WebAllService webAllService;

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 商品详情页
     */
    @RequestMapping("{skuId}.html")
    public String getItemWeb(@PathVariable("skuId")Long skuId, Model model){
        Map<String, Object> reMap =  webAllService.getItemWeb(skuId);
        model.addAllAttributes(reMap);
        return "item/index";
    }

}

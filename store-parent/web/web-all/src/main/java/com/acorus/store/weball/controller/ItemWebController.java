package com.acorus.store.weball.controller;

import com.acorus.store.weball.service.ItemWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 16:17
 */
@Controller
public class ItemWebController {

    @Autowired
    private ItemWebService itemWebService;

    @RequestMapping("{skuId}.html")
    public String getItemWeb(@PathVariable("skuId")Long skuId, Model model){
        Map itemWeb = itemWebService.getItemWeb(skuId);
        model.addAllAttributes(itemWeb);
        return "item/index";
    }

//    @Autowired
//    private ItemFeignClient itemFeignClient;
//
//    //请求转发商品详情页面
//    //  https://item.jd.com  / 100012407280 .html
//    @GetMapping("/{skuId}.html")
//    public String itemIndex(@PathVariable(name = "skuId") Long skuId, Model model){
//        //回显数据 放在Request域中
//        Map<String, Object> map = itemFeignClient.getItem(skuId);
//        model.addAllAttributes(map);// K 使用Map的Key V使用Map的Value
//        return "item/index";
//    }

}

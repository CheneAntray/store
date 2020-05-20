package com.acorus.store.item.controller;

import com.acorus.store.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 10:53
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{skuId}")
    public Map getItem(@PathVariable("skuId")Long skuId){
         return itemService.getItem(skuId);
    }
}

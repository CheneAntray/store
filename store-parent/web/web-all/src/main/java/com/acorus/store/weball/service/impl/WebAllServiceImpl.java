package com.acorus.store.weball.service.impl;

import com.acorus.store.item.client.ItemFeignClient;
import com.acorus.store.weball.service.WebAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-22 15:25
 */
@Service
public class WebAllServiceImpl implements WebAllService {

    @Autowired
    private ItemFeignClient itemFeignClient;
    @Override
    public Map<String, Object> getItemWeb(Long skuId) {
        return itemFeignClient.getItem(skuId);
    }
}

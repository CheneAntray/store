package com.acorus.store.item.client.impl;

import com.acorus.store.item.client.ItemFeignClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 16:01
 */
@Component
public class ItemDegradeFeignClient implements ItemFeignClient {
    @Override
    public Map getItem(Long skuId) {
        return null;
    }
}

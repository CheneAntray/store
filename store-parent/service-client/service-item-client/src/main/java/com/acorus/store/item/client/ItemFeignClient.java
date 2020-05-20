package com.acorus.store.item.client;

import com.acorus.store.item.client.impl.ItemDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 16:00
 */
@FeignClient(value = "service-item",fallback = ItemDegradeFeignClient.class)
public interface ItemFeignClient {

    @GetMapping("/api/item/{skuId}")
    public Map getItem(@PathVariable("skuId")Long skuId);
}

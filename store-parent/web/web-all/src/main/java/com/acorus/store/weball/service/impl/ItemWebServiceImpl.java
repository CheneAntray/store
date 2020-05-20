package com.acorus.store.weball.service.impl;

import com.acorus.store.item.client.ItemFeignClient;
import com.acorus.store.weball.service.ItemWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 16:20
 */
@Service
public class ItemWebServiceImpl implements ItemWebService {

    @Autowired
    private ItemFeignClient itemFeignClient;

    @Override
    public Map getItemWeb(Long skuId) {
        return itemFeignClient.getItem(skuId);
    }
}

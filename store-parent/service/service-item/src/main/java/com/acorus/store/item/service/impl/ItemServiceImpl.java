package com.acorus.store.item.service.impl;

import com.acorus.store.item.service.ItemService;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 10:59
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public Map getItem(Long skuId) {
        Map reMap = new HashMap();
        SkuInfo skuInfo = productFeignClient.getSkuInfoById(skuId);
        reMap.put("skuInfo",skuInfo);
        reMap.put("categoryView",productFeignClient.getCategoryView(skuInfo.getCategory3Id()));
        reMap.put("price",productFeignClient.getSkuPrice(skuId));
        reMap.put("spuSaleAttrList",productFeignClient.getSpuSaleAttrListCheckBySku(skuId,skuInfo.getSpuId()));
        reMap.put("valuesSkuJson",productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId()));
        return reMap;
    }
}

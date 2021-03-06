package com.acorus.store.product.service;

import com.acorus.store.model.product.BaseCategoryView;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuSaleAttr;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-19 11:07
 */
public interface ApiService {
    SkuInfo getSkuInfoById(Long skuId) throws InterruptedException;

    BaseCategoryView getCategoryView(Long category3Id) throws InterruptedException;

    BigDecimal getSkuPrice(Long skuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) throws InterruptedException;

    Map<String, Object> getSkuValueIdsMap(Long spuId) throws InterruptedException;
}

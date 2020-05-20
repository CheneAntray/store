package com.acorus.store.product.service.impl;

import com.acorus.store.model.product.BaseCategoryView;
import com.acorus.store.model.product.SkuImage;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuSaleAttr;
import com.acorus.store.product.mapper.*;
import com.acorus.store.product.service.ApiService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-19 11:11
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public SkuInfo getSkuInfoById(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        skuInfo.setSkuImageList(skuImageMapper.selectList(new QueryWrapper<SkuImage>().eq("sku_id",skuId)));
        return skuInfo;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        return baseCategoryViewMapper.selectById(category3Id);
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return skuInfoMapper.selectById(skuId).getPrice();
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) {
        return spuSaleAttrMapper.getSpuSaleAttrListCheckBySku(skuId,spuId);
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) {
        List<Map> skuGroups = skuSaleAttrValueMapper.getSkuValueIdsMap(spuId);
        Map reMap = new HashMap<>();
        skuGroups.forEach(skuGrop->{
            reMap.put(skuGrop.get("value_ids"),skuGrop.get("sku_id"));
        });
        return reMap;
    }
}

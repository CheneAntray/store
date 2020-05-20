package com.acorus.store.product.service;

import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuImage;
import com.acorus.store.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 23:51
 */
public interface SkuService {
    List<SpuImage> getSpuImageList(Long id);


    List<SpuSaleAttr> spuSaleAttrList(Long spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    void getSkuInfoList(IPage<SkuInfo> skuInfoIPage);

    void skuOnsale(Long skuId);

    void skuCancelSale(Long skuId);
}

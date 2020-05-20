package com.acorus.store.product.service;

import com.acorus.store.model.product.BaseSaleAttr;
import com.acorus.store.model.product.SpuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 21:49
 */
public interface SpuService {
    IPage<SpuInfo> getSpuList(Integer page, Integer limit, long id);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);
}

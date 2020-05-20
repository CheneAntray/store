package com.acorus.store.product.service.impl;

import com.acorus.store.model.product.*;
import com.acorus.store.product.mapper.*;
import com.acorus.store.product.service.SkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 23:51
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SpuImageMapper  spuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Override
    public List<SpuImage> getSpuImageList(Long id) {
        return spuImageMapper.selectList(new QueryWrapper<SpuImage>().eq("spu_id",id));
    }

    @Override
    public List<SpuSaleAttr> spuSaleAttrList(Long spuId) {
        return spuSaleAttrMapper.spuSaleAttrList(spuId);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //添加sku信息
        skuInfo.setIsSale(0);
        skuInfoMapper.insert(skuInfo);
        //添加图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        skuImageList.forEach(skuImage->{
            skuImage.setSkuId(skuInfo.getId());
            skuImageMapper.insert(skuImage);
        });
        //添加平台属性信息
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        skuAttrValueList.forEach(skuAttrValue->{
            skuAttrValue.setSkuId(skuInfo.getId());
            skuAttrValueMapper.insert(skuAttrValue);
        });
        //添加销售属性
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        skuSaleAttrValueList.forEach(skuSaleAttrValue->{
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        });
    }

    @Override
    public void getSkuInfoList(IPage<SkuInfo> skuInfoIPage) {
        skuInfoMapper.selectPage(skuInfoIPage,null);
    }

    @Override
    public void skuOnsale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void skuCancelSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        skuInfoMapper.updateById(skuInfo);
    }

}

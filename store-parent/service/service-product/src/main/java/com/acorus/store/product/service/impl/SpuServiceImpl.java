package com.acorus.store.product.service.impl;

import com.acorus.store.model.product.*;
import com.acorus.store.product.mapper.*;
import com.acorus.store.product.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 21:49
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Override
    public IPage<SpuInfo> getSpuList(Integer page, Integer limit, long id) {
    return spuMapper.selectPage(new Page<SpuInfo>(page,limit),new QueryWrapper<SpuInfo>().eq("category3_id",id));
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectList(null);
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        spuMapper.insert(spuInfo);
        List<SpuImage> imageList = spuInfo.getSpuImageList();
        imageList.forEach(image->{
            image.setSpuId(spuInfo.getId());
            spuImageMapper.insert(image);
        });
        List<SpuSaleAttr> saleAttrList = spuInfo.getSpuSaleAttrList();
        saleAttrList.forEach(saleAttr->{
            List<SpuSaleAttrValue> saleAttrValueList = saleAttr.getSpuSaleAttrValueList();
            saleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(saleAttr);
            saleAttrValueList.forEach(saleAttrValue->{
                saleAttrValue.setSpuId(spuInfo.getId());
                saleAttrValue.setSaleAttrName(saleAttr.getSaleAttrName());
                spuSaleAttrValueMapper.insert(saleAttrValue);
            });
        });
    }
}

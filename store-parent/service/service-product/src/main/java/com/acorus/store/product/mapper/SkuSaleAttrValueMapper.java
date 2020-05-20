package com.acorus.store.product.mapper;

import com.acorus.store.model.product.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-19 9:17
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    List<Map> getSkuValueIdsMap(Long spuId);
}

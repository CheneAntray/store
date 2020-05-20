package com.acorus.store.product.mapper;

import com.acorus.store.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 15:01
 */
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {
    List<BaseAttrInfo> getBaseAttrInfo(@Param("category1Id") long category1Id,
                                       @Param("category2Id") long category2Id,
                                       @Param("category3Id") long category3Id);
}

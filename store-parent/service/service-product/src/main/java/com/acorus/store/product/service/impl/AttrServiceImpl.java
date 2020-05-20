package com.acorus.store.product.service.impl;

import com.acorus.store.model.product.*;
import com.acorus.store.product.mapper.*;
import com.acorus.store.product.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-12 20:44
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private Catefory1Mapper catefory1Mapper;
    @Autowired
    private Catefory2Mapper catefory2Mapper;
    @Autowired
    private Catefory3Mapper catefory3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseCategory1> getCategory1() {
        return catefory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        return catefory2Mapper.selectList(new QueryWrapper<BaseCategory2>().eq("category1_id",category1Id));
    }

    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        return catefory3Mapper.selectList(new QueryWrapper<BaseCategory3>().eq("category2_id",category2Id));
    }

    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(long category1Id, long category2Id, long category3Id) {
        return baseAttrInfoMapper.getBaseAttrInfo(category1Id,category2Id,category3Id);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        baseAttrInfoMapper.insert(baseAttrInfo);
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        attrValueList.forEach(attrValue->{
            attrValue.setAttrId(baseAttrInfo.getId());
            baseAttrValueMapper.insert(attrValue);
        });
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(long id) {
        return baseAttrValueMapper.selectList(new QueryWrapper<BaseAttrValue>().eq("attr_id",id));
    }

    @Override
    public void updateAttrInfo(BaseAttrInfo baseAttrInfo) {
        baseAttrInfoMapper.updateById(baseAttrInfo);
        this.delAttrValueList(baseAttrInfo.getId());
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        attrValueList.forEach(attrValue->{
            attrValue.setAttrId(baseAttrInfo.getId());
            baseAttrValueMapper.insert(attrValue);
        });
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 删除某个平台属性的属性值
     */
    public void delAttrValueList(long id){
        baseAttrValueMapper.delete(new QueryWrapper<BaseAttrValue>().eq("attr_id",id));
    }


}

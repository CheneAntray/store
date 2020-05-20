package com.acorus.store.product.service;

import com.acorus.store.model.product.*;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-12 20:33
 */
public interface AttrService {
    List<BaseCategory1> getCategory1();

    List<BaseCategory2> getCategory2(Long category1Id);

    List<BaseCategory3> getCategory3(Long category2Id);


    List<BaseAttrInfo> getBaseAttrInfo(long category1Id, long category2Id, long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueList(long id);

    void updateAttrInfo(BaseAttrInfo baseAttrInfo);
}

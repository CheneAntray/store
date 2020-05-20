package com.acorus.store.product.service;

import com.acorus.store.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 20:41
 */
public interface TradeMarkService {
    IPage<BaseTrademark> getTradeMarkList(Integer page, Integer limit);

    void saveTrademark(BaseTrademark baseTrademark);

    List<BaseTrademark> getTradeMarkListNoPage();
}

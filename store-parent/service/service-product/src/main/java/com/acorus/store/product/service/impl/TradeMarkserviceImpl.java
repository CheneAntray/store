package com.acorus.store.product.service.impl;

import com.acorus.store.model.product.BaseTrademark;
import com.acorus.store.product.mapper.TradeMarkMapper;
import com.acorus.store.product.service.TradeMarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 20:41
 */
@Service
public class TradeMarkserviceImpl implements TradeMarkService {

    @Autowired
    private TradeMarkMapper tradeMarkMapper;
    @Override
    public IPage<BaseTrademark> getTradeMarkList(Integer page, Integer limit) {
        IPage<BaseTrademark> rePage = new Page<>(page,limit);
        tradeMarkMapper.selectPage(rePage,null);
        return rePage;
    }

    @Override
    public void saveTrademark(BaseTrademark baseTrademark) {
        tradeMarkMapper.insert(baseTrademark);
    }

    @Override
    public List<BaseTrademark> getTradeMarkListNoPage() {
        return tradeMarkMapper.selectList(null);
    }
}

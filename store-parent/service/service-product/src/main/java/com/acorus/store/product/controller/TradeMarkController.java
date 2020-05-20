package com.acorus.store.product.controller;

import com.acorus.store.common.result.Result;
import com.acorus.store.model.product.BaseTrademark;
import com.acorus.store.product.service.TradeMarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 20:29
 */
@Api(tags = "品牌后台管理")
@RestController
@RequestMapping("/admin/product")
public class TradeMarkController {

    @Autowired
    private TradeMarkService tradeMarkService;

    @GetMapping("baseTrademark/{page}/{limit}")
    public Result<IPage<BaseTrademark>> getTradeMarkList(@PathVariable("page") Integer page,
                                                         @PathVariable("limit")Integer limit){
        IPage<BaseTrademark> rePage =  tradeMarkService.getTradeMarkList(page,limit);
        return Result.ok(rePage);
    }

    @GetMapping("baseTrademark/getTrademarkList")
    public Result<List<BaseTrademark>> getTradeMarkList(){
        List<BaseTrademark> reList =  tradeMarkService.getTradeMarkListNoPage();
        return Result.ok(reList);
    }
    @PostMapping("baseTrademark/save")
    public Result saveTrademark(BaseTrademark baseTrademark){
        tradeMarkService.saveTrademark(baseTrademark);
        return Result.ok();
    }
}

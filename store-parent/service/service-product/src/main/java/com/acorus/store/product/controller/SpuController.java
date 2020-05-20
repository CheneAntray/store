package com.acorus.store.product.controller;

import com.acorus.store.common.result.Result;
import com.acorus.store.model.product.BaseSaleAttr;
import com.acorus.store.model.product.SpuInfo;
import com.acorus.store.product.service.SpuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 21:44
 */
@Api(tags = "标准商品单位后台管理")
@RestController
@RequestMapping("/admin/product")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/{page}/{limit}")
    public Result<IPage<SpuInfo>> getSpuList(@PathVariable("page")Integer page,
                            @PathVariable("limit")Integer limit,
                            long category3Id){
       IPage<SpuInfo> spuInfos =  spuService.getSpuList(page,limit,category3Id);
        return Result.ok(spuInfos);
    }

    @GetMapping("baseSaleAttrList")
    public Result<List<BaseSaleAttr>> baseSaleAttrList(){
        List<BaseSaleAttr> saleAttrs = spuService.baseSaleAttrList();
        return Result.ok(saleAttrs);
    }

    @PostMapping("saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        //保存SPU
        spuService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}

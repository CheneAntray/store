package com.acorus.store.product.controller;

import com.acorus.store.common.result.Result;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuImage;
import com.acorus.store.model.product.SpuSaleAttr;
import com.acorus.store.product.service.SkuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CheneAntray
 * @create 2020-05-14 23:50
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/admin/product")
public class SkuController {

    @Autowired
    private SkuService skuService;
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 查询商品图片列表
     */
    @GetMapping("spuImageList/{spuId}")
    public Result<List<SpuImage>> getSpuImageList(@PathVariable("spuId")Long spuId){
        List<SpuImage>  spuImageList = skuService.getSpuImageList(spuId);
        return Result.ok(spuImageList);
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 查询销售属性
     */
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result<List<SpuSaleAttr>> spuSaleAttrList(@PathVariable(name = "spuId") Long spuId){
       List<SpuSaleAttr> attrList = skuService.spuSaleAttrList(spuId);
        return Result.ok(attrList);
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 添加sku
     */
    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
        skuService.saveSkuInfo(skuInfo);
        return Result.ok();
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 获取sku分页列表
     */
    @GetMapping("list/{page}/{limit}")
    public Result<IPage<SkuInfo>> getSkuInfoList(@PathVariable("page")Integer page,@PathVariable("limit")Integer limit){
        IPage<SkuInfo> skuInfoIPage = new Page<>(page,limit);
        skuService.getSkuInfoList(skuInfoIPage);
        return Result.ok(skuInfoIPage);
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: sku上架
     */
    @GetMapping("onSale/{skuId}")
    public Result skuOnsale(@PathVariable("skuId")Long skuId){
        skuService.skuOnsale(skuId);
        return Result.ok();
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: sku下架
     */
    @GetMapping("cancelSale/{skuId}")
    public Result skuCancelSale(@PathVariable("skuId")Long skuId){
        skuService.skuCancelSale(skuId);
        return Result.ok();
    }
}

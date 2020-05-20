package com.acorus.store.product.controller;



import com.acorus.store.common.result.Result;
import com.acorus.store.model.product.*;
import com.acorus.store.product.service.AttrService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author CheneAntray
 * @create 2020-05-12 20:08
 */
@Api(tags = "平台属性后台管理")
@RestController
@RequestMapping("/admin/product")
//@CrossOrigin
public class AttrController {

    @Autowired
    private AttrService attrService;
    /**
     * @Author: CheneAntray
     * @Return: Result<List<BaseCategory1>>
     * @Description: 获取一级分类列表
     */
    @GetMapping("getCategory1")
    public Result<List<BaseCategory1>> getCategory1List(){
        List<BaseCategory1> category1 = attrService.getCategory1();
        return Result.ok(category1);
    }
    /**
     * @Author: CheneAntray
     * @Return: Result<List<BaseCategory1>>
     * @Description: 获取
     */
    @GetMapping("getCategory2/{category1Id}")
    public Result<List<BaseCategory2>> getCatgory2List(@PathVariable("category1Id")long category1Id){
        List<BaseCategory2> category2 = attrService.getCategory2(category1Id);
        return Result.ok(category2);
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description:
     */
    @GetMapping("getCategory3/{category2Id}")
    public Result<List<BaseCategory3>> getCatgory3List(@PathVariable("category2Id")long category2Id){
        List<BaseCategory3> category3 = attrService.getCategory3(category2Id);
        return Result.ok(category3);
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 平台属性列表
     */
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result<List<BaseAttrInfo>> getBaseAttrInfo(@PathVariable("category1Id")long category1Id,
        @PathVariable("category2Id")long category2Id,
        @PathVariable("category3Id")long category3Id){
        List<BaseAttrInfo> attrInfos = attrService.getBaseAttrInfo(category1Id,category2Id,category3Id);
        return Result.ok(attrInfos);
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 添加平台属性
     */
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        if(baseAttrInfo.getId()==0||baseAttrInfo.getId()==null){
            attrService.saveAttrInfo(baseAttrInfo);
            return Result.ok();
        }
        attrService.updateAttrInfo(baseAttrInfo);
        return Result.ok();
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 修改平台属性准备工作
     */
    @GetMapping("getAttrValueList/{id}")
    public Result<List<BaseAttrValue>>  getAttrValueList(@PathVariable("id")long id){
        List<BaseAttrValue> attrValues =  attrService.getAttrValueList(id);
        return Result.ok(attrValues);
    }
}

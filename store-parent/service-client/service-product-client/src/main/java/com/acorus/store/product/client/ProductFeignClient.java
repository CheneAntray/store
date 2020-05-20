package com.acorus.store.product.client;

import com.acorus.store.model.product.BaseCategoryView;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuSaleAttr;
import com.acorus.store.product.client.impl.ProductDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author CheneAntray
 * @create 2020-05-20 10:31
 */
@FeignClient(value ="service-product", fallback = ProductDegradeFeignClient.class)
//@RequestMapping("/api/product")
public interface ProductFeignClient {

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 根据skuId 查询skuInfo 和sku 图片信息
     */
    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfoById(@PathVariable("skuId")Long skuId);

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 获取分类信息  （在这里创建了视图）
     * SELECT
     * c3.id as id,
     * c1.id as category1_id, c1.name as category1_name,
     * c2.id as category2_id, c2.name as category2_name,
     * c3.id as category3_id, c3.name as category3_name
     * FROM base_category1 c1
     * INNER JOIN
     * base_category2 c2 on c1.id=c2.category1_id
     * INNER JOIN
     * base_category3 c3 on c2.id=c3.category2_id
     * where c3.id= 61
     */
    @GetMapping("/api/product/inner/getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryView(@PathVariable("category3Id") Long category3Id);

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 获取sku价格的信息
     */
    @GetMapping("/api/product/inner/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable("skuId")Long skuId);

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 获取所有销售属性和当前的销售属性
     */
    @GetMapping("/api/product/inner/getSpuSaleAttrListCheckBySku/{skuId}/{spuId}}")
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(@PathVariable("skuId")Long skuId,
                                                          @PathVariable("spuId")Long spuId);

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 点击其他销售属性值的组合，跳转到另外的sku页面
     * 后台要生成一个“属性值1|属性值2|属性值3：skuId”的一个json串以提供页面进行匹配
     */
    @GetMapping("/api/product/inner/getSkuValueIdsMap/{spuId}")
    public Map getSkuValueIdsMap(@PathVariable("spuId")Long spuId);
}

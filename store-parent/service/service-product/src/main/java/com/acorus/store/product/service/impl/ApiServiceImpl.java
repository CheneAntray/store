package com.acorus.store.product.service.impl;

import com.acorus.store.common.constant.RedisConst;
import com.acorus.store.model.product.BaseCategoryView;
import com.acorus.store.model.product.SkuImage;
import com.acorus.store.model.product.SkuInfo;
import com.acorus.store.model.product.SpuSaleAttr;
import com.acorus.store.product.mapper.*;
import com.acorus.store.product.service.ApiService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author CheneAntray
 * @create 2020-05-19 11:11
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;
    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
            "then return tostring(redis.call('del',KEYS[1])) else return 0 end";    //LUA脚本

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 使用原生redis实现分布式锁（待优化redisson）:获取商品详情信息
     */
    public SkuInfo getSkuInfoById3(Long skuId) throws InterruptedException {
        //设置skuinfo在redis中保存的key
        String skuInfoKeyForRedis = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKUKEY_SUFFIX;
        String skuInfoKeyForRedisLock = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKULOCK_SUFFIX;
        String uuidForLock = UUID.randomUUID().toString();
        //去redis中取值
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        if(skuInfo!=null)
            return skuInfo;
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(skuInfoKeyForRedisLock, uuidForLock,RedisConst.SKULOCK_EXPIRE_PX2,TimeUnit.SECONDS);  //加分布式锁解决缓存击穿问题
        if(!isLock){
            Thread.sleep(200);
            return (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        }
        skuInfo = getSkuInfo(skuId, skuInfoKeyForRedis);
//        Object lock = redisTemplate.opsForValue().get(skuInfoKeyForRedisLock);  //使用uuid防误删 存在没有原子性的问题
//        if(lock!=null&&uuidForLock.equals(lock))
//            redisTemplate.delete(skuInfoKeyForRedisLock);
        //使用LUA脚本保证原子性
        redisTemplate.execute(new DefaultRedisScript<>(script), Collections.singletonList("skuInfoKeyForRedisLock"), uuidForLock);
        return skuInfo;
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: redisson试用:解决分布式锁的问题
     */
    public SkuInfo getSkuInfoById2(Long skuId) throws InterruptedException {
        String skuInfoKeyForRedis = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKUKEY_SUFFIX;
        String skuInfoKeyForRedisLock = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKULOCK_SUFFIX;
        //去redis中取值
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        if(skuInfo!=null)
            return skuInfo;
        RLock lock = redissonClient.getLock(skuInfoKeyForRedisLock); //通过redisson获取分布式锁
        lock.lock(RedisConst.SKULOCK_EXPIRE_PX1,TimeUnit.SECONDS);   //上锁操作  可重入锁  阻塞  等所队列
        skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        if(skuInfo!=null)
            return skuInfo;
        skuInfo = getSkuInfo(skuId, skuInfoKeyForRedis);
        lock.unlock();
        return skuInfo;
    }
    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 尝试性获取锁:完美解决方案
     */
    @Override
    public SkuInfo getSkuInfoById(Long skuId) throws InterruptedException {
        String skuInfoKeyForRedis = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKUKEY_SUFFIX;
        String skuInfoKeyForRedisLock = RedisConst.SKUKEY_PREFIX+skuId+RedisConst.SKULOCK_SUFFIX;
        //去redis中取值
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        if(skuInfo!=null)
            return skuInfo;
        RLock lock = redissonClient.getLock(skuInfoKeyForRedisLock); //通过redisson获取分布式锁
        boolean isLock = lock.tryLock(RedisConst.SKULOCK_EXPIRE_PX1, RedisConst.SKULOCK_EXPIRE_PX3, TimeUnit.SECONDS); //1秒钟可能略长但是不影响，最多获取锁的请求多一点而已 不阻塞
        if(!isLock)
            return (SkuInfo) redisTemplate.opsForValue().get(skuInfoKeyForRedis);
        return getSkuInfo(skuId, skuInfoKeyForRedis); //不要手动释放锁，可以造成除了第一个拿到锁的其他都失败，超级加倍哦不超级完美
    }


    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 抽取查询skuinfo并存入redis，提高代码复用性
     */
    private SkuInfo getSkuInfo(Long skuId, String skuInfoKeyForRedis) {
        SkuInfo skuInfo;
        skuInfo = skuInfoMapper.selectById(skuId);
        if(skuInfo==null)
            skuInfo = new SkuInfo();//解决空指针和redis缓存穿透的问题
        skuInfo.setSkuImageList(skuImageMapper.selectList(new QueryWrapper<SkuImage>().eq("sku_id",skuId)));
        redisTemplate.opsForValue().set(skuInfoKeyForRedis,skuInfo, RedisConst.SKUKEY_TIMEOUT+getRandomTime(), TimeUnit.SECONDS);
        return skuInfo;
    }

    @Override
    public BaseCategoryView getCategoryView(Long category3Id) throws InterruptedException {
        if(category3Id==null)
            return null;
        String categoryKeyForRedis = RedisConst.CATEGORYKEY_PREFIX+category3Id+RedisConst.SKUKEY_SUFFIX;
        String categoryKeyForRedisLock = RedisConst.CATEGORYKEY_PREFIX+category3Id+RedisConst.SKULOCK_SUFFIX;
        String uuidForLock = UUID.randomUUID().toString();
        BaseCategoryView baseCategoryView = (BaseCategoryView)redisTemplate.opsForValue().get(categoryKeyForRedis);
        if(baseCategoryView!=null)
            return baseCategoryView;
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(categoryKeyForRedisLock, uuidForLock, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
        if(!isLock){
            Thread.sleep(200);
            return (BaseCategoryView)redisTemplate.opsForValue().get(categoryKeyForRedis);
        }
        baseCategoryView = baseCategoryViewMapper.selectById(category3Id);
        redisTemplate.opsForValue().set(categoryKeyForRedis,baseCategoryView,RedisConst.SKUKEY_TIMEOUT+getRandomTime(), TimeUnit.SECONDS);
        redisTemplate.execute(new DefaultRedisScript<>(script), Collections.singletonList("categoryKeyForRedisLock"), uuidForLock);
        return baseCategoryView;
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if(skuInfo==null)
            skuInfo = new SkuInfo();//解决空指针问题
        return  skuInfo.getPrice();
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Long skuId, Long spuId) throws InterruptedException {
        if(spuId==null)
            return null;
        String saleAttrKeyForRedis = RedisConst.SALEATTRKEY_PREFIX+skuId+spuId+RedisConst.SKUKEY_SUFFIX;
        String saleAttrKeyForRedisLock = RedisConst.SALEATTRKEY_PREFIX+skuId+spuId+RedisConst.SKULOCK_SUFFIX;
        String uuidForLock = UUID.randomUUID().toString();
        List<SpuSaleAttr> attrList = (List<SpuSaleAttr>) redisTemplate.opsForValue().get(saleAttrKeyForRedis);
        if(attrList!=null)
            return attrList;
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(saleAttrKeyForRedisLock, uuidForLock, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
        if(!isLock){
            Thread.sleep(200);
            return (List<SpuSaleAttr>) redisTemplate.opsForValue().get(saleAttrKeyForRedis);
        }
        attrList = spuSaleAttrMapper.getSpuSaleAttrListCheckBySku(skuId,spuId); //集合自动保存空结果天然防redis缓存穿透
        redisTemplate.opsForValue().set(saleAttrKeyForRedis,attrList,RedisConst.SKUKEY_TIMEOUT+getRandomTime(), TimeUnit.SECONDS);
        redisTemplate.execute(new DefaultRedisScript<>(script), Collections.singletonList("saleAttrKeyForRedisLock"), uuidForLock);
        return attrList;
    }

    @Override
    public Map getSkuValueIdsMap(Long spuId) throws InterruptedException {
        if (spuId==null)
            return null;
        String skuValueIdsMapForRedis = RedisConst.SKUVALUEIDSMAP_PREFIX+spuId+RedisConst.SKUKEY_SUFFIX;
        String skuValueIdsMapForRedisLock = RedisConst.SKUVALUEIDSMAP_PREFIX+spuId+RedisConst.SKULOCK_SUFFIX;
        String uuidForLock = UUID.randomUUID().toString();
        Map redisMap = (Map) redisTemplate.opsForValue().get(skuValueIdsMapForRedis);
        if(redisMap!=null)
            return redisMap;
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(skuValueIdsMapForRedisLock, uuidForLock, RedisConst.SKULOCK_EXPIRE_PX2, TimeUnit.SECONDS);
        if(!isLock){
            Thread.sleep(200);
            return (Map) redisTemplate.opsForValue().get(skuValueIdsMapForRedis);
        }
        Map reMap = new HashMap();
        List<Map> skuGroups = skuSaleAttrValueMapper.getSkuValueIdsMap(spuId);
        skuGroups.forEach(skuGrop->{
            reMap.put(skuGrop.get("value_ids"),skuGrop.get("sku_id"));
        });
        redisTemplate.opsForValue().set(skuValueIdsMapForRedis,reMap,RedisConst.SKUKEY_TIMEOUT+getRandomTime(), TimeUnit.SECONDS);
        redisTemplate.execute(new DefaultRedisScript<>(script), Collections.singletonList("skuValueIdsMapForRedisLock"), uuidForLock);
        return reMap;
    }

    /**
     * @Author: CheneAntray
     * @Return: RetVal
     * @Description: 解决缓存雪崩取随机时间
     */
    private Integer getRandomTime(){
        return new Random().nextInt(300);
    }
}

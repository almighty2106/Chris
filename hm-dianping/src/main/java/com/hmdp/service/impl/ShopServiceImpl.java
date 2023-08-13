package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryById(Long id) {
        String key = "cache:shop:" + id;
        //1.查询Redis
        String shopJson = stringRedisTemplate.opsForValue().get(key);

        //2.查询到数据，直接返回
        if (StrUtil.isNotBlank(shopJson)){
            return Result.ok(JSONUtil.toBean(shopJson, Shop.class));
        }
        //3.查询不到，访问数据库
        Shop shop = getById(id);
        //5. 数据库未查询到结果，返回404错误
        if(shop == null){
            return Result.fail("店铺不存在");
        }
        //6.写入Redis, 返回数据
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop));
        return Result.ok(shop);
    }
}

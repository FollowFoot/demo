package com.fpi.mjf.demo.utils.restructure;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;

@Component
public class RedisUtil {

    @Autowired
    @Qualifier("objectRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil redisUtil;
    
    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
    }

    /**
     * 大字符串压缩为MD5码加前缀（不同业务的缓存加不同的前缀）
     * @param key 
     * @param prefix 前缀
     * @return 
     * <xmp>
     * key=聚光科技，prefix=fpi
     * return fpi:739033123722906a86c2e387db18652f
     * </xmp>
     */
    public static String getMD5key(String key, String prefix) {
        if(key == null || "".equals(key.trim())) {
            throw new RuntimeException("key值不可为空");
        }
        if(prefix == null || "".equals(prefix.trim())) {
            throw new RuntimeException("不支持无前缀的key值");
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return (prefix + ":" + new BigInteger(1, md5.digest(key.getBytes("utf-8"))).toString(16));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 大字符串压缩为MD5码加前缀（不同业务的缓存加不同的前缀）
     * @param key 
     * @return 
     * <xmp>
     * key=聚光科技
     * return redis:739033123722906a86c2e387db18652f
     * </xmp>
     */
    public static String getMD5key(String key) {
        return getMD5key(key, "redis");
    }
    
    /**
     * 指定缓存失效时间
     * 
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        if (time > 0) {
            return redisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     * 
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * 
     * @param key 键
     * @return true 存在 false不存在
     */
    public static  boolean hasKey(String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * 
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisUtil.redisTemplate.delete(key[0]);
            } else {
                redisUtil.redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * 
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisUtil.redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * 
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        redisUtil.redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 普通缓存放入并设置时间
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        if (time > 0) {
            redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
        return true;
    }

    /**
     * 递增
     * 
     * @param key 键
     * @param by 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisUtil.redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * 
     * @param key 键
     * @param by 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisUtil.redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * HashGet
     * 
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        return redisUtil.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * 
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisUtil.redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * 
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static void hmset(String key, Map<String, Object> map) {
        redisUtil.redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     * 
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        pullAll(key,map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }
    
    
    public static void pullAll(String key, Map<String, Object> map) {
        redisUtil.redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        redisUtil.redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        redisUtil.redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 删除hash表中的值
     * 
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        redisUtil.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * 
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisUtil.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * 
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return redisUtil.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * 
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return redisUtil.redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 根据key获取Set中的所有值
     * 
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisUtil.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * 
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        return redisUtil.redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        return redisUtil.redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     * 
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        Long count = redisUtil.redisTemplate.opsForSet().add(key, values);
        if (time > 0)
            expire(key, time);
        return count;

    }

    /**
     * 获取set缓存的长度
     * 
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        return redisUtil.redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        return redisUtil.redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取list缓存的内容
     * 
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        return redisUtil.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     * 
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        return redisUtil.redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * 
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        return redisUtil.redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static void lSet(String key, Object value) {
        redisUtil.redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static void lSet(String key, Object value, long time) {
        lSet(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 将list放入缓存
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static void lSet(String key, List<Object> value) {
        redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static void lSet(String key, List<Object> value, long time) {
        redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * 
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        redisUtil.redisTemplate.opsForList().set(key, index, value);
        return true;
    }

    /**
     * 移除N个值为value
     * 
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        Long remove = redisUtil.redisTemplate.opsForList().remove(key, count, value);
        return remove;
    }
}

package com.fpi.mjf.demo.utils;

import java.util.List;
import java.util.regex.Pattern;
import com.fpi.mjf.demo.entity.po.DemoEntity;
import com.fpi.mjf.demo.entity.po.MongoDBPersistent;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MongoDB脚本工具类
 * @author 梅纪飞
 *
 */
public class MongoUtil {
    /** 
     * like '%x%'
     * @param key
     * @param value
     * @return
     */
    public DBObject like(String key, String value) {
        Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
        return new BasicDBObject().append(key, pattern);
    }
    
    /**
     * like 'x%'
     * @param key
     * @param value
     * @return
     */
    public DBObject startWith(String key, String value) {
        Pattern pattern = Pattern.compile("^" + value + ".*$", Pattern.CASE_INSENSITIVE);
        return new BasicDBObject().append(key, pattern);
    }
    
    /**
     * like '%x'
     * @param key
     * @param value
     * @return
     */
    public DBObject endWith(String key, String value) {
        Pattern pattern = Pattern.compile("^.*" + value + "$", Pattern.CASE_INSENSITIVE);
        return new BasicDBObject().append(key, pattern);
    }
    
    /**
     *  = | equals
     * @param key
     * @param value
     * @return
     */
    public DBObject equals(String key, Object value) {
        return new BasicDBObject().append(key, value);
    }
    
    /**
     *  ≠
     * @param key
     * @param value
     * @return
     */
    public DBObject notEquals(String key, Object value) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$not", value));
    }
    
    /**
     * >
     * @param key
     * @param value
     * @return
     */
    public DBObject gt(String key, Object value) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gt", value));
    }
    
    /**
     * ≥
     * @param key
     * @param value
     * @return
     */
    public DBObject gte(String key, Object value) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gte", value));
    }
    
    /**
     * <
     * @param key
     * @param value
     * @return
     */
    public DBObject lt(String key, Object value) {
        return new BasicDBObject().append(key, new BasicDBObject().append("lt", value));
    }
    
    /**
     * ≤
     * @param key
     * @param value
     * @return
     */
    public DBObject lte(String key, Object value) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$lte", value));
    }
    
    /**
     * ≤ x < | [ ... )
     * @param key
     * @param left
     * @param right
     * @return
     */
    public DBObject betweenIgnoreRight(String key, Object left, Object right) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gte", left).append("$lt", right));
    }
    
    /**
     * < and < | ( ... )
     * @param key
     * @param left
     * @param right
     * @return
     */
    public DBObject betweenIgnoreBoth(String key, Object left, Object right) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gt", left).append("$lt", right));
    }
    
    /**
     * < and ≤ | ( ... ]
     * @param key
     * @param left
     * @param right
     * @return
     */
    public DBObject betweenIgnoreLeft(String key, Object left, Object right) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gt", left).append("$lte", right));
    }
    
    /**
     * ≤ and ≤ | [ ... ]
     * @param key
     * @param left
     * @param right
     * @return
     */
    public DBObject between(String key, Object left, Object right) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$gte", left).append("$lte", right));
    }
    
    /**
     * in (...)
     * @param key
     * @param values
     * @return
     */
    public DBObject in(String key, List<Object> values) {
        return new BasicDBObject().append(key, new BasicDBObject().append("$in", values));
    }
    
    /**
     * select x
     * @param key
     * @param isShow
     * @return
     */
    public DBObject show(String key, boolean isShow) {
        return new BasicDBObject().append(key, isShow ? 1 : -1);
    }
    
    public DBObject hideId() {
        return new BasicDBObject().append("_id", -1);
    }
    
    public DBObject insert(MongoDBPersistent p) {
        return null;
    }
    
    public static void main(String[] args) {
        MongoUtil util = new MongoUtil();
        DemoEntity demo = new DemoEntity();
        demo.setName("111");
        DBObject noSql = util.insert(demo);
        System.out.println(noSql);
    }
}

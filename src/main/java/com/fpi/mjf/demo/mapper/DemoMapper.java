package com.fpi.mjf.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.fpi.mjf.demo.entity.po.DemoEntity;

public interface DemoMapper {
    @Select("SELECT * FROM test")
    List<DemoEntity> findAll();
    
    @Insert("INSERT INTO test (name, enName) VALUES (#{name}, #{enName})")
    void insert(DemoEntity entity);
    
    @Update("UPDATE test name = #{name}, enName = #{enName} WHERE id = #{id}")
    void update(DemoEntity entity);
    
    @Delete("DELETE FROM test WHERE id = #{id}")
    void delete(int id);

    @Select("SELECT * FROM test WHERE name LIKE \"%\"#{name}\"%\"")
    List<DemoEntity> findByName(String name);
    
    @Select("SELECT * FROM test WHERE name = #{name}")
    DemoEntity findOneByName(String name);
    
    @Select("<script>" 
            + "SELECT * FROM test " 
            + "<where>" 
            + "<if test = 'name != null'> AND enName LIKE \"%\"#{name}\"%\"</if>"
            + "</where>"
            + "</script>")
    List<DemoEntity> findByEnName(String name);

    @Delete("DELETE FROM test WHERE name = #{name}")
    void deleteByName(String name);
    
    @Delete("<script>" 
            + "DELETE FROM test WHERE id in "
            + "<foreach item = 'id' collection = 'list' open = '(' close = ')' separator = ','>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void deleteBatch(List<Integer> ids);
}

//package com.fpi.mjf.demo.repo;
//
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
//import com.fpi.mjf.demo.entity.po.DemoEntity;
//
//public interface DemoRepository extends JpaRepository<DemoEntity, Integer> {
//    List<DemoEntity> findByName(String name);
//    
//    @Transactional
//    int deleteByName(String name);
//}

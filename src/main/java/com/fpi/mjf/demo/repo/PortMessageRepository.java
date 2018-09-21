//package com.fpi.mjf.demo.repo;
//
//import java.util.List;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import com.fpi.mjf.demo.entity.po.PortMessage;
//
//public interface PortMessageRepository extends JpaRepository<PortMessage, Integer>, JpaSpecificationExecutor<PortMessage> {
//    @Query("FROM PortMessage WHERE port = ? ")
//    List<PortMessage> findPageDatas(String port, Pageable pageable);
//    
//    @Query("FROM PortMessage")
//    List<PortMessage> findPageDatas(Pageable pageable);
//    
//    List<PortMessage> findByPortContaining(String port, Pageable pageable);
//}

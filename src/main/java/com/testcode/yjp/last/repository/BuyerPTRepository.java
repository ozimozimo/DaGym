package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.BuyerPt;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface BuyerPTRepository extends JpaRepository<BuyerPt, Long>, QuerydslPredicateExecutor<BuyerPt>{

    @Query("select b from BuyerPt b where b.member.id=:member_id and b.trainerInfo.id=:trainer_id and b.bt_cancel=0")
    BuyerPt findPayInfo(Long member_id, Long trainer_id);

//    @Transactional
//    @Modifying
//    @Query("delete from BuyerPt b where b.member.id=:member_id and b.trainerInfo.id=:trainer_id")
//    void deleteInfo(Long member_id, Long trainer_id);

    @Transactional
    @Modifying
    @Query("update BuyerPt b set b.bt_cancel=1 where b.member.id=:member_id and b.trainerInfo.id=:trainer_id")
    void updateInfo(Long member_id, Long trainer_id);

    @Query("select b from BuyerPt b where b.member.id=:member_id")
    List<BuyerPt> findBuyers(Long member_id);

    @Query("select b from BuyerPt b where b.trainerInfo.member.id=:member_id")
    List<BuyerPt> findMemBuyers(Long member_id);
}

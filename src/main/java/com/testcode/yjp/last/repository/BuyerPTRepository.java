package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.BuyerPt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BuyerPTRepository extends JpaRepository<BuyerPt, Long> {

    @Query("select b from BuyerPt b where b.member.id=:member_id and b.trainerInfo.id=:trainer_id")
    BuyerPt findPayInfo(Long member_id, Long trainer_id);

    @Transactional
    @Modifying
    @Query("delete from BuyerPt b where b.member.id=:member_id and b.trainerInfo.id=:trainer_id")
    void deleteInfo(Long member_id, Long trainer_id);
}

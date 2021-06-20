package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Modifying
    @Query("update Notice N set N.hit = N.hit + 1 where N.id = :id")
    int updateView(Long id);

}

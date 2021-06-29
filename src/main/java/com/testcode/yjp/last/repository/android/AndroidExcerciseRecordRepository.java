package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.InBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AndroidExcerciseRecordRepository extends JpaRepository<ExRecord, Long> {

    //    @Query("select e from ExRecord e where e.ex_record_member_id=:user_id order by e.ex_date")
    @Query("select e from ExRecord e")
    ArrayList<ExRecord> findByUserId(String user_id);


}

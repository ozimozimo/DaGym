package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Comment;
import com.testcode.yjp.last.domain.calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CalendarRepository extends JpaRepository<calendar, Long> {


    @Query("select c from calendar c where c.member.id=:id")
    List<calendar> findAll(Long id);
//
//    @Query("select c from calendar c where c.member.id=:id ")
//    List<calendar> findAllData(Long id, String startDate, String endDate);


}

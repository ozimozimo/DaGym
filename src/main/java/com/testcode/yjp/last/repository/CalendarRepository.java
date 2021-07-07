package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Calendar;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {


    @Query("select c from Calendar c where c.member.id=:id")
    List<Calendar> findAll(Long id);

    @Modifying
    @Query("delete from Calendar c where c.start=:calendar_start and c.end=:calendar_end")
    void deleteCalendar(String calendar_start, String calendar_end);

    @Query("select c from Calendar c where c.start=:calendar_start and c.end=:calendar_end")
    Calendar findCalendar(String calendar_start, String calendar_end);

    @Query("select c from Calendar c where c.member.id=:member_id and c.trainerInfo.id=:trainer_id")
    List<Calendar> findAllDesc(Long member_id, Long trainer_id);

    @Query("select c from Calendar c where c.member.id=:member_id and c.trainerInfo.id=:trainer_id")
    List<Calendar> findPT(Long member_id, Long trainer_id);

    @Query("select c from Calendar c where c.trainerInfo.id=:id")
    List<Calendar> findPTAll(Long id);


//    @Query("select c from calendar c where c.member.id=:id ")
//    List<calendar> findAllData(Long id, String startDate, String endDate);


}

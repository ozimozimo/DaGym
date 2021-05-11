package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Calendar;
import com.testcode.yjp.last.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AndroidCalendarRepository extends JpaRepository<Calendar,Long> {
    @Query("select m from Calendar m where m.member=:member")
    ArrayList<Calendar> findCalendarByMember(Member member);
}

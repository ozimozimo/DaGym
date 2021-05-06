package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.android.AndMemberMypageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AndroidPTUserRepository extends JpaRepository<PTUser, Long> {
    //name 검색
    @Query("select m from Member m where m.user_role like 'trainer' order by m.user_name")
    ArrayList<Member> findTrainerAll();

    //id 검색  m.user_role like 'trainer' and
    @Query("select m from Member m where m.user_role like 'trainer' and (m.user_id like %:search% or m.user_name like %:search%) order by m.user_name")
    ArrayList<Member> findTrainer(String search);

    @Query("select m.trainer_id from PTUser m where m.member_id = :member order by m.user_name")
    Member selectTrainer(Member member);

    @Query("select m from PTUser m where m.trainer_id = :member and m.accept_condition = '1' order by m.user_name")
    ArrayList<PTUser> selectMember(Member member);

    @Query("select m from PTUser m where m.trainer_id = :member and m.accept_condition = '0'")
    ArrayList<PTUser> requestList(Member member);

    @Query("select m from PTUser m where m.trainer_id = :trainer and m.user_id = :user")
    PTUser findPTUserBy(String user, Member trainer);

}
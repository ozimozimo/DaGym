package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AndroidMemberRepository extends JpaRepository<Member, Long> {
    //    안드로이드 id찾기
    @Query("select m from Member m where user_name = :user_name and user_pn = :user_pn")
    Member findId(String user_name, String user_pn);

    //    안드로이드 pw찾기
    @Query("select m from Member m where user_name = :user_name and user_pn = :user_pn and user_id = :user_id")
    Member findPw(String user_name, String user_pn, String user_id);

    //    안드로이드 폰 중복확인
    @Query("select m from Member m where user_pn = :user_pn")
    Member findPn(String user_pn);

    @Query("select m from Member m where user_id = :user_id")
    Member findByUser_id(String user_id);
}

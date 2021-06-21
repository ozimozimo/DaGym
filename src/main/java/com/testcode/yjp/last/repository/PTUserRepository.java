package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PTUserRepository extends JpaRepository<PTUser, Long> {
    //name 검색
    @Query("select m from Member m where m.user_role like 'trainer' and m.user_name like %:user_name% ")
    List<Member> findTrainerN(String user_name);

    //id 검색
    @Query("select m from Member m where m.user_role like 'trainer' and m.user_id like %:user_id% ")
    List<Member> findTrainerI(String user_id);

    //헬스장 검색
    @Query("select m from Member m where m.user_role like 'trainer' and m.address_detail like %:address_detail%")
    List<Member> findTrainerA(String address_detail);

    //id로 select
    @Query("select m from Member m where member_id like %:member_id% ")
    List<Member> findMemberId(Long member_id);;

    // 트레이너한테 신청한 목록
    @Query(value = "select p from PTUser p where p.trainer_id.id =:trainer_id and p.accept_condition = '0'")
    List<PTUser> findApply(Long trainer_id);

    // 트레이너가 수락한 회원 목록
    @Query(value = "select * from PT_User where trainer_id = :trainer and accept_condition = 1", nativeQuery = true)
    ArrayList<PTUser> findAccept(Member trainer);

    // 신청한 회원 있는지 찾기
    @Query(value = "select * from PT_User where MEMBER_ID = :member", nativeQuery = true)
    ArrayList<PTUser> checkApply(Member member);

    @Query(value = "select count(*) from PT_User where trainer_id = :trainer and accept_condition = 0", nativeQuery = true)
    ArrayList<PTUser> countApply(Member trainer);

    // 전체 트레이너 + 카운트
//    @Query(value = "select m.id, (select count(p.trainer_id) from PTUser p where p.trainer_id like m.id group by p.trainer_id) as count from Member m where m.user_role like 'trainer'")
//    @Query(value = "select count(m) as getCount from Member m where m.user_role like 'trainer'")
//    int selectTrainerAndCount();

    // 전체 트레이너 조회
    @Query("select m from Member m where m.user_role like 'trainer'")
    List<Member> selectTrainer();

    // pt신청 endDate와 오늘 날짜 비교해서 endDate지나면 수락상태 3으로 변경
//    @Query("select m from PTUser m " +
//            "where (m.member_id = :member " +
//            "or m.trainer_id = :member )" +
//            "and m.accept_condition = '1' " +
//            "and m.end_date < :today")
//    ArrayList<PTUser> endDate(Member member, String today);


}
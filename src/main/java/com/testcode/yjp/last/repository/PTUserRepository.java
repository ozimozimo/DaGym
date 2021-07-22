package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
    @Query(value = "select p from PTUser p where p.trainer_id.member.id =:trainer_id and p.accept_condition = '0'")
    ArrayList<PTUser> findApply(Long trainer_id);

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

    @Query("select p from PTUser p where p.trainer_id.id=:trainer_id")
    PTUser findPtUserId(Long trainer_id);

    @Query("select p from PTUser p where p.trainer_id.id=:trainer_id and p.accept_condition='1' and p.pt_end=0")
    List<PTUser> findByUser(Long trainer_id);

    @Query("select p from PTUser p where p.member_id.id=:member_id and p.accept_condition='1'" )
    PTUser findCheckApply(Long member_id);

    @Query("select p from PTUser p where p.member_id=:member and (p.accept_condition='1' or p.accept_condition='0')" )
    PTUser findCheckApply0or1(Member member);

    @Query("select p from PTUser p where p.member_id.id=:id and p.accept_condition='1'")
    List<PTUser> findPTState(Long id);

    @Query("select p from PTUser p where p.member_id.id=:id and p.accept_condition='1'")
    PTUser findMYTrainer(Long id);

    @Query("select p from PTUser p where p.member_id.id=:id and p.accept_condition='1' and p.member_id.user_role='user'")
    PTUser loginCheckState(Long id);

    @Transactional
    @Modifying
    @Query("update PTUser p set p.pt_times = p.pt_times - 1 where p.member_id.id=:member_id and p.trainer_id.id=:trainer_id")
    void update(Long member_id, Long trainer_id);

    @Query("select p from PTUser p where p.member_id.id=:member_id and p.trainer_id.id=:trainer_id")
    PTUser findByInfo(Long member_id, Long trainer_id);

    @Query("select p from PTUser p where p.member_id.id=:member_id")
    PTUser findMemberAPPly(Long member_id);

    @Transactional
    @Modifying
    @Query("delete from PTUser p where p.id=:pt_id and p.pt_times=0")
    void deletePT(Long pt_id);

    @Transactional
    @Modifying
    @Query("delete from PTUser p where p.member_id.id=:member_id and p.trainer_id.id=:trainer_id")
    void deleteByInfo(Long member_id, Long trainer_id);

    @Transactional
    @Modifying
    @Query("update PTUser p set p.pt_end=2 where p.member_id.id=:member_id and p.trainer_id.id=:trainer_id")
    void acceptAdd(Long member_id, Long trainer_id);

    @Transactional
    @Modifying
    @Query("update PTUser p set p.pt_times = p.pt_times + 1 where p.member_id.id=:member_id and p.trainer_id.id=:trainer_id")
    void Ptupdate(Long member_id, Long trainer_id);

    // pt신청 endDate와 오늘 날짜 비교해서 endDate지나면 수락상태 3으로 변경
//    @Query("select m from PTUser m " +
//            "where (m.member_id = :member " +
//            "or m.trainer_id = :member )" +
//            "and m.accept_condition = '1' " +
//            "and m.end_date < :today")
//    ArrayList<PTUser> endDate(Member member, String today);


}
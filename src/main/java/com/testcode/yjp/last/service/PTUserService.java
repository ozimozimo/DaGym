package com.testcode.yjp.last.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.testcode.yjp.last.domain.*;
import com.testcode.yjp.last.domain.dto.*;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.PTUserRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import javafx.scene.shape.TriangleMesh;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PTUserService {

    private final PTUserRepository ptUserRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public List<MemberList> getMemberList() {
        List<Member> members = ptUserRepository.selectTrainer();
        List<MemberList> memberLists = new ArrayList<>();

//        List<TrainerCountInterface> trainerCountDtos = ptUserRepository.selectTrainerAndCount();
//        log.info("count = " + trainerCountDtos);

        for (Member member : members) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }

    //이름검색
    public List<MemberList> nameSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerN(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }

    //id 검색
    public List<MemberList> idSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerI(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }

    //헬스장 검색
    public List<MemberList> addrSearch(String search) {
        log.info("service = " + search);
        List<Member> trainer = ptUserRepository.findTrainerA(search);
        List<MemberList> memberLists = new ArrayList<>();

        for (Member member : trainer) {
            MemberList memberList = getMemberList(member);
            memberLists.add(memberList);
        }
        log.info("memberLists = " + memberLists.toString());

        return memberLists;
    }


    public List<MemberList> getMemberList(Long member_id) {
        List<Member> memberEntities = ptUserRepository.findMemberId(member_id);
        List<MemberList> memberLists = new ArrayList<>();
        for (Member member : memberEntities) {
            MemberList memberList = MemberList
                    .builder()
                    .id(member.getId())
                    .user_id(member.getUser_id())
                    .user_pw(member.getUser_pw())
                    .user_name(member.getUser_name())
                    .user_pn(member.getUser_pn())
                    .user_email(member.getUser_email())
                    .user_rrn(member.getUser_rrn())
                    .user_gender(member.getUser_gender())
                    .address_normal(member.getAddress_normal())
                    .address_detail(member.getAddress_detail())
                    .user_role(member.getUser_role())
                    .build();
            memberLists.add(memberList);
        }
        return memberLists;
    }

    private MemberList getMemberList(Member member) {
        return MemberList
                .builder()
                .id(member.getId())
                .user_name(member.getUser_name())
                .user_id(member.getUser_id())
                .user_pn(member.getUser_pn())
                .address_normal(member.getAddress_normal())
                .address_detail(member.getAddress_detail())
                .build();
    }

//    // 신청 전에 확인하기
//    public ArrayList<PTUserApplyMemberDto> getCheckList(Long member_id) {
//        // 트레이너 아이디 조회하면 신청했는 회원 아이디도 딸려온다
//        Member member = memberRepository.findById(member_id).get();
//        log.info("member_id = " + member);
//        ArrayList<PTUser> ptUsers = ptUserRepository.checkApply(member);
//        ArrayList<PTUserApplyMemberDto> ptUserApplyCheckDtos = new ArrayList<>();
//        for(PTUser ptUser : ptUsers) {
//            PTUserApplyMemberDto ptUserApplyCheckDto = new PTUserApplyMemberDto(
//                    ptUser.getId(),
//                    ptUser.getMember_id().getId(),
//                    ptUser.getUser_id(),
//                    ptUser.getUser_name(),
//                    ptUser.getStart_date(),
//                    ptUser.getEnd_date(),
//                    ptUser.getAccept_condition(),
//                    ptUser.getTrainer_id().getId(),
//                    ptUser.getUser_pn()
//            );
//            ptUserApplyCheckDtos.add(ptUserApplyCheckDto);
//            log.info("ptUserApplyCheckDtos = " + ptUserApplyCheckDtos);
//        }
//        return ptUserApplyCheckDtos;
//    }
//
    // 수락회원 조회하기
    public List<PTUserApplyMemberDto> getAcceptList(Long id) {

        // trainer 의 member Id 를 받아옴
        TrainerInfo trainer = trainerRepository.findTrainer_id(id);
        Long trainer_id = trainer.getId();

        // trainer_id 를 먼저 구하고 query 문을 사용해서 accept = 1이고 trainer_id 한테 신청한 내역을 조회한다

        return ptUserRepository.findByUser(trainer_id).stream()
                .map(PTUserApplyMemberDto::new)
                .collect(Collectors.toList());
    }

    // 신청내용 조회하기
    public List<PTMemberInfoDto> getPTUserApply(Long member_id) {
        log.info("ptUserService");

        TrainerInfo trainer = trainerRepository.findTrainer_id(member_id);

        Long trainer_id = trainer.getId();



        return ptUserRepository.findApply(trainer_id).stream()
                .map(PTMemberInfoDto::new)
                .collect(Collectors.toList());
    }

    // 신청온거 표시
//    public ArrayList<PTUserApplyMemberDto> getApplyCount(Long trainer_id) {
//        Member trainer = memberRepository.findById(trainer_id).get();
//        // member에서 trainer_id들만 찾아와서 trainer에 넣는다
//        ArrayList<PTUser> ptUsers = ptUserRepository.countApply(trainer);
//        // PTUser에서 위에서 넣은 trainer만 찾아서 ptUsers에 넣는다
//        ArrayList<PTUserApplyMemberDto> ptUserCount = new ArrayList<>();
//        // PTUserApply를 새로운 ArrayList로 선언하고
//        for (PTUser ptUser : ptUsers) {
//            // for each문 써서 ptUsers에 있는 값들을 ptUser에 넣어준다는데 이게 뭔소리지 시발
//            // ptUser에서 가져온 것들을 PTUserApply에 맞춰서 넣어준다
//            PTUserApplyMemberDto ptUserApplyMemberDto = new PTUserApplyMemberDto(
//                    ptUser.getId(),
//                    ptUser.getAccept_condition(),
//                    ptUser.getTrainer_id().getId()
//            );
//            ptUserCount.add(ptUserApplyMemberDto);
//            log.info("ptUserApplies = " + ptUserCount);
//        }
//        return ptUserCount;
//    }


    // trainer.js에 updateAccpet()에서 받아온 값이 PTUserApplyConDto에 들어가있다
    // 거기서 받아온 id로 신청한 id를 찾아낸다
    // 그 id에 해당하는 accept_condition을 받아온 apply_if에 맞게 바꿔준다
    public Long update(Long pt_user_id, PTUserApplyConDto ptUserApplyConDto) {
        log.info("ptuser update post service");

//        TrainerInfo trainer = trainerRepository.findTrainer_id(id);
//        Long trainer_id = trainer.getId();
//
//        PTUser ptUserId = ptUserRepository.findPtUserId(trainer_id);
//        Long ptPk = ptUserId.getId();
//
//        PTUser ptUser = ptUserRepository.findById(ptPk).get();

        PTUser ptUser = ptUserRepository.findById(pt_user_id).get();

        ptUser.update(ptUserApplyConDto.getApply_if());
        System.out.println("신청상태 : " + ptUserApplyConDto.getApply_if());
        ptUserRepository.save(ptUser);
        return ptUser.getId();
    }

    // (기간만료) pt신청 endDate와 오늘 날짜 비교해서 endDate지나면 수락상태 3으로 변경
//   public void endDate(Member member) {
//        Date today = new Date();
//        log.info("today = " + today.toString());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String format = simpleDateFormat.format(today);
//        log.info("format = " + format);
//        ArrayList<PTUser> ptUsers = ptUserRepository.endDate(member, format);
//        log.info("ptUsers = " + ptUsers.toString());
//        for (PTUser ptUser : ptUsers) {
//            log.info("for in");
//            ptUser.setAccept_condition("3");
//            log.info("condition 3");
//            ptUserRepository.save(ptUser);
//            log.info("save succ");
//         }
//
//    }

    public PageResultDto<TrainerInfoDto, TrainerInfo> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(pageRequestDto);
        Page<TrainerInfo> result = trainerRepository.findAll(booleanBuilder, pageable);
        Function<TrainerInfo, TrainerInfoDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

    private TrainerInfoDto entityToDto(TrainerInfo entity) {
        TrainerInfoDto dto = TrainerInfoDto.builder()
                .id(entity.getId())
                .user_id(entity.getMember().getUser_id())
                .imgName(entity.getImgName())
                .fileName(entity.getFileName())
                .user_pn(entity.getMember().getUser_pn())
                .trainer_workTime(entity.getTrainer_workTime())
                .trainer_category(entity.getTrainer_category())
                .trainer_kakao(entity.getTrainer_kakao())
                .trainer_instagram(entity.getTrainer_instagram())
                .user_name(entity.getMember().getUser_name())
                .trainer_content(entity.getTrainer_content())
                .build();

        return dto;
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {  // Querydsl처리
        String type = requestDto.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QTrainerInfo qTrainerInfo = QTrainerInfo.trainerInfo;
        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qTrainerInfo.id.gt(0L);
        booleanBuilder.and(expression);

        //검색조건이 없는경우
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        // 검색조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("n")) {
            conditionBuilder.or(qTrainerInfo.member.user_name.contains(keyword));
        }
        if (type.contains("g")) {
            conditionBuilder.or(qTrainerInfo.trainer_gymName.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qTrainerInfo.trainer_category.contains(keyword));
        }
        if (type.contains("k")) {
            conditionBuilder.or(qTrainerInfo.trainer_kakao.contains(keyword));
        }
        if (type.contains("i")) {
            conditionBuilder.or(qTrainerInfo.trainer_instagram.contains(keyword));
        }
        if (type.contains("p")) {
            conditionBuilder.or(qTrainerInfo.trainer_content.contains(keyword));
        }

        // 모든조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }


    public void save(PTMemberInfoDto ptMemberInfoDto) {

        PTUser ptUser = PTUser.builder()
                .member_height(ptMemberInfoDto.getMember_height())
                .member_weight(ptMemberInfoDto.getMember_weight())
                .pt_purpose(ptMemberInfoDto.getPt_purpose())
                .pt_count(ptMemberInfoDto.getPt_count())
                .pt_positiveDate(ptMemberInfoDto.getPt_positiveDate())
                .pt_wantTime(ptMemberInfoDto.getPt_wantTime())
                .accept_condition(ptMemberInfoDto.getAccept_condition())
                .member_id(ptMemberInfoDto.getMember())
                .trainer_id(ptMemberInfoDto.getTrainer())
                .build();
        ptUserRepository.save(ptUser);
    }
}
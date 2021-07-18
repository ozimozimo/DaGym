package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.PTUser;
import com.testcode.yjp.last.domain.dto.android.*;
import com.testcode.yjp.last.repository.android.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/ptUser")
public class AndroidPTUserController {
//    private final AndroidPTUserRepository androidPTUserRepository;
    private final AndroidMemberRepository androidMemberRepository;
//    private final AndPTUserService andPTUserService;
    private final AndroidCalendarRepository androidCalendarRepository;
    private final AndroidInBodyRepository androidInBodyRepository;
    private final AndroidExcerciseRecordRepository androidExcerciseRecordRepository;


//    // 트레이너 검색
//    @PostMapping("/search")
//    public ArrayList<AndPTUserSearchDto> search(@RequestBody AndTrainerSearchDto trainerSearchDto) {
//        String search = trainerSearchDto.getSearch();
//
//        //  search = 검색어, 서비스에 검색어 넣어서 보내주고,
//        //  ArrayList<AndPTUserSearchDto>로 리턴받아줌.
//        return andPTUserService.getTrainers(search);
//    }
//
//    // 트레이너 전체조회
//    @GetMapping("/selectAll")
//    public ArrayList<AndPTUserSearchDto> searchAll() {
//        // repository에서 모든 trainer 찾아옴.
//        ArrayList<Member> trainerAll = androidPTUserRepository.findTrainerAll();
//
//        // search()에서와 같이 ArrayList<Member> -> ArrayList<AndPTUserSearchDto> 변환 작업
//        return andPTUserService.getTrainers(trainerAll);
//    }
//
//    // 신청
////    @PostMapping("/apply")
////    public void applyTo(@RequestBody AndPTUserSaveDto andPTUserSaveDto) {
////        String start_date = andPTUserSaveDto.getStart_date();
////        String end_date = andPTUserSaveDto.getEnd_date();
////        Long member_id = andPTUserSaveDto.getMember_id();
////        Long trainer_id = andPTUserSaveDto.getTrainer_id();
////        log.info("member_id = " + member_id + ", trainer_id = " + trainer_id + ", start_date = " + start_date + ", end_date = " + end_date);
////
////        andPTUserService.extracted(member_id, trainer_id, andPTUserSaveDto);
////    }
//
////    // 일반회원 -> 자기 트레이너 조회
////    @GetMapping("/find/trainer/{member_id}")
////    public AndMemberMypageDto selectTrainers(@PathVariable("member_id") Long member_id) {
////        log.info("selectTrainers in + :" + member_id);
////        // 회원이 자신의 트레이너 조회 (회원의 친구창)
////        return andPTUserService.getTrainers(member_id);
////    }
////
////    // 트레이너 -> 자기회원 조회
////    @GetMapping("/find/member/{trainer_id}")
////    public ArrayList<AndPTUserSearchDto> selectMembers(@PathVariable("trainer_id") Long trainer_id) {
////        // 트레이너가 자신의 회원들 조회 (트레이너의 친구창)
////        return andPTUserService.getMembers(trainer_id);
////    }
//
//    //신청 갯수
//    @PostMapping("/apply/request")
//    public int requestList(@RequestBody Long member_id) {
//        Member member = androidMemberRepository.findById(member_id).get();
//        // 트레이너(자신)에게 온 신청갯수, condition이 0인것만.
//        // 이거는 안드로이드에서 알림창에 알림 갯수 알려고 만든것.
//        ArrayList<PTUser> ptUsers = androidPTUserRepository.requestList(member);
//        return ptUsers.size();
//    }
//
////    //신청온 회원 확인
////    @PostMapping("/apply/findMember")
////    public ArrayList<AndPTUserApplyMemberDto> applyMember(@RequestBody Long member_id) {
////        // 서비스에서 신청온 회원들을 받아옴 ArrayList<AndPTUserApplyMemberDto> 리턴.
////        ArrayList<AndPTUserApplyMemberDto> andPTUserApplyMemberDtos = andPTUserService.getAndPTUserSearchDtos(member_id);
////
////        return andPTUserApplyMemberDtos;
////    }
//
//    // 신청 수락인지 거절인지 판단. 후 update
//    @PutMapping("/apply/if")
//    public void applyIf(@RequestBody AndPTUserApply andPTUserApply) {
//        String user_id = andPTUserApply.getUser_id();
//        Long trainer_id = andPTUserApply.getTrainer_id();
//        String apply_if = andPTUserApply.getApply_if();
//        log.info("member_id = " + user_id + "trainer_id = " + trainer_id);
//
//        // 해당 트레이너의 Member를 받아옴
//        Member trainer = androidMemberRepository.findById(trainer_id).get();
//
//        // 회원의 user_id와 트레이너로 PTUser를 찾아옴
//        PTUser ptUserBy = androidPTUserRepository.findPTUserBy(user_id, trainer);
//        // apply_if 가 수락 or 거절로 받아와서 수락일 경우 condition 1로 변경
//        // 거절일 경우 2로 변경
//        if (apply_if.equals("수락")) {
//            ptUserBy.setAccept_condition("1");
//        } else if (apply_if.equals("거절")) {
//            ptUserBy.setAccept_condition("2");
//        }
//
//        // save해서 update해줌.
//        androidPTUserRepository.save(ptUserBy);
//    }
//
//    @PostMapping("/userfind/member")
//    public Member findMember(@RequestBody String user_id) {
//        user_id = user_id.replaceAll("\\\"", "");
//        return androidMemberRepository.findByUser_id(user_id);
//    }
//
////    @Transactional
////    @PostMapping("/user/allfind")
////    public Map<String, Object> allFind(@RequestBody String user_id) {
////        user_id = user_id.replaceAll("\\\"", "");
////        Member member = androidMemberRepository.findByUser_id(user_id);
////        ArrayList<Calendar> calendars = androidCalendarRepository.findCalendarByMember(member);
////        ArrayList<InBody> inbodys = androidInBodyRepository.findByUserId(user_id);
////
////        Map<String, Object> result = new HashMap<String, Object>();
////
////        log.info("member = " + member);
////        result.put("member", member);
////        result.put("calendar", calendars);
////        result.put("inbody", inbodys);
////
////
////        return result;
////    }
//
}

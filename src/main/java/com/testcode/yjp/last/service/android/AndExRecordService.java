package com.testcode.yjp.last.service.android;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.android.AndExerciseRecordDto;
import com.testcode.yjp.last.repository.android.AndroidExcerciseRecordRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class AndExRecordService {

    private final AndroidExcerciseRecordRepository excerciseRecordRepository;
    private final AndroidMemberRepository androidMemberRepository;


    // 운동기록 조회
    public ArrayList<AndExerciseRecordDto> select(AndExerciseRecordDto exerciseRecordDto) {
        String exRecord_user_id = exerciseRecordDto.getEx_record_member_id();
        ArrayList<ExRecord> byUserId = excerciseRecordRepository.findByUserId(exRecord_user_id);
        ArrayList<AndExerciseRecordDto> andExrecordDtos = new ArrayList<>();
        for (ExRecord record : byUserId) {
            AndExerciseRecordDto dto = new AndExerciseRecordDto();
            dto.setEx_record_id(record.getEx_record_id());
            dto.setEx_record_member_id(record.getEx_record_member_id());
            dto.setEx_name(record.getEx_name());
            dto.setEx_set(record.getEx_set());
            dto.setEx_weight(record.getEx_weight());
            dto.setEx_count(record.getEx_count());
            dto.setEx_date(record.getEx_date());
            andExrecordDtos.add(dto);
        }
        return andExrecordDtos;
    }


    // 운동기록 저장
    public Long save(Long id, AndExerciseRecordDto exerciseRecordDto) {
        Member member = androidMemberRepository.findById(id).get();
        ExRecord exRecord = new ExRecord();
        exRecord.setEx_record_member_id(member.getUser_id());
        exRecord.setEx_name(exerciseRecordDto.getEx_name());
        exRecord.setEx_set(exerciseRecordDto.getEx_set());
        exRecord.setEx_weight(exerciseRecordDto.getEx_weight());
        exRecord.setEx_count(exerciseRecordDto.getEx_count());
        exRecord.setEx_date(exerciseRecordDto.getEx_date());
        exRecord.setMember(member);
        excerciseRecordRepository.save(exRecord);
        return id;
    }

    // 운동기록 업데이트
    public Long update(Long id, AndExerciseRecordDto exerciseRecordDto) {
        ExRecord result = excerciseRecordRepository.findById(id).get();

        result.ExRecordUpdate(exerciseRecordDto);
        excerciseRecordRepository.save(result);
        return id;
    }

    public Long delete(Long id) {
        excerciseRecordRepository.deleteById(id);
        return id;
    }
}

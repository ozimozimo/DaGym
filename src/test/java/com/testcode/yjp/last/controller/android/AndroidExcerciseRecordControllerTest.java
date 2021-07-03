package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.ExRecord;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.ExerciseRecordDto;
import com.testcode.yjp.last.domain.dto.android.AndExerciseRecordDto;
import com.testcode.yjp.last.repository.android.AndroidExcerciseRecordRepository;
import com.testcode.yjp.last.repository.android.AndroidMemberRepository;
import com.testcode.yjp.last.service.android.AndExRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AndroidExcerciseRecordControllerTest {

    @Autowired
    public AndroidMemberRepository androidMemberRepository;

    @Autowired
    public AndroidExcerciseRecordRepository excerciseRecordRepository;

    @Autowired
    public AndExRecordService andExRecordService;

    // 삽입
    @Test
    public void insert() {
        long id = (long) 61;
        Member member = Member.builder().id(id).build();
        System.out.println(member);
        AndExerciseRecordDto dto = new AndExerciseRecordDto();
        dto.setEx_set("test");
        dto.setEx_weight("test");
        dto.setEx_count("count");
        dto.setEx_date("date");

        andExRecordService.save(member.getId(), dto);
    }

    //조회
    @Test
    public void select() {
        AndExerciseRecordDto dto = new AndExerciseRecordDto();
        dto.setEx_set("test");
        dto.setEx_weight("test");
        dto.setEx_count("count");
        dto.setEx_date("date");
    }

    //삭제
    @Test
    public void delete() {
        long id = (long) 122;
        ExRecord record = ExRecord.builder().ex_record_id(id).build();
        andExRecordService.delete(record.getEx_record_id());
    }



    //수정
    @Test
    public void update() {
        long id = (long) 123;
        ExRecord record = ExRecord.builder().ex_record_id(id).build();

        AndExerciseRecordDto dto = new AndExerciseRecordDto();
        dto.setEx_set("update");
        dto.setEx_weight("update");
        dto.setEx_count("update");
        dto.setEx_date("update");

        andExRecordService.update(record.getEx_record_id(), dto);


    }


}
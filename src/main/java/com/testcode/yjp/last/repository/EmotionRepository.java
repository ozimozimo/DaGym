package com.testcode.yjp.last.repository;


import com.testcode.yjp.last.domain.Boastboard;
import com.testcode.yjp.last.domain.Emotion;
import com.testcode.yjp.last.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    @Query("select e from Emotion e where e.member = :member and e.emotion = :emotion and e.boastboard = :boastboard")
    Emotion findByAll(Member member, String emotion, Boastboard boastboard);

    @Query("select e from Emotion e where e.member = :member and e.emotion not like :emotion and e.boastboard = :boastboard")
    Emotion findNotEmo(Member member, String emotion, Boastboard boastboard);

    @Query("select e from Emotion e where e.boastboard = :boastboard")
    List<Emotion> findByEmotion(Boastboard boastboard);

}

package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.TrReview;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrReviewRepository extends JpaRepository<TrReview, Long> {

    @Query("select t from TrReview t where t.trainer_id.id=:trainer_id")
    List<TrReview> reviewList(Long trainer_id);
}

package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.InBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AndroidInBodyRepository extends JpaRepository<InBody,Long> {
    @Query("select m from InBody m where m.inBody_user_id = :user_id order by m.inBody_date")
    ArrayList<InBody> findByUserId(String user_id);

    @Query("select m from InBody m where m.inBody_user_id = :user_id and m.inBody_date like :date%")
    InBody findByInBody(String user_id, String date);
}

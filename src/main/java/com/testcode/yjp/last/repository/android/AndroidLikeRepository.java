package com.testcode.yjp.last.repository.android;

import com.testcode.yjp.last.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidLikeRepository extends JpaRepository<Likes, Long> {

}

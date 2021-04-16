package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {



    @Query("delete from BoardImage b where b.imgName=:data")
    void deleteByUuid(String data);
}

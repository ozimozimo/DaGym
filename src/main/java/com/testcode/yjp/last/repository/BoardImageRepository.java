package com.testcode.yjp.last.repository;

import com.testcode.yjp.last.domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {


    @Transactional
    @Modifying
    @Query("delete from BoardImage b where b.imgName=:imageName")
    void deleteByImgName(String imageName);
}

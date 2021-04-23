package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.BoardImage;
import com.testcode.yjp.last.domain.dto.UploadResultDto;
import com.testcode.yjp.last.repository.BoardImageRepository;
import com.testcode.yjp.last.service.BoardImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    @Value("${org.zerock.upload.path}")// applicaton.properties의변수
    private String uploadPath;

    private final BoardImageService boardImageService;
    private final BoardImageRepository boardImageRepository;
    
    @PostMapping("/uploadFile")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles) {

        System.out.println(uploadFiles);

        List<UploadResultDto> resultDtoList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {
            
            // 이미지 파일만 업로드 가능
            if(!uploadFile.getContentType().startsWith("image")){
                log.warn("이미지 타입이 아닙니다");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("originalName" + originalName);
            log.info("uploadFile fileName" + fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +
                    "_" + fileName;

            Path savePath = Paths.get(saveName);

            log.info("uuid" + uuid);
            log.info("saveName" + saveName);

            try {
                // 원본 파일 저장
                uploadFile.transferTo(savePath);

                // 섬네일 생성 하는이유 원본 이미지가 그대로 나오면 데이터 낭비가 심해서 사이즈 줄이는 작업
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                // 섬네일 파일 이름은 중간에 s_로 시작하도록 설정
                File thumbnailFile = new File(thumbnailSaveName);

                // 섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,100,100);

                resultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));

                BoardImage boardImage = BoardImage.builder()
                        .uuid(uuid)
                        .imgName(thumbnailSaveName)
                        .build();
                boardImageRepository.save(boardImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } // end
        return new ResponseEntity<>(resultDtoList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        //make folder
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("display file :"+file);
            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        System.out.println(fileName);
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();
            System.out.println("srcFileName ="+srcFileName);
            System.out.println("result =" + result);
            System.out.println("thumbnail =" + thumbnail);

            String data = thumbnail.toString();

//            boardImageRepository.deleteByUuid(data);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

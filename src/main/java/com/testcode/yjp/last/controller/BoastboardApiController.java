package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Boastboard;
import com.testcode.yjp.last.domain.Emotion;
import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.BoardUpdateRequestDto;
import com.testcode.yjp.last.domain.dto.EmoSelectDto;
import com.testcode.yjp.last.domain.dto.PageRequestDto;
import com.testcode.yjp.last.domain.dto.UploadResultDto;
import com.testcode.yjp.last.repository.BoastboardRepository;
import com.testcode.yjp.last.repository.EmotionRepository;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.BoastboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/boastboard")
public class BoastboardApiController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    private String thumbnailSaveName = null;

    private String fileName = null;
    private String uuid = null;
    private String folderPath = null;
    private String srcFileName = null;
    String lastImage = null;
    String loadImageName = null;

    private final MemberRepository memberRepository;
    private final BoastboardRepository boastboardRepository;
    private final BoastboardService boastboardService;
    private final EmotionRepository emotionRepository;

    @PostMapping("/uploadBoardFile")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadBoardFiles) {

        List<UploadResultDto> resultDtoList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadBoardFiles) {

            // 이미지 파일만 업로드 가능
            if (!uploadFile.getContentType().startsWith("image")) {
                log.warn("이미지 타입이 아닙니다");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            String originalName = uploadFile.getOriginalFilename();
            fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("originalName" + originalName);
            log.info("uploadFile fileName" + fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            uuid = UUID.randomUUID().toString();

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
                thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                // 섬네일 파일 이름은 중간에 s_로 시작하도록 설정
                File thumbnailFile = new File(thumbnailSaveName);
                // 섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 640, 427);
                resultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));

                lastImage = fileName;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } // end
        return new ResponseEntity<>(resultDtoList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        folderPath = str.replace("/", File.separator);
        //make folder
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/displayBoard")
    public ResponseEntity<byte[]> getFile(String fileName) {

        System.out.println("파일이름은=?" + fileName);
        loadImageName = fileName;
        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("display file :" + file);
            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(result);

        return result;
    }

    @PostMapping("/removeBoardFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        System.out.println(fileName);

        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();
            System.out.println("srcFileName =" + srcFileName);
            System.out.println("result =" + result);
            System.out.println("thumbnail =" + thumbnail);

            String imageName = thumbnail.toString();
//            boardImageRepository.deleteByImgName(imageName);

            System.out.println("uuid 스트링으로 바꿈" + imageName);
//            boardImageRepository.deleteByUuid(data);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bbInsert/{member_id}")
    public void bbInsert(@PathVariable Long member_id, @RequestBody Boastboard boastboard) {
        Member member = memberRepository.findById(member_id).get();
        boastboard.setMember(member);
        boastboard.setUuid(thumbnailSaveName);
        boastboard.setFileName(fileName);
        boastboardRepository.save(boastboard);
    }

    @PutMapping("/bbUpdate/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto,
                       @ModelAttribute("PageRequestDto") PageRequestDto pageRequestDto,
                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", pageRequestDto.getPage());

        log.info(boardUpdateRequestDto.getUuid());
        log.info(boardUpdateRequestDto.getFileName());
        log.info(boardUpdateRequestDto.getImgName());
        return boastboardService.update(id, boardUpdateRequestDto);


    }

    @DeleteMapping("/bbDelete/{id}")
    public Long delete(@PathVariable Long id) {
        boastboardService.delete(id);
        return id;
    }

    @PostMapping("/emotion/{member_id}/{bb_num}")
    public String emoInsert(@PathVariable("member_id") Long member_id, @PathVariable("bb_num") Long bb_num, @RequestBody Emotion emotion) {
        System.out.println("mem = " + member_id + " bb_num = " + bb_num + "emo = " + emotion.getEmotion());
        return boastboardService.saveEmo(member_id, bb_num, emotion);
    }

    @GetMapping("/emotion/select/{bb_num}")
    public EmoSelectDto emoSelect(@PathVariable("bb_num") Long bb_num) {
        Boastboard boastboard = boastboardRepository.findById(bb_num).get();
        List<Emotion> byEmotion = emotionRepository.findByEmotion(boastboard);
        int lCount=0;
        int mCount=0;
        int sCount=0;
        int aCount=0;
        int wCount=0;
        for (Emotion e : byEmotion) {
            if (e.getEmotion().equals("l")) {
                System.out.println("l = " + e.getEmotion());
                lCount++;
            } else if (e.getEmotion().equals("m")) {
                System.out.println("m = " + e.getEmotion());
                mCount++;
            }else if (e.getEmotion().equals("s")) {
                System.out.println("s = " + e.getEmotion());
                sCount++;
            }else if (e.getEmotion().equals("a")) {
                System.out.println("a = " + e.getEmotion());
                aCount++;
            }else if(e.getEmotion().equals("w")){
                System.out.println("emo = " + e.getEmotion());
                wCount++;
            }
        }
        EmoSelectDto emoSelectDto = EmoSelectDto.builder()
                .m(mCount)
                .a(aCount)
                .l(lCount)
                .s(sCount)
                .w(wCount)
                .build();
        return emoSelectDto;
    }
}

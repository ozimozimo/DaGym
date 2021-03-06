package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.domain.dto.UploadResultDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.TrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class TrainerApiController {

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
    private final TrainerService trainerService;

    @PostMapping("/uploadFile")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDto> resultDtoList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // ????????? ????????? ????????? ??????
            if (!uploadFile.getContentType().startsWith("image")) {
                log.warn("????????? ????????? ????????????");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            // ?????? ?????? ?????? IE??? Edge??? ?????? ????????? ???????????????
            String originalName = uploadFile.getOriginalFilename();
            fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("originalName" + originalName);
            log.info("uploadFile fileName" + fileName);

            // ?????? ?????? ??????
            String folderPath = makeFolder();

            //UUID
            uuid = UUID.randomUUID().toString();

            // ????????? ?????? ?????? ????????? "_"??? ???????????? ??????
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +
                    "_" + fileName;

            Path savePath = Paths.get(saveName);

            log.info("uuid" + uuid);
            log.info("saveName" + saveName);

            try {
                // ?????? ?????? ??????
                uploadFile.transferTo(savePath);
                // ????????? ?????? ???????????? ?????? ???????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ??????
                thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                // ????????? ?????? ????????? ????????? s_??? ??????????????? ??????
                File thumbnailFile = new File(thumbnailSaveName);
                // ????????? ??????
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 640, 427);
                resultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));

                lastImage = fileName;
//                Optional<Member> byId = memberRepository.findById(id);
//                BoardImage boardImage = BoardImage.builder()
//                        .uuid(uuid)
//                        .imgName(thumbnailSaveName)
//                        .build();
//                boardImageRepository.save(boardImage);

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

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        System.out.println("???????????????=?" + fileName);
        loadImageName = fileName;
        ResponseEntity<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("display file :" + file);
            HttpHeaders header = new HttpHeaders();

            //MIME?????? ??????
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //?????? ???????????????
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

            System.out.println("uuid ??????????????? ??????" + imageName);
//            boardImageRepository.deleteByUuid(data);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/trainer/trInfo/{id}")
    public TrainerInfoDto save(@PathVariable Long id, @RequestBody TrainerInfoDto trainerInfoDto, HttpServletRequest request) {

        log.info("trainer ?????? ?????? ????????? controller ");

        log.info(trainerInfoDto.getTrainer_content());
        log.info(trainerInfoDto.getFileName());
        log.info(trainerInfoDto.getImgName());
        log.info(id + "-===================================");

        Optional<Member> byId = memberRepository.findById(id);
        trainerInfoDto.setId(id);
        trainerInfoDto.setMember(byId.get());
        trainerInfoDto.setUuid(thumbnailSaveName);
        trainerInfoDto.setFileName(fileName);


        trainerService.save(trainerInfoDto);
        HttpSession session = (HttpSession) request.getSession();
        session.removeAttribute("loginUser");
        session.invalidate();
        return trainerInfoDto;
    }

    @PostMapping("/trainer/trUpdate/{id}")
    public TrainerInfo trUpdate(@PathVariable Long id, @RequestBody TrainerInfoDto trainerInfoDto) {
        log.info("trUpdate Controller postMapping");
        log.info(id);
        log.info(trainerInfoDto);
        log.info(trainerInfoDto.getTrainer_pt_discount());
        log.info(trainerInfoDto.getTrainer_pt_AddCount());

        return trainerService.update(id, trainerInfoDto);
    }

}

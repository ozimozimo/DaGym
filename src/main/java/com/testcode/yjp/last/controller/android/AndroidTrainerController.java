package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.domain.dto.TrainerInfoDto;
import com.testcode.yjp.last.domain.dto.UploadResultDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.repository.TrainerRepository;
import com.testcode.yjp.last.service.android.AndTrainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/android/trainer")
public class AndroidTrainerController {
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final AndTrainerService andTrainerService;


    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    private String thumbnailSaveName = null;

    private String fileName = null;
    private String uuid = null;
    private String folderPath = null;
    private String srcFileName = null;

    @PostMapping("/trInfo/{id}")
    public TrainerInfoDto trInfo(@PathVariable Long id, @RequestBody TrainerInfoDto trainerInfoDto) {
        Optional<Member> byId = memberRepository.findById(id);
        trainerInfoDto.setId(id);
        trainerInfoDto.setMember(byId.get());

        andTrainerService.save(trainerInfoDto);

        return trainerInfoDto;
    }

    @PostMapping("/findTrainer/{id}")
    public TrainerInfoDto findTrainer(@PathVariable("id") Long id) {
        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        TrainerInfoDto trainerInfoDto = new TrainerInfoDto(trainer_id);
        System.out.println(trainerInfoDto);

        return trainerInfoDto;
    }

    @PostMapping("/findTrainers")
    public List<TrainerInfoDto> findTrainers() {
        List<TrainerInfo> all = trainerRepository.findAll();
        List<TrainerInfoDto> trainerInfoDtos = new ArrayList<>();
        for (TrainerInfo t : all) {
            TrainerInfoDto trainerInfoDto = new TrainerInfoDto(t);
            trainerInfoDtos.add(trainerInfoDto);
        }

        return trainerInfoDtos;
    }

    @PostMapping("/findTrainerStr/{id}")
    public String findTrainerStr(@PathVariable("id") Long id) {
        TrainerInfo trainer_id = trainerRepository.findTrainer_id(id);
        if (trainer_id != null) {
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/display")
    public List<byte[]> getFile(String fileName) {
        log.info("fileName=" + fileName);

        List<byte[]> result = null;
        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("display file :" + file);
            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터처리
            result.add(FileCopyUtils.copyToByteArray(file));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result;
        }
        return result;
    }


    @PostMapping("/file")
    public ResponseEntity<List<UploadResultDto>> uploadFile(UploadResultDto uploadFiles) {

        System.out.println(uploadFiles);

        List<UploadResultDto> resultDtoList = new ArrayList<>();

        // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
//            String originalName = uploadFile.getOriginalFilename();
//            fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//            log.info("originalName" + originalName);
//            log.info("uploadFile fileName" + fileName);
//
//            // 날짜 폴더 생성
//            String folderPath = makeFolder();
//
//            //UUID
//            uuid = UUID.randomUUID().toString();
//
//            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
//            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +
//                    "_" + fileName;
//
//            Path savePath = Paths.get(saveName);
//
//            log.info("uuid" + uuid);
//            log.info("saveName" + saveName);
//
//            try {
//                // 원본 파일 저장
//                uploadFile.transferTo(savePath);
//                // 섬네일 생성 하는이유 원본 이미지가 그대로 나오면 데이터 낭비가 심해서 사이즈 줄이는 작업
//                thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
//                // 섬네일 파일 이름은 중간에 s_로 시작하도록 설정
//                File thumbnailFile = new File(thumbnailSaveName);
//                // 섬네일 생성
//                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 640, 427);
//                resultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } // end
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
}

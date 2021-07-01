package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.TrainerInfo;
import com.testcode.yjp.last.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@Log4j2
@RequestMapping("/pt")
public class PTReviewController {

    private final TrainerRepository trainerRepository;

    @GetMapping("/review/{member_id}/{trainer_id}")
    public String review(@PathVariable Long member_id , @PathVariable Long trainer_id, Model model) {

        log.info("member_id =" + member_id);
        log.info("trainer_id =" + trainer_id);
        TrainerInfo trainerInfo = trainerRepository.findById(trainer_id).get();
        model.addAttribute("trainerInfo", trainerInfo);
        model.addAttribute("member_id", member_id);
        model.addAttribute("trainer_id", trainer_id);
        return "ptUser/trainerReview";
    }
}

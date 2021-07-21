package com.testcode.yjp.last.controller.android;

import com.testcode.yjp.last.domain.BuyerPt;
import com.testcode.yjp.last.repository.BuyerPTRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AndroidIamportTest {

    private final BuyerPTRepository buyerPTRepository;

    @RequestMapping(value = "/orderCompleteMobile", produces = "application/text; charset=utf8", method = RequestMethod.GET)
    public String orderCompleteMobile(Model model, @RequestParam String pay_method, @RequestParam(required = false) String imp_uid
            , @RequestParam(required = false) String merchant_uid,
                                      @RequestParam int amount) {

        log.info("pay_method=" + pay_method);
        log.info("pay imp_uid" + imp_uid);
        log.info("pay merchant_uid" + merchant_uid);

        String pt_amount = String.valueOf(amount);

        BuyerPt buyerPt = BuyerPt.builder()
                .pay_method(pay_method)
                .imp_uid(imp_uid)
                .merchant_uid(merchant_uid)
                .pt_amount(pt_amount)
                .build();

        buyerPTRepository.save(buyerPt);
        model.addAttribute("merchant_uid", merchant_uid);

        return "payEnd";
    }

}

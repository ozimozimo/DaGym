package com.testcode.yjp.last.controller;

import com.testcode.yjp.last.domain.Member;
import com.testcode.yjp.last.domain.dto.MemberSoDto;
import com.testcode.yjp.last.repository.MemberRepository;
import com.testcode.yjp.last.service.MemberService;
import com.testcode.yjp.last.service.PTUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Log4j2
@Controller
@RequiredArgsConstructor
public class GoogleController {
    private final MemberService soMemberService;
    private final MemberRepository memberRepository;
    private final PTUserService ptUserService;

    @PostMapping(value = "/login/google")
    public String googleLogin(@RequestBody String param, HttpServletRequest request, HttpServletResponse response) {

        BufferedReader in = null; // 버퍼를 이용. String값을 모아뒀다가 한번에 보내서 오히려 빠름.
        InputStream is = null; // 데이터가 이동하는 통로에서 넣어주는곳
        InputStreamReader isr = null; // 데이터가 이동하는 통로에서 읽는곳
        JSONParser jsonParser = new JSONParser(); //

        String user_id = null; // 리턴할 값

        try {
            String idToken = param.split("=")[1]; // =이 들어가는 곳마다 나눠줌. + 나뉜것중에 2번째배열
            String url = "https://oauth2.googleapis.com/tokeninfo";
            url += "?id_token=" + idToken; // 이 주소에 토큰값 넘겨줌.

            URL gUrl = new URL(url); // URL 저장
            HttpURLConnection conn = (HttpURLConnection) gUrl.openConnection(); //gUrl 연결

            is = conn.getInputStream(); // inputStream 연결
            isr = new InputStreamReader(is, "UTF-8"); // Stream리더에 UTF-8 추가
            in = new BufferedReader(isr); // 버퍼에 inputStream연결

            JSONObject jsonObj = (JSONObject) jsonParser.parse(in);

            user_id = jsonObj.get("sub").toString(); // id값 받아옴.
            String user_name = jsonObj.get("name").toString(); // 이름
            String user_email = jsonObj.get("email").toString(); // 이메일

            log.info(user_id);
            log.info(user_email);
            log.info(user_name);

            MemberSoDto memberGoogleDto = new MemberSoDto(user_id, user_name, user_email);
            Member ckUserId = memberRepository.findByUser_id(user_id);
            if (ckUserId == null) {
                log.info("googleSave");
                soMemberService.googleSave(memberGoogleDto);
            }
            ckUserId = memberRepository.findByUser_id(user_id);

            // pt 기간 만료
            ptUserService.endDate(ckUserId);

            HttpSession session = (HttpSession) request.getSession();
            session.setAttribute("loginUser", ckUserId.getId());
            session.setAttribute("loginName", user_name);
            session.setAttribute("loginRole", ckUserId.getUser_role());
            session.setAttribute("social","social");

            log.info("sendRedirect succ");
//            return "redirect:/"; // redirect: 는 restMapping 안의 경로에서 시작, redirect:/는 도메인 절대경로

            in.close();
            isr.close();
            is.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/"; // restController 쓰니까 return에 주소값 못받아옴.
    }

}
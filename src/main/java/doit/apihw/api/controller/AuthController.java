package doit.apihw.api.controller;

import doit.apihw.api.controller.dto.AuthLoginRequest;
import doit.apihw.api.controller.dto.AuthLoginResponse;
import doit.apihw.api.controller.dto.AuthSignUpRequest;
import doit.apihw.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /* 아래의 코드는 @RequiredArgsConstructor 어노테이션을 사용하면 자동으로 생성되므로 필요 없어짐
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    */

    /**
     * 아이디 중복 체크를 진행한다.
     */
    @GetMapping("/auth/members/validate")
    public void validateLoginId(@RequestParam String memberLoginId) {

        authService.validateLoginId(memberLoginId);
    }

    /**
     * 회원가입을 진행한다.
     */
    @PostMapping("/auth/members")
    public AuthLoginResponse signUp(@RequestBody AuthSignUpRequest request) {
        return authService.signUp(request);
    }

    /**
     * 로그인을 진행한다.
     */
    @PostMapping("/auth/members/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest request) {
        return authService.login(request);
    }


}

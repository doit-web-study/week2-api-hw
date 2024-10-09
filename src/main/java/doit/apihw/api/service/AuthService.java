package doit.apihw.api.service;

import doit.apihw.api.controller.dto.*;
import doit.apihw.domain.member.Member;
import doit.apihw.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    /* 아래의 코드는 @RequiredArgsConstructor 어노테이션을 사용하면 자동으로 생성되므로 필요 없어짐
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
   */

    // 아이디 중복 체크를 진행한다.
    public void validateLoginId(String memberLoginId) {
        // DB에서 memberLoginId를 조회하여 이미 존재하는 아이디인지 확인한다.
        Member member = memberRepository.findByMemberLoginId(memberLoginId);

        // 만약 이미 존재하는 아이디라면, IllegalArgumentException을 발생시킨다.
        if (member != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    // 회원가입을 진행한다.
    public AuthLoginResponse signUp(AuthSignUpRequest request) {
        if (memberRepository.findByMemberLoginId(request.getMemberLoginId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder()
                .memberName(request.getMemberName())
                .memberBirthday(request.getMemberBirthday())
                .memberLoginId(request.getMemberLoginId())
                .memberPassword(request.getMemberPassword())
                .build();

        Member savedMember = memberRepository.save(member);
        return AuthLoginResponse.from(savedMember);
    }

    // TODO : 로그인을 진행한다.
    public AuthLoginResponse login(AuthLoginRequest request) {
        // DB에서 memberLoginId와 memberPassword를 조회하여 일치하는 회원이 있는지 확인한다.
        Member member = memberRepository.findByMemberLoginId(request.getMemberLoginId());

        // 만약 일치하는 회원이 없다면, IllegalArgumentException을 발생시킨다.
        if(member == null){
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        // 일치하는 회원이 있다면, MemberResponse로 변환하여 반환한다.
        return AuthLoginResponse.from(member);
    }


}

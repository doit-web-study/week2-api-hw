package doit.apihw.api.controller;

import doit.apihw.api.controller.dto.AuthPasswordChangeRequest;
import doit.apihw.api.controller.dto.MemberResponse;
import doit.apihw.api.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 정보를 조회한다.
     */
    @GetMapping("/members/{memberId}")
    public MemberResponse findOneMember(@PathVariable Long memberId,
                                        @SessionAttribute(required = false) Long loginId){
        if (loginId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return memberService.findOneMember(memberId);
    }

    /**
     * 전체 회원 정보를 조회한다.
     */
    @GetMapping("/members")
    public List<MemberResponse> findAllMembers(@SessionAttribute(required = false) Long loginId) {

        if (loginId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return memberService.findAllMembers();
    }

    /**
     * 회원 이름으로 회원 정보를 조회한다.
     */
    @GetMapping("/members/search")
    public List<MemberResponse> searchMembers(@RequestParam String memberName,
                                              @SessionAttribute(required = false) Long loginId) {
        if (loginId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return memberService.searchMembersWithName(memberName);
    }

    /**
     * 회원 비밀번호를 변경한다.
     */
    @PostMapping("/members/{memberId}/password")
    public void changePassword(@PathVariable Long memberId, @RequestBody AuthPasswordChangeRequest request,
                               @SessionAttribute(required = false) Long loginId){
        if (loginId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        if (!loginId.equals(memberId)) {
            throw new IllegalArgumentException("본인의 비밀번호만 변경할 수 있습니다.");
        }

        memberService.changePassword(memberId, request);
    }

    // TODO : 자유 주제로 API를 추가로 구현해보세요.
    /**
     * 회원 중 특정 날짜 이전에 태어난 회원 정보를 조회한다.
     */
    @GetMapping("/members/birthday")
    public List<MemberResponse> searchMembersByBirthday(@RequestParam LocalDate date,
                                                        @SessionAttribute(required = false) Long loginId){
        if (loginId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return memberService.searchMembersWithBirthday(date);
    }

    /**
     * 세션에 저장된 회원 정보를 조회한다.
     */
    @GetMapping("/members/session")
    public MemberResponse findMemberInSession(HttpSession session) { // 여기 파라미터 + 밑의 메소드 결과는
        Long memberId = (Long) session.getAttribute("loginId");      // @SessionAttribute(required = false) Long loginId와 동일

        if (memberId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        return memberService.findOneMember(memberId);
    }
}

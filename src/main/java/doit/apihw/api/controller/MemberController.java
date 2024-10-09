package doit.apihw.api.controller;

import doit.apihw.api.controller.dto.AuthPasswordChangeRequest;
import doit.apihw.api.controller.dto.MemberResponse;
import doit.apihw.api.controller.dto.NewNameRequest;
import doit.apihw.api.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 정보를 조회한다.
     */
    @GetMapping("/members/{memberId}")
    public MemberResponse findOneMember(@PathVariable Long memberId) {

        return memberService.findOneMember(memberId);
    }

    /**
     * 전체 회원 정보를 조회한다.
     */
    @GetMapping("/members")
    public List<MemberResponse> findAllMembers() {
        return memberService.findAllMembers();
    }

    /**
     * 회원 이름으로 회원 정보를 조회한다.
     */
    @GetMapping("/members/search")
    public List<MemberResponse> searchMembers(@RequestParam String memberName) {
        return memberService.searchMembersWithName(memberName);
    }

    /**
     * 회원 비밀번호를 변경한다.
     */
    @PostMapping("/members/{memberId}/password")
    public void changePassword(@PathVariable Long memberId, @RequestBody AuthPasswordChangeRequest request) {
        memberService.changePassword(memberId, request);
    }

    // TODO : 자유 주제로 API를 추가로 구현해보세요.
    @PostMapping("/members/{memberId}/name")
    public void changeName(@PathVariable Long memberId, @RequestBody NewNameRequest request){
        memberService.changeName(memberId, request);
    }
}

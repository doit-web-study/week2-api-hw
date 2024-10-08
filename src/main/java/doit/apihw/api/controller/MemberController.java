package doit.apihw.api.controller;

import doit.apihw.api.controller.dto.AuthPasswordChangeRequest;
import doit.apihw.api.controller.dto.MemberResponse;
import doit.apihw.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // TODO : 자유 주제로 API를 추가로 구현해보세요. => 관리자가 회원 관리하기 위한 memberId 기준으로 정렬된 list
    @PostMapping("/members/loginIdAsc")
    public List<MemberResponse> searchMembersByIdAsc(@RequestParam String memberId) {
        return memberService.AllMembersWithLoginIdDesc(memberId);
    }

}

package doit.apihw.api.service;

import doit.apihw.api.controller.dto.AuthPasswordChangeRequest;
import doit.apihw.api.controller.dto.MemberResponse;
import doit.apihw.domain.member.Member;
import doit.apihw.domain.member.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // memberId에 해당하는 회원 정보를 조회한다.
    public MemberResponse findOneMember(Long memberId) {
        // DB에서 memberId에 해당하는 회원 정보를 조회하고, 존재하지 않는다면 IllegalArgumentException을 발생시킨다.
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 조회된 회원 정보를 MemberResponse로 변환하여 반환한다.
        return MemberResponse.from(member.get());
    }

    // TODO : 전체 회원 정보를 조회한다.
    public List<MemberResponse> findAllMembers() {
        // DB에서 전체 회원 정보를 조회한다.
        List<Member> member = memberRepository.findAll();
        // 조회된 회원 정보를 List<MemberResponse>로 변환하여 반환한다.
        // 얘도 답지 없었으면 못 구현했음...........
        return member.stream()
                .map(MemberResponse::from)
                .toList();
    }

    // TODO : 회원 이름으로 회원 정보를 조회한다.
    public List<MemberResponse> searchMembersWithName(String memberName) {
        // DB에서 memberName에 해당하는 회원 정보를 조회한다.
        List<Member> member = memberRepository.findByMemberName(memberName);
        // 조회된 회원 정보를 List<MemberResponse>로 변환하여 반환한다.
        return member.stream()
                .map(MemberResponse::from)
                .toList();
    }

    // TODO : 회원 비밀번호를 변경한다.
    public void changePassword(Long memberId, AuthPasswordChangeRequest request) {
        // DB에서 memberId에 해당하는 회원 정보를 조회하고, 존재하지 않는다면 IllegalArgumentException을 발생시킨다.
//        Member member = memberRepository.findById(memberId);
//        if (member.isEmpty()) {
//            throw new IllegalArgumentException("존재 안함 ㅠㅠ");
//        }
        // 이렇게 쓰면 되는데 왜 위처럼 쓰면 안되지ㅣ..
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        // 조회된 회원 정보의 비밀번호를 변경한다. ( request.getNewPassword()를 이용하여 변경한다. )
        // 오... 이렇게...
        member.changePassword(request.getOldPassword(), request.getNewPassword());
        // 변경된 회원 정보를 DB에 저장한다.
        memberRepository.save(member);
    }

    public void deleteMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        else{
            memberRepository.deleteById(memberId);
        }
    }
}

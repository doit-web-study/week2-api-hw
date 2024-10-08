package doit.apihw.api.service;

import doit.apihw.api.controller.dto.AuthPasswordChangeRequest;
import doit.apihw.api.controller.dto.MemberResponse;
import doit.apihw.domain.member.Member;
import doit.apihw.domain.member.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
        List<Member> members = memberRepository.findAll();

        // 조회된 회원 정보를 List<MemberResponse>로 변환하여 반환한다.
        List<MemberResponse> memberResponses = new ArrayList<>();

        for (Member member : members) {
            MemberResponse memberResponse = MemberResponse.from(member);
            memberResponses.add(memberResponse);
        }

        return memberResponses;
    }

    // TODO : 회원 이름으로 회원 정보를 조회한다.
    public List<MemberResponse> searchMembersWithName(String memberName) {
        // DB에서 memberName에 해당하는 회원 정보를 조회한다.
        List<Member> members = memberRepository.findAllByMemberName(memberName);

        // 조회된 회원 정보를 List<MemberResponse>로 변환하여 반환한다.
        List<MemberResponse> memberResponses = new ArrayList<>();
        for (Member member : members) {
            MemberResponse memberResponse = MemberResponse.from(member);
            memberResponses.add(memberResponse);
        }

        return memberResponses;
    }

    // TODO : 회원 비밀번호를 변경한다.
    public void changePassword(Long memberId, AuthPasswordChangeRequest request) {
        // DB에서 memberId에 해당하는 회원 정보를 조회하고, 존재하지 않는다면 IllegalArgumentException을 발생시킨다.
        Optional<Member> OptionalMember = memberRepository.findById(memberId);

        if(OptionalMember.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Member member = OptionalMember.get();

        // 조회된 회원 정보의 비밀번호를 변경한다. ( request.getNewPassword()를 이용하여 변경한다. )
        member.changePassword(request.getOldPassword(), request.getNewPassword());

        // 변경된 회원 정보를 DB에 저장한다.
        memberRepository.save(member);
    }

    public List<MemberResponse> findTodayBirthdayMember() {
        LocalDate today = LocalDate.now();
        List<Member> members = memberRepository.findAllByMemberBirthday(today);
        List<MemberResponse> memberResponses = new ArrayList<>();

        for (Member member : members) {
            MemberResponse memberResponse = MemberResponse.from(member);
            memberResponses.add(memberResponse);
        }

        return memberResponses;

    }

}

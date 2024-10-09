package doit.apihw.api.controller.dto;

import doit.apihw.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    private Long id;
    private String memberName;

    public static AuthLoginResponse from(Member member) {
        return AuthLoginResponse.builder()
                .id(member.getId())
                .memberName(member.getMemberName())
                .build();
    }
}
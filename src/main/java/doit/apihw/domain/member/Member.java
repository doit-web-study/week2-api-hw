package doit.apihw.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;
    private String memberName;
    private LocalDate memberBirthday;
    private String memberLoginId;
    private String memberPassword;

    @Builder
    private Member(String memberName, LocalDate memberBirthday, String memberLoginId, String memberPassword) {
        this.memberName = memberName;
        this.memberBirthday = memberBirthday;
        this.memberLoginId = memberLoginId;
        this.memberPassword = memberPassword;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!memberPassword.equals(oldPassword)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        memberPassword = newPassword;
    }

    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
        this.memberName = newName;
    }
}

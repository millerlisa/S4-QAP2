package com.keyin.membership;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findByMemberName_ShouldReturnMember() {
        // Create test member
        Member member = new Member();
        member.setMemberName("John Doe");
        member.setMemberEmail("john@example.com");
        member.setMemberPhone("555-1234");
        member.setStartDate(LocalDate.of(2024, 1, 1));
        entityManager.persist(member);
        entityManager.flush();

        // Test search
        List<Member> found = memberRepository.findByMemberName("john");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getMemberName()).isEqualTo("John Doe");
    }

    @Test
    public void findByMemberPhone_ShouldReturnMember() {
        Member member = new Member();
        member.setMemberName("John Doe");
        member.setMemberEmail("john@example.com");
        member.setMemberPhone("555-1234");
        member.setStartDate(LocalDate.of(2024, 1, 1));
        entityManager.persist(member);
        entityManager.flush();

        List<Member> found = memberRepository.findByMemberPhone("555-1234");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getMemberPhone()).isEqualTo("555-1234");
    }

    @Test
    public void findByStartDate_ShouldReturnMember() {
        LocalDate testDate = LocalDate.of(2024, 1, 1);

        Member member = new Member();
        member.setMemberName("John Doe");
        member.setMemberEmail("john@example.com");
        member.setMemberPhone("555-1234");
        member.setStartDate(testDate);
        entityManager.persist(member);
        entityManager.flush();

        List<Member> found = memberRepository.findByStartDate(testDate);

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getStartDate()).isEqualTo(testDate);
    }

    @Test
    public void findByMemberName_ShouldReturnEmpty() {
        List<Member> found = memberRepository.findByMemberName("NonExistent");
        assertThat(found).isEmpty();
    }

    @Test
    public void findByMemberNameContaining_ShouldBeCaseInsensitive() {
        Member member = new Member();
        member.setMemberName("John Doe");
        member.setMemberEmail("john@example.com");
        member.setStartDate(LocalDate.of(2024, 1, 1));
        entityManager.persist(member);
        entityManager.flush();

        List<Member> foundLower = memberRepository.findByMemberName("john");
        List<Member> foundUpper = memberRepository.findByMemberName("JOHN");
        List<Member> foundMixed = memberRepository.findByMemberName("JoHn");

        assertThat(foundLower).hasSize(1);
        assertThat(foundUpper).hasSize(1);
        assertThat(foundMixed).hasSize(1);
    }
}
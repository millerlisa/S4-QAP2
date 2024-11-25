package com.keyin.membership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void getAllMembers_ShouldReturnAllMembers() {
        Member member1 = new Member();
        member1.setId(1L);
        member1.setMemberName("John Doe");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setMemberName("Jane Doe");

        when(memberRepository.findAll()).thenReturn(Arrays.asList(member1, member2));

        List<Member> members = memberService.getAllMembers();

        assertEquals(2, members.size());
        assertEquals("John Doe", members.get(0).getMemberName());
        assertEquals("Jane Doe", members.get(1).getMemberName());
        verify(memberRepository).findAll();
    }

    @Test
    public void getMemberById_ShouldReturnMember() {
        Member member = new Member();
        member.setId(1L);
        member.setMemberName("John Doe");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Optional<Member> result = memberService.getMemberById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getMemberName());
        verify(memberRepository).findById(1L);
    }

    @Test
    public void saveMember_ShouldReturnSavedMember() {
        Member memberToSave = new Member();
        memberToSave.setMemberName("John Doe");
        memberToSave.setMemberEmail("john@example.com");
        memberToSave.setStartDate(LocalDate.now());

        when(memberRepository.save(any(Member.class))).thenReturn(memberToSave);

        Member savedMember = memberService.saveMember(memberToSave);

        assertNotNull(savedMember);
        assertEquals("John Doe", savedMember.getMemberName());
        verify(memberRepository).save(memberToSave);
    }

    @Test
    public void deleteMember_ShouldDeleteMember() {
        doNothing().when(memberRepository).deleteById(1L);

        memberService.deleteMember(1L);

        verify(memberRepository).deleteById(1L);
    }

    @Test
    public void searchByName_ShouldReturnMatchingMembers() {
        Member member1 = new Member();
        member1.setMemberName("John Doe");
        Member member2 = new Member();
        member2.setMemberName("Betty Smith");

        when(memberRepository.findByMemberName("John"))
                .thenReturn(Arrays.asList(member1, member2));

        List<Member> results = memberService.searchByName("John");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(m -> m.getMemberName().equals("John Doe")));
        assertTrue(results.stream().anyMatch(m -> m.getMemberName().equals("Betty Smith")));
        verify(memberRepository).findByMemberName("John");
    }

    @Test
    public void searchByStartDate_ShouldReturnMatchingMembers() {
        LocalDate searchDate = LocalDate.of(2024, 1, 1);
        Member member = new Member();
        member.setMemberName("John Doe");
        member.setStartDate(searchDate);

        when(memberRepository.findByStartDate(searchDate))
                .thenReturn(Arrays.asList(member));

        List<Member> results = memberService.searchByStartDate(searchDate);

        assertEquals(1, results.size());
        assertEquals(searchDate, results.get(0).getStartDate());
        verify(memberRepository).findByStartDate(searchDate);
    }
}


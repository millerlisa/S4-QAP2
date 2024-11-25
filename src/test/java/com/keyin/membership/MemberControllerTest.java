package com.keyin.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getMember_ShouldReturnMember() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setMemberName("John Doe");
        member.setMemberEmail("john@example.com");
        member.setStartDate(LocalDate.of(2024, 1, 1));

        when(memberService.getMemberById(1L)).thenReturn(Optional.of(member));

        mockMvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberName").value("John Doe"))
                .andExpect(jsonPath("$.memberEmail").value("john@example.com"));
    }

    @Test
    public void createMember_ShouldReturnNewMember() throws Exception {
        Member newMember = new Member();
        newMember.setMemberName("Jane Doe");
        newMember.setMemberEmail("jane@example.com");
        newMember.setStartDate(LocalDate.of(2024, 1, 1));

        Member savedMember = new Member();
        savedMember.setId(1L);
        savedMember.setMemberName("Jane Doe");
        savedMember.setMemberEmail("jane@example.com");
        savedMember.setStartDate(LocalDate.of(2024, 1, 1));

        when(memberService.saveMember(any(Member.class))).thenReturn(savedMember);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberName").value("Jane Doe"))
                .andExpect(jsonPath("$.memberEmail").value("jane@example.com"));
    }

    @Test
    public void searchByName_ShouldReturnMatchingMembers() throws Exception {
        Member member1 = new Member();
        member1.setId(1L);
        member1.setMemberName("John Doe");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setMemberName("Betty Smith");

        when(memberService.searchByName("John")).thenReturn(Arrays.asList(member1, member2));

        mockMvc.perform(get("/members/search/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("John Doe"))
                .andExpect(jsonPath("$[1].memberName").value("Betty Smith"));
    }

    @Test
    public void deleteMember_ShouldReturn200WhenSuccessful() throws Exception {
        Member member = new Member();
        member.setId(1L);

        when(memberService.getMemberById(1L)).thenReturn(Optional.of(member));

        mockMvc.perform(delete("/members/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMember_ShouldReturn404WhenNotFound() throws Exception {
        when(memberService.getMemberById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/members/1"))
                .andExpect(status().isNotFound());
    }
}

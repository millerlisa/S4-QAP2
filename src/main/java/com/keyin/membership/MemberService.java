package com.keyin.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    // Search methods
    public List<Member> searchByName(String name) {
        return memberRepository.findByMemberName(name);
    }

    public List<Member> searchByPhone(String phone) {
        return memberRepository.findByMemberPhone(phone);
    }

    public List<Member> searchByStartDate(LocalDate startDate) {
        return memberRepository.findByStartDate(startDate);
    }
    public List<Member> searchByMembershipDuration(Integer duration) {
        return memberRepository.findByMembershipDuration(duration);
    }
}

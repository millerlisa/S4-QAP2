package com.keyin.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMemberName(String name);
    List<Member> findByMemberPhone(String phone);
    List<Member> findByStartDate(LocalDate startDate);
    List<Member> findByMembershipDuration(Integer duration);
}
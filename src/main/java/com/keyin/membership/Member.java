package com.keyin.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.tournament.Tournament;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence")
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    private Long id;

    private String memberName;
    private String memberAddress;
    private String memberEmail;
    private String memberPhone;
    private LocalDate startDate;
    private Integer membershipDuration; // in months

    @JsonIgnore
    @ManyToMany(mappedBy = "participatingMembers")
    private Set<Tournament> tournaments = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getMembershipDuration() {
        return membershipDuration;
    }

    public void setMembershipDuration(Integer membershipDuration) {
        this.membershipDuration = membershipDuration;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
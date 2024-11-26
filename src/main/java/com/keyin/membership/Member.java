package com.keyin.membership;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_sequence")
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    private Long id;

    private String memberName;
    private String address;
    private String memberEmail;
    private String memberPhone;
    private LocalDate startDate;
    private Integer duration; // in months

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

    public String getAddress() {return address; }

   public void setAddress(String address) {this.address = address;}

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

    public Integer getDuration() { return duration; }

    public void setDuration(Integer membershipDuration) {this.duration = membershipDuration;}

}
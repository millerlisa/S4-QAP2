package com.keyin.tournament;

import com.keyin.membership.Member;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_sequence")
    @SequenceGenerator(name = "tournament_sequence", sequenceName = "tournament_sequence", allocationSize = 1)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private Double entryFee;
    private Double cashPrizeAmount;

//    @ManyToMany
//    @JoinTable(
//            name = "tournament_members",
//            joinColumns = @JoinColumn(name = "tournament_id"),
//            inverseJoinColumns = @JoinColumn(name = "member_id")
//    )

    @OneToMany
    private Set<Member> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public Double getCashPrizeAmount() {
        return cashPrizeAmount;
    }

    public void setCashPrizeAmount(Double cashPrizeAmount) {
        this.cashPrizeAmount = cashPrizeAmount;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {this.members = members; }
}

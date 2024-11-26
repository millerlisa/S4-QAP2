package com.keyin.tournament;

import com.keyin.membership.Member;
import com.keyin.membership.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    public Tournament addTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    public List<Tournament> searchByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    }

    public List<Tournament> searchByLocation(String location) {
        return tournamentRepository.findByLocation(location);
    }

    public Tournament addMemberToTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Set<Member> memberList =  tournament.getMembers();

        memberList.add(member);

        tournament.setMembers(memberList);

        return tournamentRepository.save(tournament);
    }

    public Tournament removeMemberFromTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        tournament.getMembers().removeIf(member -> member.getId().equals(memberId));
        return tournamentRepository.save(tournament);
    }
}

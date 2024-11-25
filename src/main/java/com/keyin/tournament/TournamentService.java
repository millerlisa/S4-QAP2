package com.keyin.tournament;

import com.keyin.membership.Member;
import com.keyin.membership.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    // Search methods
    public List<Tournament> searchByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    }

    public List<Tournament> searchByLocation(String location) {
        return tournamentRepository.findByLocation(location);
    }

    public List<Tournament> searchByDateRange(LocalDate start, LocalDate end) {
        return tournamentRepository.findByDates(start, end);
    }


    // Member management
    public Tournament addMemberToTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        tournament.getParticipatingMembers().add(member);
        return tournamentRepository.save(tournament);
    }

    public Tournament removeMemberFromTournament(Long tournamentId, Long memberId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        tournament.getParticipatingMembers().removeIf(member -> member.getId().equals(memberId));
        return tournamentRepository.save(tournament);
    }
}

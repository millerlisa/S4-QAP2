package com.keyin.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentService.addTournament(tournament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        return tournamentService.getTournamentById(id)
                .map(existingTournament -> {
                    tournament.setId(id);
                    return ResponseEntity.ok(tournamentService.addTournament(tournament));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        if (tournamentService.getTournamentById(id).isPresent()) {
            tournamentService.deleteTournament(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search/location/{location}")
    public List<Tournament> searchByLocation(@PathVariable String location) {
        return tournamentService.searchByLocation(location);
    }

    @GetMapping("/search/date")
    public List<Tournament> searchByStartDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return tournamentService.searchByStartDate(startDate);
    }

    @PostMapping("/{tournamentId}/members/{memberId}")
    public Tournament addMemberToTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId) {
        return tournamentService.addMemberToTournament(tournamentId, memberId);
    }

    @DeleteMapping("/{tournamentId}/members/{memberId}")
    public Tournament removeMemberFromTournament(
            @PathVariable Long tournamentId,
            @PathVariable Long memberId) {
        return tournamentService.removeMemberFromTournament(tournamentId, memberId);
    }
}

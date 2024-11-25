package com.keyin.tournament;

import com.keyin.membership.*;
import com.keyin.tournament.*;
import org.junit.jupiter.api.BeforeEach;
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
public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TournamentService tournamentService;

    private Tournament testTournament1;
    private Tournament testTournament2;
    private Member testMember;

    @BeforeEach
    void setUp() {
        testTournament1 = new Tournament();
        testTournament1.setId(1L);
        testTournament1.setLocation("Clovelly");
        testTournament1.setStartDate(LocalDate.of(2024, 6, 15));
        testTournament1.setEndDate(LocalDate.of(2024, 6, 16));
        testTournament1.setEntryFee(250.0);
        testTournament1.setCashPrizeAmount(5000.0);

        testTournament2 = new Tournament();
        testTournament2.setId(2L);
        testTournament2.setLocation("Bally Haly");
        testTournament2.setStartDate(LocalDate.of(2024, 8, 15));
        testTournament2.setEndDate(LocalDate.of(2024, 8, 16));
        testTournament2.setEntryFee(200.0);
        testTournament2.setCashPrizeAmount(2000.0);

        testMember = new Member();
        testMember.setId(1L);
        testMember.setMemberName("John Doe");
    }

    @Test
    void getAllTournaments_ShouldReturnAllTournaments() {
        when(tournamentRepository.findAll()).thenReturn(Arrays.asList(testTournament1, testTournament2));

        List<Tournament> tournaments = tournamentService.getAllTournaments();

        assertEquals(2, tournaments.size());
        assertEquals("Clovelly", tournaments.get(0).getLocation());
        assertEquals("Bally Haly", tournaments.get(1).getLocation());
        verify(tournamentRepository).findAll();
    }

    @Test
    void getTournamentById_ShouldReturnTournament() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(testTournament1));

        Optional<Tournament> result = tournamentService.getTournamentById(1L);

        assertTrue(result.isPresent());
        assertEquals("Clovelly", result.get().getLocation());
        verify(tournamentRepository).findById(1L);
    }

    @Test
    void saveTournament_ShouldReturnSavedTournament() {
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(testTournament1);

        Tournament savedTournament = tournamentService.saveTournament(testTournament1);

        assertNotNull(savedTournament);
        assertEquals("Clovelly", savedTournament.getLocation());
        verify(tournamentRepository).save(testTournament1);
    }


    @Test
    void searchByDateRange_ShouldReturnMatchingTournaments() {
        LocalDate start = LocalDate.of(2024, 6, 1);
        LocalDate end = LocalDate.of(2024, 6, 30);

        when(tournamentRepository.findByDates(start, end))
                .thenReturn(Arrays.asList(testTournament1));

        List<Tournament> results = tournamentService.searchByDateRange(start, end);

        assertEquals(1, results.size());
        assertEquals(LocalDate.of(2024, 6, 15), results.get(0).getStartDate());
        verify(tournamentRepository).findByDates(start, end);
    }

    @Test
    void addMemberToTournament_ShouldAddMemberSuccessfully() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(testTournament1));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(testTournament1);

        Tournament result = tournamentService.addMemberToTournament(1L, 1L);

        assertNotNull(result);
        verify(tournamentRepository).save(any(Tournament.class));
    }

    @Test
    void removeMemberFromTournament_ShouldRemoveMemberSuccessfully() {
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(testTournament1));
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(testTournament1);

        Tournament result = tournamentService.removeMemberFromTournament(1L, 1L);

        assertNotNull(result);
        verify(tournamentRepository).save(any(Tournament.class));
    }

    @Test
    void searchByStartDate_ShouldReturnMatchingTournaments() {
        LocalDate searchDate = LocalDate.of(2024, 1, 15);
        when(tournamentRepository.findByStartDate(searchDate))
                .thenReturn(Arrays.asList(testTournament1));

        List<Tournament> results = tournamentService.searchByStartDate(searchDate);

        assertEquals(1, results.size());
        assertEquals(searchDate, results.get(0).getStartDate());
        verify(tournamentRepository).findByStartDate(searchDate);
    }
}

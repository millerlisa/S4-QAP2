package com.keyin.tournament;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TournamentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void findByLocation_ShouldReturnTournament() {
        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(LocalDate.of(2024, 6, 15));
        tournament.setEndDate(LocalDate.of(2024, 6, 16));
        tournament.setEntryFee(250.0);
        tournament.setCashPrizeAmount(5000.0);
        entityManager.persist(tournament);
        entityManager.flush();

        List<Tournament> found = tournamentRepository.findByLocation("Clovelly");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getLocation()).isEqualTo("Clovelly");
    }

    @Test
    public void findByStartDate_ShouldReturnTournament() {
        LocalDate testDate = LocalDate.of(2024, 6, 15);

        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(testDate);
        tournament.setEndDate(LocalDate.of(2024, 6, 16));
        entityManager.persist(tournament);
        entityManager.flush();

        List<Tournament> found = tournamentRepository.findByStartDate(testDate);

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getStartDate()).isEqualTo(testDate);
    }

    @Test
    public void findByStartDateBetween_ShouldReturnTournament() {
        LocalDate startDate = LocalDate.of(2024, 6, 15);
        LocalDate endDate = LocalDate.of(2024, 6, 16);

        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        entityManager.persist(tournament);
        entityManager.flush();

        List<Tournament> found = tournamentRepository.findByDates(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
        );

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getStartDate()).isEqualTo(startDate);
    }

    @Test
    public void findByStartDateBetween_ShouldReturnEmpty_WhenOutsideRange() {
        LocalDate startDate = LocalDate.of(2024, 6, 15);
        LocalDate endDate = LocalDate.of(2024, 6, 16);

        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        entityManager.persist(tournament);
        entityManager.flush();

        List<Tournament> found = tournamentRepository.findByDates(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
        );

        assertThat(found).isEmpty();
    }
}


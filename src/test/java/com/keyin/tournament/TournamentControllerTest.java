package com.keyin.tournament;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TournamentController.class)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getTournament_ShouldReturnTournament() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setLocation("Clovelly");
        tournament.setStartDate(LocalDate.of(2024, 1, 15));
        tournament.setEndDate(LocalDate.of(2024, 1, 16));

        when(tournamentService.getTournamentById(1L)).thenReturn(Optional.of(tournament));

        mockMvc.perform(get("/tournaments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.location").value("Clovelly"));
    }

    @Test
    public void createTournament_ShouldReturnNewTournament() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setLocation("Pine Valley");
        tournament.setStartDate(LocalDate.of(2024, 1, 15));
        tournament.setEndDate(LocalDate.of(2024, 1, 16));
        tournament.setEntryFee(150.0);
        tournament.setCashPrizeAmount(1000.0);

        when(tournamentService.saveTournament(any(Tournament.class))).thenReturn(tournament);

        mockMvc.perform(post("/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tournament)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Clovelly"));
    }

    @Test
    public void searchByDate_ShouldReturnMatchingTournaments() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(LocalDate.of(2024, 6, 15));

        when(tournamentService.searchByStartDate(any(LocalDate.class)))
                .thenReturn(Arrays.asList(tournament));

        mockMvc.perform(get("/tournaments/search/date")
                        .param("startDate", "2024-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Clovelly"));
    }

    @Test
    public void searchByDateRange_ShouldReturnMatchingTournaments() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setLocation("Clovelly");
        tournament.setStartDate(LocalDate.of(2024, 6, 15));

        when(tournamentService.searchByDateRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(tournament));

        mockMvc.perform(get("/tournaments/search/dateRange")
                        .param("start", "2024-06-01")
                        .param("end", "2024-06-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Clovelly"));
    }

    @Test
    public void addMemberToTournament_ShouldReturnUpdatedTournament() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setLocation("Clovelly");

        when(tournamentService.addMemberToTournament(1L, 1L)).thenReturn(tournament);

        mockMvc.perform(post("/tournaments/1/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Clovelly"));
    }

    @Test
    public void removeMemberFromTournament_ShouldReturnUpdatedTournament() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        tournament.setLocation("Clovelly");

        when(tournamentService.removeMemberFromTournament(1L, 1L)).thenReturn(tournament);

        mockMvc.perform(delete("/tournaments/1/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Clovelly"));
    }

    @Test
    public void getTournament_ShouldReturn404WhenNotFound() throws Exception {
        when(tournamentService.getTournamentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tournaments/1"))
                .andExpect(status().isNotFound());
    }
}


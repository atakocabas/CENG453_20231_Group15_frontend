package com.catan.catanui.service;

import com.catan.catanui.config.TokenStore;
import com.catan.catanui.entity.LeaderboardEntry;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LeaderboardService {

    public List<LeaderboardEntry> getWeeklyScores() {
        String weeklyApiUrl = "http://localhost:8080/api/v1/leaderboard/weekly";
        return getLeaderboardEntries(weeklyApiUrl);
    }

    public List<LeaderboardEntry> getMonthlyScores(){
        String monthlyApiUrl = "http://localhost:8080/api/v1/leaderboard/monthly";
        return getLeaderboardEntries(monthlyApiUrl);
    }

    public List<LeaderboardEntry> getAllTimeScores(){
        String allTimeApiUrl = "http://localhost:8080/api/v1/leaderboard/all-time";
        return getLeaderboardEntries(allTimeApiUrl);
    }

    private List<LeaderboardEntry> getLeaderboardEntries(String apiUrl){
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + TokenStore.getInstance().getToken());

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<List<LinkedHashMap>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<LinkedHashMap>>() {}
            );

            List<LinkedHashMap> entries = response.getBody();

            if(entries == null) throw new IllegalStateException("No entries found");

            for (LinkedHashMap map : entries) {
                LeaderboardEntry entry = new LeaderboardEntry(
                        (String) map.get("username"),
                        ((Number) map.get("score")).longValue()
                );
                leaderboardEntries.add(entry);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the leaderboard scores: " + e.getMessage());
        }
        return leaderboardEntries;
    }
}
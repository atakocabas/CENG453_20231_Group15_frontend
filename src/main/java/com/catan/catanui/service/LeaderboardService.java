package com.catan.catanui.service;

import com.catan.catanui.config.TokenStore;
import com.catan.catanui.entity.LeaderboardEntry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a service for retrieving and adding leaderboard entries.
 */
public class LeaderboardService {
    @Value("${catan.api.url}")
    private String API_URL;

    public List<LeaderboardEntry> getWeeklyScores() {
        String weeklyApiUrl = API_URL + "/leaderboard/weekly";
        return getLeaderboardEntries(weeklyApiUrl);
    }

    public List<LeaderboardEntry> getMonthlyScores(){
        String monthlyApiUrl = API_URL + "/leaderboard/monthly";
        return getLeaderboardEntries(monthlyApiUrl);
    }

    public List<LeaderboardEntry> getAllTimeScores(){
        String allTimeApiUrl = API_URL + "/leaderboard/all-time";
        return getLeaderboardEntries(allTimeApiUrl);
    }

    /**
     * Retrieves the leaderboard entries from the specified API URL.
     *
     * @param apiUrl the URL of the API endpoint to fetch the leaderboard entries from
     * @return a list of leaderboard entries
     */
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

    /**
     * Adds a leaderboard entry for the given username and score.
     * 
     * @param username the username of the player
     * @param score the score achieved by the player
     * @return true if the leaderboard entry was added successfully, false otherwise
     */
    public boolean addLeaderboardEntry(String username, int score){
        String leaderboardAddApiUrl = API_URL + "/leaderboard/add";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + TokenStore.getInstance().getToken());

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("score", String.valueOf(score));

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try{
            ResponseEntity<List<LinkedHashMap>> response = restTemplate.exchange(
                    leaderboardAddApiUrl,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<LinkedHashMap>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()){
                return true;
            }
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
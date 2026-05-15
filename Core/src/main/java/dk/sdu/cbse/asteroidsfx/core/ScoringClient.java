package dk.sdu.cbse.asteroidsfx.core;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ScoringClient {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/score";

    public ScoringClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getScore() {
        try {
            return restTemplate.getForObject(baseUrl, Integer.class);
        } catch (Exception e) {
            System.err.println("Failed to fetch score from MicroService: " + e.getMessage());
            return 0;
        }
    }

    public void addScore(int points) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/add")
                    .queryParam("points", points)
                    .toUriString();
            restTemplate.postForObject(url, null, Integer.class);
        } catch (Exception e) {
            System.err.println("Failed to add score to MicroService: " + e.getMessage());
        }
    }

    public void resetScore() {
        try {
            restTemplate.postForObject(baseUrl + "/reset", null, Void.class);
        } catch (Exception e) {
            System.err.println("Failed to reset score in MicroService: " + e.getMessage());
        }
    }
}

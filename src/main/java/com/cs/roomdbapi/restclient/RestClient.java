package com.cs.roomdbapi.restclient;

import com.cs.roomdbapi.dto.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Data
@AllArgsConstructor
@Slf4j
@Component
public class RestClient {

    public boolean postNotification(String url, Notification req) {
        log.info("URL: {}", url);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        try {
            HttpEntity<Notification> request = new HttpEntity<>(req, headers);
            ResponseEntity<String> resEn = restTemplate.postForEntity(url, request, String.class);

            log.info("Request: " + req +
                    "Response Status: {}" + resEn.getStatusCodeValue() +
                    "\n Response Body: " + resEn.getBody());

            // Went successful or not
            return resEn.getStatusCodeValue() == HttpStatus.OK.value() || resEn.getStatusCodeValue() == HttpStatus.CREATED.value();
        } catch (Exception e) {
            log.info("Failed to sent Notification request: " + req);
            e.printStackTrace();
        }
        return false;
    }

}

package com.simple.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ExternalDogAPIService implements ExternalDogProvider {
    @Value("${aws.accessKeyId}")
    private String externalAPIURL;
    final String URI = "https://dog.ceo/api/breeds/image/random";

    public ExternalDogAPIResponse GetDogDetails() {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(URI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
                ExternalDogAPIResponse ob = new ObjectMapper().readValue(sb.toString(), ExternalDogAPIResponse.class);
                if (ob.getStatus().equalsIgnoreCase("success") && ob.getMessage() != null && ob.getMessage().contains("/")) {
                    String[] arr = ob.getMessage().split("/");
                    ob.setFileName(arr[arr.length - 1]);
                    ob.setDogName(arr[arr.length - 2]);
                }
                return ob;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

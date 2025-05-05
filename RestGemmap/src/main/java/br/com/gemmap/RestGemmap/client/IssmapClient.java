package br.com.gemmap.RestGemmap.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class IssmapClient {

    private final RestTemplate restTemplate;
    private final String issmapUrl;

    public IssmapClient(RestTemplate restTemplate, @Value("${issmap.api.url}") String issmapUrl) {
        this.restTemplate = restTemplate;
        this.issmapUrl = issmapUrl;
    }

    public boolean isConfigOnline() {
        return !issmapUrl.contains("localhost");
    }

    public String buscarAlgumaInfo(String chaveCidade) {
        // Exemplo com query param usando a chave
        String url = issmapUrl + "/algum-endpoint?chave=" + chaveCidade;
        return restTemplate.getForObject(url, String.class);
    }
}

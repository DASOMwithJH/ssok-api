package DasomwithJH.ssok.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiConfig {

    @Bean
    public RestClient openAiRestClient(OpenAiProperties props) {
        return RestClient.builder()
                .baseUrl(props.getApiUrl())
                .defaultHeader("Authorization", "Bearer " + props.getApiKey())
                .build();
    }
}

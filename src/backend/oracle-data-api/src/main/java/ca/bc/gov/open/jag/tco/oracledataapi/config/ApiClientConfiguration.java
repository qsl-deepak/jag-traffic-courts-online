package ca.bc.gov.open.jag.tco.oracledataapi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.bc.gov.open.jag.tco.oracledataapi.api.handler.ApiClient;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ApiClientConfiguration {
	
	@Bean({"ordsApiClient"})
    @ConditionalOnMissingBean(ApiClient.class)
    public ApiClient apiClient(ConfigProperties configProperties)  {
        ApiClient apiClient = new ApiClient();
        //Setting this to null will make it use the baseUrl instead
        apiClient.setServerIndex(null);
        apiClient.setBasePath(configProperties.getOrdsRestApiUrl());
        return apiClient;
    }
}

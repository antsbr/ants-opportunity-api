package org.ants.opportunity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;

@Configuration
public class GeoJsonConfiguration extends SpringDataJacksonConfiguration {
    @Bean
    public GeoJsonModule geoJsonModule() {
        return new GeoJsonModule();
    }
}

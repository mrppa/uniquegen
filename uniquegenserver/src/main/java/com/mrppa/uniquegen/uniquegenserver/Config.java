package com.mrppa.uniquegen.uniquegenserver;

import com.mrppa.uniquegen.IDGenProvidersRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public IDGenProvidersRegistry idGenProvidersRegistry() {
        return new IDGenProvidersRegistry();
    }
}

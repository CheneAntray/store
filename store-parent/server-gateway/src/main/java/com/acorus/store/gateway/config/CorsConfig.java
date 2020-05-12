package com.acorus.store.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author CheneAntray
 * @create 2020-05-12 18:22
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(){

        // cors跨域配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        // 配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);
        // cors过滤器对象
        return new CorsWebFilter(configurationSource);
    }
}

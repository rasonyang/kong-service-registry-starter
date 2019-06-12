/*
 * Copyright 2017-2019 Pamarin.com
 */
package com.pamarin.kong.service.registry;

import com.pamarin.kong.service.registry.service.KongServiceRegistry;
import com.pamarin.kong.service.registry.service.KongServiceRegistryImpl;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jitta
 */
@Slf4j
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
@EnableConfigurationProperties({KongServiceRegistryProperties.class})
public class KongServiceRegistryAutoConfiguration {

    private final KongServiceRegistryProperties properties;

    public KongServiceRegistryAutoConfiguration(KongServiceRegistryProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean({KongServiceRegistry.class})
    public KongServiceRegistry kongServiceRegistry() {
        return new KongServiceRegistryImpl(properties);
    }

    @PostConstruct
    public void start() {
        boolean enabled = properties.getServiceRegistry().getEnabled();
        log.debug("kong.adminUrl => {}", properties.getAdminUrl());
        log.debug("kong.serviceRegistry.name => {}", properties.getServiceRegistry().getName());
        log.debug("kong.serviceRegistry.url => {}", properties.getServiceRegistry().getUrl());
        log.debug("kong.serviceRegistry.routePaths => {}", properties.getServiceRegistry().getRoutePaths());
        log.debug("kong.serviceRegistry.enabled => {}", enabled);
        if (enabled) {
            kongServiceRegistry().register();
        }
    }
}

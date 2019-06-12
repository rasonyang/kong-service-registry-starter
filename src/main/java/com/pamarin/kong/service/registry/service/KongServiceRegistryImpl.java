/*
 * Copyright 2017-2019 Pamarin.com
 */
package com.pamarin.kong.service.registry.service;

import com.pamarin.kong.service.registry.KongServiceRegistryProperties;
import com.pamarin.kong.service.registry.exception.KongServiceRegistryException;
import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author jitta
 */
@Slf4j
public class KongServiceRegistryImpl implements KongServiceRegistry {

    private final KongServiceRegistryProperties properties;

    private final RestTemplate restTemplate;

    public KongServiceRegistryImpl(KongServiceRegistryProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void register() {
        log.debug("register service \"{}\" to kong {}...", properties.getServiceRegistry().getName(), properties.getAdminUrl());
        deleteRoutes();
        deleteService();
        addService();
        addRoutes();
    }

    private void deleteRoutes() {
        try {
            restTemplate.delete(format(
                    "%s/routes/%s",
                    properties.getAdminUrl(),
                    properties.getServiceRegistry().getName()
            ));
        } catch (RestClientException ex) {
            throw new KongServiceRegistryException("delete rotues error", ex);
        }
    }

    private void deleteService() {
        try {
            restTemplate.delete(format(
                    "%s/services/%s",
                    properties.getAdminUrl(),
                    properties.getServiceRegistry().getName()
            ));
        } catch (RestClientException ex) {
            throw new KongServiceRegistryException("delete service error", ex);
        }
    }

    private void addService() {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("name", properties.getServiceRegistry().getName());
            requestBody.put("url", properties.getServiceRegistry().getUrl());
            restTemplate.postForObject(
                    format("%s/services", properties.getAdminUrl()),
                    requestBody,
                    Object.class
            );
        } catch (RestClientException ex) {
            throw new KongServiceRegistryException("delete service error", ex);
        }
    }

    private void addRoutes() {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("name", properties.getServiceRegistry().getName());
            requestBody.put("paths", properties.getServiceRegistry().getRoutePaths());
            restTemplate.postForObject(
                    format("%s/services/%s/routes", properties.getAdminUrl(), properties.getServiceRegistry().getName()),
                    requestBody,
                    Object.class
            );
        } catch (RestClientException ex) {
            throw new KongServiceRegistryException("delete service error", ex);
        }
    }
}

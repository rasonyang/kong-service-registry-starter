/*
 * Copyright 2017-2019 Pamarin.com
 */
package com.pamarin.kong.service.registry.exception;

/**
 *
 * @author jitta
 */
public class KongServiceRegistryException extends RuntimeException {

    public KongServiceRegistryException(String message) {
        super(message);
    }

    public KongServiceRegistryException(String message, Throwable cause) {
        super(message, cause);
    }

}

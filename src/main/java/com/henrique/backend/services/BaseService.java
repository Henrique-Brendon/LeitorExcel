package com.henrique.backend.services;

import java.util.function.Supplier;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;

public abstract class BaseService {

    protected <T> T execute(Supplier<T> action, String errorMessage) {
        try {
            return action.get(); // Executa o método específico
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Invalid argument: " + errorMessage, e);
        } catch (DataAccessException e) {
            throw new ServiceException("Database error: " + errorMessage, e);
        } catch (Exception e) {
            throw new ServiceException("Unexpected error: " + errorMessage, e);
        }
    }

    protected void execute(Runnable action, String errorMessage) {
        try {
            action.run(); // Executa a ação específica
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Invalid argument: " + errorMessage, e);
        } catch (DataAccessException e) {
            throw new ServiceException("Database error: " + errorMessage, e);
        } catch (Exception e) {
            throw new ServiceException("Unexpected error: " + errorMessage, e);
        }
    }
}
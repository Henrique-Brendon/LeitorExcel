package com.henrique.backend.services;

import com.henrique.backend.services.execeptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.function.Supplier;

public abstract class BaseService {

    protected <T> T execute(Supplier<T> action, String errorMessage) {
        try {
            return action.get(); // Executa o método específico
        } catch (IllegalArgumentException e) {
            throw new ServiceException(errorMessage + " - Invalid argument", e);
        } catch (DataAccessException e) {
            throw new ServiceException(errorMessage + " - Database error", e);
        } catch (Exception e) {
            throw new ServiceException(errorMessage + " - Unexpected error", e);
        }
    }

    protected void execute(Runnable action, String errorMessage) {
        try {
            action.run(); // Executa a ação específica
        } catch (IllegalArgumentException e) {
            throw new ServiceException(errorMessage + " - Invalid argument", e);
        } catch (DataAccessException e) {
            throw new ServiceException(errorMessage + " - Database error", e);
        } catch (Exception e) {
            throw new ServiceException(errorMessage + " - Unexpected error", e);
        }
    }
}

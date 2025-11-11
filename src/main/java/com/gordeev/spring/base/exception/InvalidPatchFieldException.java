package com.gordeev.spring.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPatchFieldException extends RuntimeException {
    public InvalidPatchFieldException(String field) {
        super("Поле '" + field + "' не поддерживается в PATCH-запросе. Разрешены: title, description");
    }
}

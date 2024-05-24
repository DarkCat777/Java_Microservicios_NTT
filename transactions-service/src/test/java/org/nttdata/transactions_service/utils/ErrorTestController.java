package org.nttdata.transactions_service.utils;

import org.nttdata.transactions_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.transactions_service.domain.exception.NotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class ErrorTestController {

    @GetMapping("/method-argument-not-valid")
    public void methodArgumentNotValid() throws BindException {
        BindException exception = new BindException(new Object(), "testObject");
        exception.addError(new ObjectError("testObject", "Validation error"));
        throw exception;
    }

    @GetMapping("/web-exchange-bind")
    public void webExchangeBind() throws BindException {
        BindException exception = new BindException(new Object(), "testObject");
        exception.addError(new ObjectError("testObject", "Validation error"));
        throw exception;
    }

    @GetMapping("/business-logic")
    public void businessLogic() {
        throw new BusinessLogicValidationException("Business logic error", new HashMap<>());
    }

    @GetMapping("/not-found")
    public void notFound() {
        throw new NotFoundException("Resource not found");
    }

    @GetMapping("/server-error")
    public void serverError() {
        throw new RuntimeException("Internal server error");
    }
}
package pl.bdygasinski.gameoflife.rest.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

@RestControllerAdvice
class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldValidationError> fieldErrors = getFieldValidationErrors(ex);
        logger.warn("Validation failed on request to {}: {}", request.getRequestURI(), fieldErrors);

        return new ValidationErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorMessage.VALIDATION_FAILED,
                request.getRequestURI(),
                fieldErrors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        logger.warn("Malformed JSON request", ex);

        return new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorMessage.MALFORMED_JSON,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(exception = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse unexpectedExceptionHandler(Exception e, HttpServletRequest request) {
        logger.error("Unexpected exception thrown: ", e);

        return new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorMessage.UNKNOWN,
                request.getRequestURI()
        );
    }


    private static List<FieldValidationError> getFieldValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldValidationError(error.getField(), error.getDefaultMessage()))
                .toList();
    }
}
package pl.bdygasinski.gameoflife.rest.error;

import java.time.ZonedDateTime;
import java.util.List;

record ValidationErrorResponse(
        ZonedDateTime timestamp,
        int statusCode,
        String message,
        String requestUri,
        List<FieldValidationError> fieldErrors
) {}
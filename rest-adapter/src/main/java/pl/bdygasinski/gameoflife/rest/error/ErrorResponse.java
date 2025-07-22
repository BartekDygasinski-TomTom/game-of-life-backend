package pl.bdygasinski.gameoflife.rest.error;

import java.time.ZonedDateTime;

record ErrorResponse(
        ZonedDateTime timestamp,
        int statusCode,
        String message,
        String requestUri
) {
}
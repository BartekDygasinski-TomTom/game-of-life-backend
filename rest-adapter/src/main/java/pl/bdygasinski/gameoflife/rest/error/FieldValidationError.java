package pl.bdygasinski.gameoflife.rest.error;

record FieldValidationError(String field, String error) {}
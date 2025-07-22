package pl.bdygasinski.gameoflife.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class MatrixValidator implements ConstraintValidator<ValidMatrix, List<List<Boolean>>> {

    @Override
    public boolean isValid(List<List<Boolean>> matrix, ConstraintValidatorContext context) {
        if (matrix == null || matrix.isEmpty()) {
            return false;
        }

        List<Boolean> firstRow = matrix.getFirst();
        if (firstRow == null || firstRow.isEmpty()) {
            return false;
        }

        int expectedSize = firstRow.size();


        for (List<Boolean> row : matrix) {
            if (row == null || row.isEmpty()) {
                return false;
            }

            if (row.size() != expectedSize) {
                return false;
            }

            for (Boolean cell : row) {
                if (cell == null) return false;
            }
        }

        return true;
    }
}
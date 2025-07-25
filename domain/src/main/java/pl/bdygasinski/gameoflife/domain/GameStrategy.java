package pl.bdygasinski.gameoflife.domain;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;

@FunctionalInterface
public interface GameStrategy {

    @NonNull Cell applyCellTransition(@NonNull Cell cell, @NonNull CellStats cellStats);
}

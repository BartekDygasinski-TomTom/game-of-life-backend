package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;

public interface GameStrategy {

    Cell applyCellTransition(Cell cell, CellStats cellStats);
}

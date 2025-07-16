package pl.bdygasinski.gameoflife.domain;

import java.util.List;

public interface GameStrategy {

    Cell applyCellTransition(Cell cell, List<Cell> neighborCells);
}

package pl.bdygasinski.gameoflife.domain;

import java.util.stream.Stream;

public interface GameStrategy {

    Cell applyCellTransition(Cell cell, Stream<Cell> neighborCells);
}

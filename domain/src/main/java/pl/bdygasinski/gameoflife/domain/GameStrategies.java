package pl.bdygasinski.gameoflife.domain;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;

public enum GameStrategies implements GameStrategy {
    CLASSIC_GAME_OF_LIFE_STRATEGY {

        @Override
        public Cell applyCellTransition(@NonNull Cell cell, @NonNull CellStats cellStats) {
            long liveNeighbors = cellStats.liveNeighbors();
            return switch (cell) {
                case DEAD  -> (liveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
                case ALIVE -> (liveNeighbors < 2 || liveNeighbors > 3) ? Cell.DEAD : Cell.ALIVE;
            };
        }
    }
}

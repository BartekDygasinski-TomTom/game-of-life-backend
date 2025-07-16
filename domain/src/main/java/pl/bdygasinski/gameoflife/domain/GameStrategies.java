package pl.bdygasinski.gameoflife.domain;

import java.util.stream.Stream;

public enum GameStrategies implements GameStrategy {
    CLASSIC_GAME_OF_LIFE_STRATEGY {

        @Override
        public Cell applyCellTransition(Cell cell, Stream<Cell> neighborCells) {
            long liveNeighbors = neighborCells
                    .filter(c -> c == Cell.ALIVE)
                    .count();

            return switch (cell) {
                case DEAD  -> (liveNeighbors == 3) ? Cell.ALIVE : Cell.DEAD;
                case ALIVE -> (liveNeighbors < 2 || liveNeighbors > 3) ? Cell.DEAD : Cell.ALIVE;
            };
        }
    }
}

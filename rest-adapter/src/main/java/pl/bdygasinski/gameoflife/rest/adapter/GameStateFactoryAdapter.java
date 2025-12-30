package pl.bdygasinski.gameoflife.rest.adapter;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import pl.bdygasinski.gameoflife.domain.GameState;
import pl.bdygasinski.gameoflife.domain.GameStateFactory;
import pl.bdygasinski.gameoflife.domain.GameStrategies;
import pl.bdygasinski.gameoflife.domain.GameStrategy;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;
import pl.bdygasinski.gameoflife.rest.dto.NewRandomGameStateDto;

@Component
class GameStateFactoryAdapter implements GameStateService {

    private final GameStateFactory gameStateFactory;
    private final MatrixMapper matrixMapper;

    GameStateFactoryAdapter(@NonNull GameStateFactory gameStateFactory, @NonNull MatrixMapper matrixMapper) {
        this.gameStateFactory = gameStateFactory;
        this.matrixMapper = matrixMapper;
    }

    @Override
    public GameStateDto nextStep(@NonNull GameStateDto dto, @NonNull GameStrategy gameStrategy) {
        Matrix2D<Cell> matrix = matrixMapper.toDomain(dto);
        GameState currentGameStep = gameStateFactory.fromBaseState(matrix, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY);
        GameState nextGameStep = currentGameStep.nextStep();
        return matrixMapper.toDto(nextGameStep.board());
    }

    @Override
    public GameStateDto random(@NonNull NewRandomGameStateDto dto, @NonNull GameStrategy gameStrategy) {
        MatrixDimensions matrixDimensions = new MatrixDimensions(dto.rows(), dto.columns());
        GameState nextGameStep = gameStateFactory
                .randomGameStateWithAlivePercentage(matrixDimensions, dto.alivePercentage(), gameStrategy);
        return matrixMapper.toDto(nextGameStep.board());
    }
}
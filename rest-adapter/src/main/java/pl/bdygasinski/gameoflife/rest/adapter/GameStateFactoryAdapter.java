package pl.bdygasinski.gameoflife.rest.adapter;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import pl.bdygasinski.gameoflife.domain.GameState;
import pl.bdygasinski.gameoflife.domain.GameStateFactory;
import pl.bdygasinski.gameoflife.domain.GameStrategies;
import pl.bdygasinski.gameoflife.domain.GameStrategy;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

@Component
class GameStateFactoryAdapter implements GameStateService {

    private final GameStateFactory gameStateFactory;
    private final MatrixMapper matrixMapper;

    GameStateFactoryAdapter(@NonNull GameStateFactory gameStateFactory, @NonNull MatrixMapper matrixMapper) {
        this.gameStateFactory = gameStateFactory;
        this.matrixMapper = matrixMapper;
    }

    @Override
    public GameStateDto nextStep(GameStateDto dto, GameStrategy gameStrategy) {
        Matrix2D<Cell> matrix = matrixMapper.toDomain(dto);
        GameState gameState = gameStateFactory.fromBaseState(matrix, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY);
        GameState nextStepBoard = gameState.nextStep();
        return matrixMapper.toDto(nextStepBoard.board());
    }
}
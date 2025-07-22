package pl.bdygasinski.gameoflife.rest.adapter;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.GameStrategy;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;
import pl.bdygasinski.gameoflife.rest.dto.NewRandomGameStateDto;

public interface GameStateService {

    GameStateDto nextStep(GameStateDto gameStateDto, GameStrategy gameStrategy);

    GameStateDto random(@NonNull NewRandomGameStateDto dto, @NonNull GameStrategy gameStrategy);
}
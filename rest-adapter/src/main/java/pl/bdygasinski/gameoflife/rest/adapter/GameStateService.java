package pl.bdygasinski.gameoflife.rest.adapter;

import pl.bdygasinski.gameoflife.domain.GameStrategy;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

public interface GameStateService {

    GameStateDto nextStep(GameStateDto gameStateDto, GameStrategy gameStrategy);
}
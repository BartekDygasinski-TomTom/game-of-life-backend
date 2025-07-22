package pl.bdygasinski.gameoflife.rest.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bdygasinski.gameoflife.domain.GameStrategies;
import pl.bdygasinski.gameoflife.rest.adapter.GameStateService;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

@RestController
@RequestMapping(ApiVersion.V1 + "/gamestates")
class GameStateController {

    private final GameStateService gameStateService;

    GameStateController(@NonNull GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @PostMapping("/next")
    @ResponseStatus(HttpStatus.OK)
    GameStateDto nextStep(@Valid @RequestBody GameStateDto requestBodyContent) {
        return gameStateService.nextStep(requestBodyContent, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY);
    }
}
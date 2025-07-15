package pl.bdygasinski.gameoflife.domain.cell;

import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

public sealed interface Cell permits LiveCell, DeadCell {

    Coordinate2D coordinate2D();
}

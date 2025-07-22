package pl.bdygasinski.gameoflife.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpringContextIT {

    @DisplayName("Should correctly boot up")
    @Test
    void shouldBootUp() {
        Application.main(new String[0]);
    }
}
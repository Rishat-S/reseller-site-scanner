package ru.rishat.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * Test for position entity
 *
 */
class PositionTest {
    static Position position = new Position();

    @BeforeAll
    public static void b() {
        position.setPercent(10);
        position.setProductPurchasePrise(222);
    }

    @Test
    void getIntermediatePrice250outOf222With10Percent() {
        Assertions.assertEquals(250, position.getIntermediatePrice());
    }

    @Test
    void getPrice280outOf250With10Percent() {
        Assertions.assertEquals(280, position.getPrice());
    }
}
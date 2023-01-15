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
    public static void setPurchasePrice() {
        position.setProductPurchasePrice(222);
    }

    @Test
    void getIntermediatePrice250outOf222With10Percent() {
        position.setSpecialTypeOfCalculation(false);
        position.setPercent(10);
        Assertions.assertEquals(250, position.getIntermediatePrice());
    }

    @Test
    void getPrice275outOf250With10Percent() {
        position.setSpecialTypeOfCalculation(false);
        position.setPercent(10);
        Assertions.assertEquals(275, position.getPrice());
    }

    @Test
    void getPrice380OfSpecialType29kl30() {
        position.setSpecialTypeOfCalculation(true);
        position.setSpecialProductPurchasePrice(290);
        position.setPercent(30);
        Assertions.assertEquals(380, position.getPrice());
    }
}
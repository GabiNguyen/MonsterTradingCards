package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class BattleServiceTest {
    @Test
    void testGoblinVsDragon() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "Goblin", 45.0, "player1", "Normal", "Monster");
        Card card2 = new Card("card2Id", "Dragon", 20.0, "player2", "Normal", "Monster");

        // Act & Assert
        assertEquals("Dragon",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testWizzardVsOrk() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "Wizzard", 30.0, "player1", "Normal", "Monster");
        Card card2 = new Card("card2Id", "Ork", 50.0, "player2", "Normal", "Monster");

        // Act & Assert
        assertEquals("Wizzard",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testKnightVsWaterSpell() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "Knight", 55.0, "player1", "Normal", "Monster");
        Card card2 = new Card("card2Id", "WaterSpell", 25.0, "player2", "Water", "Spell");

        // Act & Assert
        assertEquals("WaterSpell",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testFireElfVsDragon() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "FireElf", 35.0, "player1", "Fire", "Monster");
        Card card2 = new Card("card2Id", "Dragon", 45.0, "player2", "Normal", "Monster");

        // Act & Assert
        assertEquals("FireElf",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testMonsterVsMonster() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "WaterGoblin", 50.0, "player1", "Water", "Monster");
        Card card2 = new Card("card2Id", "FireElf", 35.0, "player2", "Fire", "Monster");

        // Act & Assert
        assertEquals("WaterGoblin",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testSpellVsSpell() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "WaterSpell", 20.0, "player1", "Water", "Spell");
        Card card2 = new Card("card2Id", "FireSpell", 10.0, "player2", "Fire", "Spell");

        // Act & Assert
        assertEquals("WaterSpell",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testSpellVsSpell2() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "FireSpell", 20.0, "player1", "Fire", "Spell");
        Card card2 = new Card("card2Id", "WaterSpell", 5.0, "player2", "Water", "Spell");

        // Act & Assert
        assertEquals("",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testSpellVsSpell3() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "WaterSpell", 5.0, "player1", "Water", "Spell");
        Card card2 = new Card("card2Id", "FireSpell", 90.0, "player2", "Fire", "Spell");

        // Act & Assert
        assertEquals("FireSpell",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testSpellVsMonster() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "FireSpell", 10.0, "player1", "Fire", "Spell");
        Card card2 = new Card("card2Id", "WaterGoblin", 10.0, "player2", "Water", "Monster");

        // Act & Assert
        assertEquals("WaterGoblin",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testSpellVsMonster2() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "WaterSpell", 10.0, "player1", "Water", "Spell");
        Card card2 = new Card("card2Id", "WaterGoblin", 10.0, "player2", "Water", "Monster");

        // Act & Assert
        assertEquals("",  battleService.roundBattle(card1, card2));
    }
    @Test
    void testSpellVsMonster3() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "RegularSpell", 10.0, "player1", "Normal", "Spell");
        Card card2 = new Card("card2Id", "WaterGoblin", 10.0, "player2", "Water", "Monster");

        // Act & Assert
        assertEquals("RegularSpell",  battleService.roundBattle(card1, card2));
    }
    @Test
    void testSpellVsMonster4() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "RegularSpell", 10.0, "player1", "Normal", "Spell");
        Card card2 = new Card("card2Id", "Knight", 15.0, "player2", "Normal", "Monster");

        // Act & Assert
        assertEquals("Knight",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testKnightVsWaterGoblin() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "Knight", 20.0, "player1", "Normal", "Monster");
        Card card2 = new Card("card2Id", "WaterGoblin", 10.0, "player2", "Water", "Monster");

        // Act & Assert
        assertEquals("Knight",  battleService.roundBattle(card1, card2));
    }

    @Test
    void testWaterSpellVsWaterGoblin() {

        // Arrange
        var battleService = new BattleService();

        Card card1 = new Card("card1Id", "WaterSpell", 20.0, "player1", "Water", "Monster");
        Card card2 = new Card("card2Id", "WaterGoblin", 10.0, "player2", "Water", "Monster");

        // Act & Assert
        assertEquals("WaterSpell", battleService.roundBattle(card1, card2));

    }
}
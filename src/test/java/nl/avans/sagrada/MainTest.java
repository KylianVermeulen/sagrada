package nl.avans.sagrada;

import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class MainTest {

    public static void main(String[] args) {
        PatternCard patternCard = new PatternCard(1, 1, true);
        GameDie gameDie1 = new GameDie(1, "rood", 1);
        GameDie gameDie2 = new GameDie(2, "blauw", 2);
        GameDie gameDie3 = new GameDie(3, "groen", 3);
        GameDie gameDie4 = new GameDie(4, "paars", 4);
        GameDie gameDie5 = new GameDie(5, "geel", 5);
        GameDie gameDie6 = new GameDie(6, "rood", 6);
        GameDie gameDie7 = new GameDie(7, "rood", 1);
        GameDie gameDie8 = new GameDie(8, "rood", 2);
        patternCard.placeDie(1, 1, gameDie1);
        patternCard.placeDie(2, 1, gameDie2);
        patternCard.placeDie(3, 1, gameDie3);
        patternCard.placeDie(4, 1, gameDie4);
        patternCard.placeDie(5, 1, gameDie5);
        patternCard.placeDie(2, 2, gameDie6);
        patternCard.placeDie(2, 3, gameDie7);
        patternCard.placeDie(2, 4, gameDie8);
        System.out.println(
                "POJ1: " + new PublicObjectiveCard(1, "x", "x", 3).calculateScore(patternCard));
        System.out.println(
                "POJ2: " + new PublicObjectiveCard(2, "x", "x", 3).calculateScore(patternCard));
        System.out.println(
                "POJ3: " + new PublicObjectiveCard(3, "x", "x", 3).calculateScore(patternCard));
        System.out.println(
                "POJ4: " + new PublicObjectiveCard(4, "x", "x", 3).calculateScore(patternCard));
        System.out.println(
                "POJ5: " + new PublicObjectiveCard(5, "x", "x", 3).calculateScore(patternCard));
        System.out.println(
                "POJ7: " + new PublicObjectiveCard(7, "x", "x", 3).calculateScore(patternCard));
    }
}

package modelo;

import consumables.ScoreExclusivity;
import exceptions.NoMoreConsumablesException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PlayerTest {
    @Test
    public void testSetAnAmountOfPointsToPlayerAndGetIt(){
        Player player = new Player();
        Integer points = 5;
        player.gainAmountOfPoints(points);

        Assert.assertEquals(player.getPoints(),points);

    }

    @Test
    public void testSetNegativePointsToPlayerAndGetZeroPoints(){
        Player player = new Player();
        Integer points = -5 ;
        Integer zero = 0 ;
        player.gainAmountOfPoints(points);

        Assert.assertEquals(player.getPoints(),zero);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPlayerActivatesConsumableThreeTimesAndThrowsException() throws NoMoreConsumablesException {
        //Arrange
        ScoreExclusivity scoreExclusivity = new ScoreExclusivity();

        Player player = new Player();
        player.activateConsumable(scoreExclusivity);
        player.activateConsumable(scoreExclusivity);
        //Act and Assert
        thrown.expect(NoMoreConsumablesException.class);
        player.activateConsumable(scoreExclusivity);

    }

    @Test
    public void testInstanceAndGetNameShouldBeOK() {
        String name = "jose";
        Player player = new Player(name);
        Assert.assertEquals(name, player.getName());

    }

    @Test
    public void testSetAndGetNameShouldBeOK() {
        Player player = new Player();
        String name = "jose";

        player.setName(name);
        Assert.assertEquals(name, player.getName());

    }
}


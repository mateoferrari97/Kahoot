package modelo;

import modelo.options.CorrectOptionScorer;
import modelo.options.IncorrectOptionScorer;
import modelo.options.Option;
import modelo.questions.BooleanQuestion;
import modelo.questions.Question;
import modelo.scorers.PenaltyScorer;
import modelo.scorers.QuestionScorer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BooleanQuestionPenaltyTest {

    @Test
    public void testBooleanQuestionWithPenaltyDecreasePlayerPointsWhenOptionIsIncorrect() {
        // Given
        List<Option> options = Arrays.asList(
                new Option("si", new CorrectOptionScorer()),
                new Option("no", new IncorrectOptionScorer()));
        QuestionScorer scorer = new PenaltyScorer();
        Question question = new BooleanQuestion("vamos a aprobar algoritmos 3?", options, scorer);

        Player player = new Player();
        player.setPoints(5);
        List<Option> playerOptions = Arrays.asList(new Option("no", new IncorrectOptionScorer()));
        Integer expectedPlayerPoints = 4;

        // When
        question.score(player, playerOptions);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testBooleanQuestionWithPenaltyIncreasePlayerPointsWhenOptionIsCorrect() {
        // Given
        List<Option> options = Arrays.asList(
                new Option("si", new CorrectOptionScorer()),
                new Option("no", new IncorrectOptionScorer()));
        QuestionScorer scorer = new PenaltyScorer();
        Question question = new BooleanQuestion("vamos a aprobar algoritmos 3?", options, scorer);

        Player player = new Player();
        player.setPoints(5);
        List<Option> playerOptions = Arrays.asList(new Option("si", new CorrectOptionScorer()));
        Integer expectedPlayerPoints = 6;

        // When
        question.score(player, playerOptions);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testBooleanQuestionWithPenaltyDontDecreasePlayerPointsWhenOptionIsIncorrectAndPlayerHasntPoints() {
        // Given
        List<Option> options = Arrays.asList(
                new Option("si", new CorrectOptionScorer()),
                new Option("no", new IncorrectOptionScorer()));
        QuestionScorer scorer = new PenaltyScorer();
        Question question = new BooleanQuestion("vamos a aprobar algoritmos 3?", options, scorer);

        Player player = new Player();
        player.setPoints(0);
        List<Option> playerOptions = Arrays.asList(new Option("no", new IncorrectOptionScorer()));
        Integer expectedPlayerPoints = 0;

        // When
        question.score(player, playerOptions);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testBooleanQuestionWithPenaltyGivesDoubleThePointsToPlayerWhenUsingDoubleMultiplicatorWhenOptionIsCorrect() {
        // Given
        List<Option> options = Arrays.asList(
                new Option("si", new CorrectOptionScorer()),
                new Option("no", new IncorrectOptionScorer()));
        QuestionScorer scorer = new PenaltyScorer();
        Multiplicator multiplicator = new PenaltyMultiplicator();
        Question question = new BooleanQuestion("vamos a aprobar algoritmos 3?", options, scorer, multiplicator);

        Player player = new Player();
        player.setPoints(5);
        List<Option> playerOptions = Arrays.asList(new Option("si", new CorrectOptionScorer()));
        Integer expectedPlayerPoints = 7;

        // When
        question.multiplicate(2);
        question.score(player, playerOptions);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testBooleanQuestionWithPenaltyGivesTripleThePointsToPlayerWhenUsingTripleMultiplicatorWhenOptionIsCorrect() {
        // Given
        List<Option> options = Arrays.asList(
                new Option("si", new CorrectOptionScorer()),
                new Option("no", new IncorrectOptionScorer()));
        QuestionScorer scorer = new PenaltyScorer();
        Multiplicator multiplicator = new PenaltyMultiplicator();
        Question question = new BooleanQuestion("vamos a aprobar algoritmos 3?", options, scorer, multiplicator);

        Player player = new Player();
        player.setPoints(5);
        List<Option> playerOptions = Arrays.asList(new Option("si", new CorrectOptionScorer()));
        Integer expectedPlayerPoints = 8;

        // When
        question.multiplicate(3);
        question.score(player, playerOptions);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }
}

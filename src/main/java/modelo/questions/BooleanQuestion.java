package modelo.questions;

import modelo.consumables.*;
import modelo.game.Player;
import modelo.game.Points;
import modelo.options.Option;
import modelo.scorers.QuestionScorer;

import java.util.LinkedList;
import java.util.List;

import static constantes.Constantes.BOOLEAN_QUESTION_TYPE;

public class BooleanQuestion extends Question {
    public BooleanQuestion(String text, List<Option> options, QuestionScorer scorer, Consumable consumable) {
        super();
        this.options = options;
        this.text = text;
        this.scorer = scorer;
        this.points = new Points();
        this.consumable = consumable;
        this.type = BOOLEAN_QUESTION_TYPE;
    }


    @Override
    public void selectOptions(List<Option> playerAnswers) {
        for (Option aOption : playerAnswers) {
            aOption.calculatePoints(scorer, this.points);
        }
        if (!(this.isCorrect())) this.consumable.useWithIncorrectAnswer();
    }

    @Override
    public void score(Player player) {
        this.consumable.multiplicate(this.points);
        scorer.score(player, this.points);
    }

    public List<Option> getCorrectOptions() {
        List<Option> options = new LinkedList<>();
        for (Option option : this.options) {
            if (option.isCorrect()) {
                options.add(option);
            }
        }

        return options;
    }
}

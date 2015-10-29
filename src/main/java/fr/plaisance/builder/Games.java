package fr.plaisance.builder;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import fr.plaisance.model.Answer;
import fr.plaisance.model.Challenge;
import fr.plaisance.model.Game;
import fr.plaisance.model.Paper;
import fr.plaisance.model.Player;
import fr.plaisance.model.Question;
import fr.plaisance.model.Quizz;

public abstract class Games {

	public static Answer newAnswer(String value) {
		Answer answer = new Answer();
		answer.setValue(value);
		return answer;
	}

	public static Question newQuestion(String value) {
		Question question = new Question();
		question.setValue(value);
		return question;
	}

	public static Player newPlayer(String username) {
		Player player = new Player();
		player.setUsername(username);
		return player;
	}

	public static Challenge newChallenge(String question, String... answers) {
		Challenge challenge = new Challenge();
		challenge.setQuestion(newQuestion(question));
		challenge.setAnswers(Arrays
			.asList(answers)
			.stream()
			.map(Games::newAnswer)
			.collect(Collectors.toSet()));
		return challenge;
	}

	public static Quizz newQuizz(List<Challenge> challenges) {
		Quizz quizz = new Quizz();
		quizz.setChallenges(challenges);
		return quizz;
	}

	public static Paper newPaper() {
		return new Paper();
	}

	public static Game newGame(Player player, Quizz quizz) {
		Game game = new Game();
		game.setPlayer(player);
		game.setQuizz(quizz);
		game.setDate(Date.from(Instant.now()));
		game.setPaper(Games.newPaper());
		return game;
	}
}

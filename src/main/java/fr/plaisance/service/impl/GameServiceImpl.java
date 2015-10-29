package fr.plaisance.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.plaisance.builder.Games;
import fr.plaisance.model.Answer;
import fr.plaisance.model.Challenge;
import fr.plaisance.model.Game;
import fr.plaisance.model.Player;
import fr.plaisance.model.Proposition;
import fr.plaisance.model.Question;
import fr.plaisance.model.Quizz;
import fr.plaisance.service.DataService;
import fr.plaisance.service.GameService;
import fr.plaisance.service.MetricService;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private DataService dataService;

	@Autowired
	private MetricService metricService;

	@Override
	public Game game(Player player, long size) {
		return Games.newGame(player, this.quizz(size));
	}

	@Override
	public Quizz quizz(long size) {
		if (size < 0) {
			size = 10;
		}
		List<Challenge> challenges = dataService.load("capitales");
		Collections.shuffle(challenges);
		challenges = challenges
			.stream()
			.limit(size)
			.collect(Collectors.toList());
		return Games.newQuizz(challenges);
	}

	@Override
	public Proposition proposition(Challenge challenge, String value) {
		Proposition proposition = new Proposition();
		proposition.setQuestion(challenge.getQuestion());
		proposition.setAnswer(Games.newAnswer(value));
		proposition.setCorrect(this.correct(challenge, value));
		return proposition;
	}

	@Override
	public Challenge challenge(Game game) {
		List<Question> questions = game
			.getPaper()
			.getPropositions()
			.stream()
			.map(Proposition::getQuestion)
			.collect(Collectors.toList());
		List<Challenge> challenges = game
			.getQuizz()
			.getChallenges()
			.stream()
			.filter(challenge -> !questions.contains(challenge.getQuestion()))
			.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(challenges)) {
			return null;
		}
		return challenges.get(0);
	}

	public boolean correct(Challenge challenge, String value) {
		for (Answer answer : challenge.getAnswers()) {
			if (metricService.matches(answer.getValue(), value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean answer(Game game, String value) {
		Challenge challenge = this.challenge(game);
		Proposition proposition = this.proposition(challenge, value);
		boolean answer = proposition.isCorrect();
		game.getPaper().addProposition(proposition);
		return answer;
	}

}

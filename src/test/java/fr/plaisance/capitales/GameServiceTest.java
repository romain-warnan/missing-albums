package fr.plaisance.capitales;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.plaisance.builder.Games;
import fr.plaisance.model.Challenge;
import fr.plaisance.model.Game;
import fr.plaisance.model.Player;
import fr.plaisance.model.Proposition;
import fr.plaisance.model.Quizz;
import fr.plaisance.service.GameService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
public class GameServiceTest {

	@Autowired
	private GameService gameService;

	@Test
	public void quizz() {
		Quizz quizz = gameService.quizz(30l);
		quizz.getChallenges();
		Assertions.assertThat(quizz).isNotNull().hasSize(30);
	}

	@Test
	public void proposition() {
		Challenge challenge = Games.newChallenge("France", "Paris");
		Assertions.assertThat(gameService.proposition(challenge, "Paris").isCorrect()).isTrue();
		Assertions.assertThat(gameService.proposition(challenge, "Rome").isCorrect()).isFalse();
	}

	@Test
	public void challenge() {
		Player player = Games.newPlayer("Romain");
		Game game = gameService.game(player, 10);
		Challenge challenge1 = game.getQuizz().getChallenges().get(0);
		Challenge challenge2 = game.getQuizz().getChallenges().get(1);
		Proposition proposition = gameService.proposition(challenge1, StringUtils.EMPTY);
		game.getPaper().addProposition(proposition);
		Assertions.assertThat(gameService.challenge(game)).isEqualTo(challenge2);
	}

	@Test
	public void game() {
		Player player = Games.newPlayer("Romain");
		Game game = gameService.game(player, 10);
		Assertions.assertThat(game.getDate()).isNotNull();
		Assertions.assertThat(game.getPaper().getPropositions()).isEmpty();
		Assertions.assertThat(game.getQuizz().getChallenges()).hasSize(10);
		Assertions.assertThat(game.getPlayer().getUsername()).isEqualTo("Romain");
	}
}

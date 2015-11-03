package fr.plaisance.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.plaisance.builder.Games;
import fr.plaisance.model.Challenge;
import fr.plaisance.model.Game;
import fr.plaisance.model.Player;
import fr.plaisance.model.Proposition;
import fr.plaisance.model.Question;
import fr.plaisance.service.GameService;

@Service
@Path("/")
public class GameWebservice {

	private static Logger logger = LoggerFactory.getLogger(GameWebservice.class);

	@Autowired
	private GameService gameService;

	@Context
	private HttpServletRequest request;

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/game/{size}/{username}")
	public void start(@PathParam("size") long size, @PathParam("username") String username) {
		logger.info("start : " + username + ", " + size);
		Player player = Games.newPlayer(username);
		Game game = this.gameService.game(player, size);
		this.putGame(game);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/next")
	public Question next() {
		logger.info("next");
		Game game = this.getGame();
		Challenge challenge = this.gameService.challenge(game);
		if (challenge == null) {
			return null;
		}
		return challenge.getQuestion();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/answer/{answer}")
	public Proposition answer(@PathParam("answer") String answer) {
		logger.info("answer : " + answer);
		Game game = this.getGame();
		return this.gameService.answer(game, answer);
	}

	private Game getGame() {
		HttpSession session = request.getSession();
		return (Game) session.getAttribute("game");
	}

	private void putGame(Game game) {
		HttpSession session = request.getSession(true);
		session.setAttribute("game", game);
	}
}

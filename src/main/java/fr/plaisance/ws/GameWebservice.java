package fr.plaisance.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
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
import fr.plaisance.service.GameService;

@Service
@Path("/")
public class GameWebservice {

	private static Logger logger = LoggerFactory.getLogger(GameWebservice.class);

	@Autowired
	private GameService gameService;

	@Context
	private HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/game/{size}/{username}")
	public Game start(@PathParam("size") long size, @PathParam("username") String username) {
		logger.info("Start : " + username + ", " + size);
		Player player = Games.newPlayer(username);
		Game game = this.gameService.game(player, size);
		HttpSession session = request.getSession(true);
		session.setAttribute("game", game);
		return game;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/play")
	public Challenge play() {
		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		Challenge challenge = this.gameService.challenge(game);
		return challenge;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/answer/{answer}")
	public Challenge answer(@PathParam("answer") String answer) {
		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("game");
		logger.info(String.valueOf(this.gameService.answer(game, answer)));
		return this.gameService.challenge(game);
	}
}

package fr.plaisance.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import fr.plaisance.builder.Games;
import fr.plaisance.model.Player;
import fr.plaisance.model.Quizz;
import fr.plaisance.service.GameService;

@Service
@Path("/")
public class PlayerWebservice {

	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	private GameService gameService;

	@Context
	private HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/player/add")
	public Player addPlayer(@QueryParam("username") String username) {
		Player player = Games.newPlayer(username);
		mongoOperation.save(player);
		return player;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/player/find")
	public Player findPlayer(@QueryParam("username") String username) {
		Query query = new Query(Criteria.where("username").is(username));
		Player player = mongoOperation.findOne(query, Player.class);
		return player;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/player/findAll")
	public List<Player> findAllPlayer() {
		List<Player> players = mongoOperation.findAll(Player.class);
		return players;
	}

	@DELETE
	@Path("/player/remove")
	public void removePlayer(@QueryParam("username") String username) {
		Query query = new Query(Criteria.where("username").is(username));
		mongoOperation.remove(query, Player.class);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("/quizz")
	public Quizz quizz(@QueryParam("size") long size) throws Exception {
		HttpSession session = request.getSession(true);
		Quizz quizz = (Quizz) session.getAttribute("quizz");
		if (quizz == null) {
			quizz = gameService.quizz(size);
			session.setAttribute("quizz", quizz);
		}
		return quizz;
	}
}

package fr.plaisance.service;

import fr.plaisance.model.Challenge;
import fr.plaisance.model.Game;
import fr.plaisance.model.Player;
import fr.plaisance.model.Proposition;
import fr.plaisance.model.Quizz;

public interface GameService {

	/**
	 * Creates a new quizz.
	 * 
	 * @param size - Number of challenges in the quizz. Must be positive.
	 * @return A new quizz containing <code>size</code> challenges.
	 */
	Quizz quizz(long size);

	/**
	 * Creates a new proposition made of the challenge, the answer given and the correction.
	 * 
	 * @param challenge - The current challenge.
	 * @param value - The answer given to the challenge.
	 * @return A corrected proposition.
	 */
	Proposition proposition(Challenge challenge, String value);

	/**
	 * Creates a new game.
	 * 
	 * @param player - The player that will play this game.
	 * @param size - The number of challenges in this game. Must be positive.
	 * @return A new game containing <code>size</code> challenges.
	 */
	Game game(Player player, long size);

	/**
	 * Return the next challenge of the current game.
	 * The next challenge is the first remaining challenge that have not already be played or <code>null</code> if all challenges have been played.
	 * 
	 * @param game - The current game.
	 * @return The next challenge of this game.
	 */
	Challenge challenge(Game game);

	/**
	 * Corrects a proposition by matching it against the right answers to a challenge.
	 * 
	 * @param challenge - The current challenge.
	 * @param value - The answer given to the challenge.
	 * @return <code>true</code> if the given answer matches one of the right answer of this challenge and <code>false</code> otherwise.
	 * @see MetricService#matches(String, String)
	 */
	boolean correct(Challenge challenge, String value);

	/**
	 * Corrects a proposition by matching it against the right answers to a challenge.
	 * 
	 * @param game - The current game.
	 * @param value - The answer given to the current challenge.
	 * @return The corrected proposition to the current challenge.
	 */
	Proposition answer(Game game, String value);
}

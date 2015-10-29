package fr.plaisance.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Quizz implements Iterable<Challenge> {

	private List<Challenge> challenges;

	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	@Override
	public Iterator<Challenge> iterator() {
		return challenges.iterator();
	}

	public void addChallenge(Challenge challenge) {
		if (this.challenges == null) {
			this.challenges = new ArrayList<>();
		}
		this.challenges.add(challenge);
	}
}

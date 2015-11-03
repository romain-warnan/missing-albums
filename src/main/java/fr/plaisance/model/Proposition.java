package fr.plaisance.model;

import com.google.common.base.Objects;

public class Proposition {

	private Challenge challenge;
	private String value;
	private boolean correct;

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.challenge.getQuestion());
	}

	@Override
	public boolean equals(Object object) {

		if (object == null) {
			return false;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		final Proposition other = (Proposition) object;
		return Objects.equal(this.challenge.getQuestion(), other.challenge.getQuestion()) && Objects.equal(this.correct, other.correct);
	}

	@Override
	public String toString() {
		return "Question: " + this.challenge.getQuestion().getValue() + "? " + this.value + " correct: " + this.correct;
	}
}

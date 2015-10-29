package fr.plaisance.model;

import com.google.common.base.Objects;

public class Proposition {

	private Question question;
	private Answer answer;
	private Boolean correct;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Boolean isCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.question);
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
		return Objects.equal(this.question, other.question) && Objects.equal(this.correct, other.correct);
	}

	@Override
	public String toString() {
		return "Question: " + this.question.getValue() + "? " + this.answer + " correct: " + (this.correct == null ? "?" : this.correct);
	}
}

package fr.plaisance.model;

import java.util.Set;

import com.google.common.base.Objects;

public class Challenge {

	private Question question;
	private Set<Answer> answers;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
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
		final Challenge other = (Challenge) object;
		return Objects.equal(this.question, other.question);
	}

	@Override
	public String toString() {
		return "Question: " + this.question.getValue() + "? " + this.answers;
	}
}

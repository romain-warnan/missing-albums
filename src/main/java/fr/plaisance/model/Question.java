package fr.plaisance.model;

import com.google.common.base.Objects;

public class Question {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.value);
	}

	@Override
	public boolean equals(Object object) {

		if (object == null) {
			return false;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		final Question other = (Question) object;
		return Objects.equal(this.value, other.value);
	}

	@Override
	public String toString() {
		return "Question: " + value;
	}
}

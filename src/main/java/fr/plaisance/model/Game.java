package fr.plaisance.model;

import java.util.Date;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Game {

	private Integer id;
	private Player player;
	private Quizz quizz;
	private Paper paper;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Quizz getQuizz() {
		return quizz;
	}

	public void setQuizz(Quizz quizz) {
		this.quizz = quizz;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

	@Override
	public boolean equals(Object object) {

		if (object == null) {
			return false;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		final Game other = (Game) object;
		return Objects.equal(this.id, other.id);
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("id", id)
			.add("size", quizz.getChallenges().size())
			.toString();
	}
}

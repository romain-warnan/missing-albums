package fr.plaisance.model;

import java.util.Date;

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
}

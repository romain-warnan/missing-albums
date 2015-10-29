package fr.plaisance.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Document(collection = "players")
public class Player {

	@Id
	private String id;

	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Player other = (Player) obj;
		return Objects.equal(this.id, other.id);
	}

	@Override
	public String toString() {
		return "Player : " + MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("username", username)
			.toString();
	}
}

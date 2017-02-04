package fr.plaisance.arn.bean;

import com.owlike.genson.annotation.JsonProperty;

import java.util.List;

public class DiscogsReleases {

	@JsonProperty("releases")
	private List<DiscogsAlbum> albums;

	public List<DiscogsAlbum> getAlbums() {
		return albums;
	}

	public void setAlbums(List<DiscogsAlbum> albums) {
		this.albums = albums;
	}

	public static class DiscogsAlbum {

		@JsonProperty("title")
		private String title;

		@JsonProperty("type")
		private String type;

		@JsonProperty("year")
		private Integer year;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getType() {
			return type;
		}

		public void setType(String format) {
			this.type = format;
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}
	}
}

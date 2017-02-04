package fr.plaisance.arn.bean;

import com.owlike.genson.annotation.JsonProperty;

import java.util.List;

public class DiscogsQuery {

	@JsonProperty("results")
	private List<DiscogsArtist> artists;

	public List<DiscogsArtist> getArtists() {
		return artists;
	}

	public void setArtists(List<DiscogsArtist> artists) {
		this.artists = artists;
	}

	public static class DiscogsArtist {

		@JsonProperty("resource_url")
		private String resourceUrl;

		public String getResourceUrl() {
			return resourceUrl;
		}

		public void setResourceUrl(String resourceUrl) {
			this.resourceUrl = resourceUrl;
		}
	}
}

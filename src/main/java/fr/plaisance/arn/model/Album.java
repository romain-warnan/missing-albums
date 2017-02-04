package fr.plaisance.arn.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.apache.commons.lang3.StringUtils;

public class Album implements Comparable<Album> {

	private String name, year;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Album) {
			Album other = (Album) object;
			return StringUtils.equalsIgnoreCase(this.name, other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(StringUtils.lowerCase(this.name));
	}

	@Override
	public int compareTo(Album other) {
		return ComparisonChain.start().compare(this.year, other.year).result();
	}

	@Override
	public String toString() {
		return this.year + " - " + this.name;
	}

}

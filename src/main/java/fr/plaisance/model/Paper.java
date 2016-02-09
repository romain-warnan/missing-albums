package fr.plaisance.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "papers")
public class Paper implements Iterable<Proposition> {

	@Id
	private String id;

	private List<Proposition> propositions = Lists.newArrayList();

	public List<Proposition> getPropositions() {
		return propositions;
	}

	public void setPropositiones(List<Proposition> propositions) {
		this.propositions = propositions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Iterator<Proposition> iterator() {
		return propositions.iterator();
	}

	public void addProposition(Proposition proposition) {
		if (this.propositions == null) {
			this.propositions = new ArrayList<>();
		}
		this.propositions.add(proposition);
	}
}

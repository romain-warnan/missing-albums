package fr.plaisance.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.util.Lists;

public class Paper implements Iterable<Proposition> {

	private List<Proposition> propositions = Lists.newArrayList();

	public List<Proposition> getPropositions() {
		return propositions;
	}

	public void setPropositiones(List<Proposition> propositions) {
		this.propositions = propositions;
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

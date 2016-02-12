package fr.plaisance.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import fr.plaisance.dao.PaperDao;
import fr.plaisance.model.Paper;

@Repository
public class PaperDaoImpl extends SimpleMongoRepository<Paper, Long> implements PaperDao {

	@Autowired
	public PaperDaoImpl(MongoRepositoryFactory mongoRepositoryFactory, MongoOperations mongoOperations) {
		this(mongoRepositoryFactory.getEntityInformation(Paper.class), mongoOperations);
	}

	private PaperDaoImpl(MongoEntityInformation<Paper, Long> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}

}

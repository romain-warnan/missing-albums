package fr.plaisance.dao;

import org.springframework.data.repository.CrudRepository;

import fr.plaisance.model.Paper;

public interface PaperDao extends CrudRepository<Paper, Long> {}

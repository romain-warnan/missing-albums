package fr.plaisance.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import fr.plaisance.builder.Games;
import fr.plaisance.model.Challenge;
import fr.plaisance.service.DataService;

@Service
public class DataServiceImpl implements DataService {

	private static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

	@Override
	@Cacheable("dataCache")
	public List<Challenge> load(String name) {
		Path path = Paths.get("C:/data/files/" + name + ".txt");
		try (Stream<String> stream = Files.lines(path)) {
			return stream
				.map(line -> line.split("\t"))
				.map(tokens -> Games.newChallenge(tokens[0], tokens[1].split(",")))
				.collect(Collectors.toList());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}

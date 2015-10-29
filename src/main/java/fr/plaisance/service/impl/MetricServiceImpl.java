package fr.plaisance.service.impl;

import java.util.Locale;

import org.simmetrics.StringMetric;
import org.simmetrics.StringMetricBuilder;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.simplifiers.Simplifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.plaisance.service.MetricService;

@Service
public class MetricServiceImpl implements MetricService {

	private static StringMetric metric;
	private static final float THRESHOLD = 0.7f;
	private static Logger logger = LoggerFactory.getLogger(MetricServiceImpl.class);

	public MetricServiceImpl() {
		metric = StringMetricBuilder
			.with(new Levenshtein())
			.simplify(Simplifiers.toUpperCase(Locale.FRENCH))
			.build();
	}

	@Override
	public boolean matches(String a, String b) {
		if (a == null || b == null) {
			return false;
		}
		float score = metric.compare(a, b);
		logger.info(String.valueOf(score));
		return score > THRESHOLD;
	}

}

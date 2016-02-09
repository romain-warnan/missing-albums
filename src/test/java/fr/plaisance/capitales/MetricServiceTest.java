package fr.plaisance.capitales;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.plaisance.service.MetricService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
public class MetricServiceTest {

	@Autowired
	private MetricService metricService;

	@Test
	public void matches() {
		Assertions.assertThat(metricService.matches("Washington", "Washintown")).isTrue();
		Assertions.assertThat(metricService.matches("Rome", "Roma")).isTrue();
		Assertions.assertThat(metricService.matches("Kuala Lumpur", "Roma")).isFalse();
	}
}

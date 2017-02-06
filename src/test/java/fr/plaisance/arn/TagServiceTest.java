package fr.plaisance.arn;

import fr.plaisance.arn.model.Album;
import fr.plaisance.arn.model.Artist;
import fr.plaisance.arn.service.TagService;
import org.assertj.core.api.Assertions;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
public class TagServiceTest {

	private static String path = null;

	@Autowired
	private TagService tagService;

	@BeforeClass
	public static void beforeClass() {
		ClassPathResource classPathResource = new ClassPathResource("music/Amon Amarth - 01 - First Kill.mp3");
		try {
			path = classPathResource.getFile().getPath();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void album() {
		ID3V1Tag tag = tagService.tag(path);
		Album album = tagService.album(tag);
		System.out.println("album : " + album);
		Assertions.assertThat(album.getName()).isEqualToIgnoringCase("Jomsviking");
		Assertions.assertThat(album.getYear()).isEqualToIgnoringCase("2016");
	}

	@Test
	public void artist() throws IOException {
		ID3V1Tag tag = tagService.tag(path);
		Artist artist = tagService.artist(tag);
		System.out.println("artist : " + artist);
		Assertions.assertThat(artist.getName()).isEqualToIgnoringCase("Amon Amarth");
	}
}

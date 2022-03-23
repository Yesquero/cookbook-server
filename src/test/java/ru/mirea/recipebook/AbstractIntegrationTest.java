package ru.mirea.recipebook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "test")
@SpringBootTest
class AbstractIntegrationTest {

	@Test
	void contextLoads() {
	}

	@Test
	void failingTest() {
		Assertions.assertTrue(true);
	}

}

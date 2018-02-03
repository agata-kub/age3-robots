package pl.edu.agh.simulation.intruders.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.simulation.intruders.Main;
import pl.edu.agh.simulation.intruders.roson.model.RosonBuilding;

public class IOServiceTest {
	
	private IOService ioService = new IOService();
	
	private String SAMPLE_PATH = "roson/five_rooms_two_robots.roson";
	
	@Before
	public void setup() {

	}
	
	@Test
	public void testImportFromJson() throws IOException {
		// given
		File testFile = new File(Main.class.getClassLoader().getResource(SAMPLE_PATH).getFile());
		
		// when
		RosonBuilding building = ioService.importFromJson(testFile);
		
		// then
		assertEquals(5, building.getSpaces().size());
		assertEquals(10, building.getGates().size());
		assertEquals(32, building.getEdges().size());
	}

}

package pl.edu.agh.simulation.intruders.api.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.simulation.intruders.controller.IntruderController;
import pl.edu.agh.simulation.intruders.controller.SampleIntruderController;
import pl.edu.agh.simulation.intruders.model.DoorNode;

public class SampleIntruderControllerTest {
	
	IntruderController intruderController;
	
	@Before
	public void setup() {
		intruderController = new SampleIntruderController();
	}
	
	@After
	public void cleanup() {
		
	}
	
	@Test
	public void testIntruderController() {
		// given
		List<DoorNode> doorNodes = createSampleDoorNodesList();
		intruderController.init(doorNodes);
		
		// when
		intruderController.update();
		
		// then
		
	}

	private List<DoorNode> createSampleDoorNodesList() {
		List<DoorNode> doorNodes = new LinkedList<>();
		
		return null;
	}

}

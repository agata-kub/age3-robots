package pl.edu.agh.simulation.measure;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pl.edu.agh.simulation.intruders.api.impl.Building;
import pl.edu.agh.simulation.intruders.api.impl.DoorEdge;
import pl.edu.agh.simulation.intruders.api.impl.DoorNode;

public class SimpleMeasurer implements IMeasurer{
	
	List<Float> majorIntruderValues;
	
	List<Float> allIntruderValues;
	
	public SimpleMeasurer() {
		majorIntruderValues = new LinkedList<>();
		allIntruderValues = new LinkedList<>();
	}

	@Override
	public void measure(String fileName) throws IOException {
		// does nothing
	}

	@Override
	public void addMajorState(Building building) {
		majorIntruderValues.add(getTotalIntruderValue(building));
		allIntruderValues.add(getTotalIntruderValue(building));
	}

	@Override
	public void addMinorState(Building building) {
		allIntruderValues.add(getTotalIntruderValue(building));
	}
	
	private float getTotalIntruderValue (Building building) {
		float total = 0;
		for (DoorNode node : building.getDoorNodes()) {
			total += node.getProbability();
			for (DoorEdge edge : node.getEdges()) {
				Queue<Float> queue = edge.getIntruderQueue();
				for (float intruderVal : queue) {
					total += intruderVal;
				}
			}
		}
		return total;
	}
	
	@Override
	public float getFinalValue() {
		return allIntruderValues.get(allIntruderValues.size()-1);
	}
}

package pl.edu.agh.simulation.intruders.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;

public class SampleRobotsController extends AbstractRobotsController {
	
	@Override
	public void update() {
		Map<DoorNode, List<Robot>> robotsOnTheOtherSide = new HashMap<>();
		for (DoorNode node : doorNodes) {
				// get robots to distribute
			List<DoorEdge> edges = node.getEdges();
			List<Robot> robotsByTheDoor = new LinkedList<>();
			for (DoorEdge edge : edges) {
				if (edge.getDestination().equals(node) && !edge.getRobotsQueue().isEmpty()) {
					robotsByTheDoor.addAll(edge.getRobotsQueue().poll());
				}
			}
			robotsByTheDoor.addAll(node.getRobotsFromTheOtherSide());
				// prepare lists to be distributed
			List<DoorEdge> outEdges= new LinkedList<>();
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getSource().equals(node)) {
					outEdges.add(edge);
				}
			}
			List<List<Robot>> listsToDistribute = new LinkedList<>();
			for (int i=0;i<outEdges.size()+1;i++) {
				listsToDistribute.add(new LinkedList<>());
			}
			int i = 0;
			while (!robotsByTheDoor.isEmpty()) {
				listsToDistribute.get(i).add(robotsByTheDoor.remove(0));
				i = (i+1) % (outEdges.size()+1);
			}
			Collections.shuffle(listsToDistribute);
				// distribute lists
			for (DoorEdge edge : outEdges) {
				edge.getRobotsQueue().add(listsToDistribute.remove(0));
			}
			robotsOnTheOtherSide.put(node, listsToDistribute.remove(0));
		}
		// update other side
		for (DoorNode node : robotsOnTheOtherSide.keySet()) {
			node.setRobotsFromTheOtherSide(robotsOnTheOtherSide.get(node));
		}
	}

	@Override
	protected Map<String, List<Robot>> assignRoutesToRobots(DoorNode node, List<Robot> robotsByTheDoor) {
		// TODO Auto-generated method stub
		return null;
	}
}

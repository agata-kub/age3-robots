package pl.edu.agh.simulation.intruders.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.intruders.model.Room;
import pl.edu.agh.simulation.util.Commons;

public abstract class AbstractRobotsController implements RobotsController {
	
	protected List<DoorNode> doorNodes;
	
	protected List<Room> rooms;
	
	@Override
	public void init(List<DoorNode> doorNodes, List<Room> rooms) {
		this.doorNodes = doorNodes;
		this.rooms = rooms;
	}

	@Override
	public void reduceProbabilities(IConfig config) {
		for (Room room : rooms) {
//			System.out.println(room);
			for (DoorNode node : room.getDoorNodes()) {
				for (Robot robot : node.getRobots()) {
					for (DoorNode nodeToUpdate : room.getDoorNodes()) {
						for (DoorEdge edge : nodeToUpdate.getEdges()) {
							Queue<Float> newIntruderProb = new LinkedList<>();
							edge.getIntruderQueue().forEach(prob->newIntruderProb.add(config.getNewProbability(prob, robot)));
							edge.setIntrudersQueue(newIntruderProb);
						}
						nodeToUpdate.setProbability(config.getNewProbability(nodeToUpdate.getProbability(), robot));
					}
				}
			}
		}
	}
	
	@Override
	public void update() {
		for (DoorNode node : doorNodes) {
			// get robots to distribute
			List<Robot> robotsByTheDoor = getRobotsToDistribute(node);
			// for each available robot send him over appropriate path
			Map<String, List<Robot>> nameToRobots = assignRoutesToRobots(node, robotsByTheDoor);
			// distribute prepared robots
			distributeRobotsToAppropriateNodes(node, nameToRobots);
		}
	}
	
	protected abstract Map<String, List<Robot>> assignRoutesToRobots(DoorNode node, List<Robot> robotsByTheDoor);
	
	protected List<Robot> getRobotsToDistribute(DoorNode node) {
		List<DoorEdge> edges = node.getEdges();
		List<Robot> robotsByTheDoor = new LinkedList<>();
		for (DoorEdge edge : edges) {
			if (edge.getDestination().equals(node) && !edge.getRobotsQueue().isEmpty()) {
				robotsByTheDoor.addAll(edge.getRobotsQueue().poll());
			}
		}
		robotsByTheDoor.addAll(node.getRobotsFromTheOtherSide());
		return robotsByTheDoor;
	}
	
	protected void distributeRobotsToAppropriateNodes(DoorNode node, Map<String, List<Robot>> nameToRobots) {
		for (String name : nameToRobots.keySet()) {
			if (name.equals(Commons.THROUGH)) {
				node.setRobotsFromTheOtherSide(nameToRobots.get(name));
			} else {
				for (DoorEdge edge : node.getEdges()) {
					if (edge.getSource().equals(node) && edge.getDestination().getName().equals(name)) {
						edge.getRobotsQueue().add(nameToRobots.get(name));
					}
				}
			}
		}
	}
}

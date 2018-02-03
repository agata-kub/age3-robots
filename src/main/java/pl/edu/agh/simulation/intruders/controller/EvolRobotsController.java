package pl.edu.agh.simulation.intruders.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.intruders.model.Room;
import pl.edu.agh.simulation.util.Commons;

public class EvolRobotsController implements RobotsController {
	
	Map<String,List<String>> nametoRoutes;
	
	Map<String,Integer> nametoIndex;
	
	private List<DoorNode> doorNodes;
	
	private List<Room> rooms;
	
	private int routesCount;
	
	public EvolRobotsController(Map<String,List<String>> nametoRoutes, int routesCount) {
		this.nametoRoutes = nametoRoutes;
		this.nametoIndex = new HashMap<>();
		for (String name : nametoRoutes.keySet()) {
			nametoIndex.put(name, 0);
		}
		this.routesCount = routesCount;
	}

	@Override
	public void init(List<DoorNode> doorNodes, List<Room> rooms) {
		this.doorNodes = doorNodes;
		this.rooms = rooms;
	}

	@Override
	public void update() {
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
			// for each available robot send him over appropriate path
			Map<String,List<Robot>> nameToRobots = new HashMap<>();
			for (Robot r : robotsByTheDoor) {
				String name = node.getName();
//				System.out.println(nametoIndex);
//				System.out.println(name);
				int currentIndex = nametoIndex.get(name);
				String route = nametoRoutes.get(name).get(currentIndex);
				nametoIndex.put(name, (currentIndex+1)%routesCount);
				if (!nameToRobots.containsKey(route)) {
					nameToRobots.put(route, new LinkedList<>());
				}
				nameToRobots.get(route).add(r);
			}
			// distribute prepared robots
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

}

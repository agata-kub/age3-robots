package pl.edu.agh.simulation.intruders.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.util.Commons;

public class EvolRobotsController extends AbstractRobotsController {
	
	Map<String,List<String>> nametoRoutes;
	
	Map<String,Integer> nametoIndex;
	
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
}

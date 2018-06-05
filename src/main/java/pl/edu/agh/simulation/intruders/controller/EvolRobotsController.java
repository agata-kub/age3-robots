package pl.edu.agh.simulation.intruders.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;

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
	protected Map<String, List<Robot>> assignRoutesToRobots(DoorNode node, List<Robot> robotsByTheDoor) {
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
		return nameToRobots;
	}
}

package pl.edu.agh.simulation.intruders.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.intruders.model.Room;

public class BfsRobotsController extends AbstractRobotsController {

	private Map<Room, Integer> visitsCounter = new HashMap<>();

	@Override
	public void init(List<DoorNode> doorNodes, List<Room> rooms) {
		super.init(doorNodes, rooms);
		for (Room room : rooms) {
			visitsCounter.put(room, 0);
		}
	}

	@Override
	protected Map<String, List<Robot>> assignRoutesToRobots(DoorNode node, List<Robot> robotsByTheDoor) {
		Map<String, List<Robot>> nameToRobots = new HashMap<>();
		List<DoorNode> neighbors = node.getNeighbors();
		List<DoorNode> doorNodesSorted = neighbors.stream()
			.sorted((node1, node2) -> visitsCounter.get(node1.getRoom()) - visitsCounter.get(node2.getRoom()))
			.collect(Collectors.toList());
		int currentIndex = 0;
		for (Robot r : robotsByTheDoor) {
			DoorNode currentDoorNode = doorNodesSorted.get(currentIndex);
			String route = currentDoorNode.getName();
			visitsCounter.put(currentDoorNode.getRoom(), visitsCounter.get(currentDoorNode.getRoom()) + 1);
			if (!nameToRobots.containsKey(route)) {
				nameToRobots.put(route, new LinkedList<>());
			}
			currentIndex = (currentIndex+1)%doorNodesSorted.size();
			nameToRobots.get(route).add(r);
		}
		return nameToRobots;
	}

	// @Override
	// public void update() {
	// for robot in robots:
	// if on_path():
	// move_forward()
	// if reached_node():
	// node = get_nearest_unvisited(current_node)
	// if node == null:
	// node = get_random_neighbor() // ???
	// send_robot(robot, node)
	// }
	//
	// def get_next_bfs_node():

}

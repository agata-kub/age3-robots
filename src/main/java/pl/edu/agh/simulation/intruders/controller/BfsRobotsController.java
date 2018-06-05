package pl.edu.agh.simulation.intruders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		// TODO Auto-generated method stub
		return null;
	}
	
	

//	@Override
//	public void update() {
//		for robot in robots:
//			if on_path():
//				move_forward()
//			if reached_node():
//				node = get_nearest_unvisited(current_node)
//				if node == null:
//					node = get_random_neighbor() // ???
//			    send_robot(robot, node)
//	}
//	
//	def get_next_bfs_node():
		
}

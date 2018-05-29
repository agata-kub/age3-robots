package pl.edu.agh.simulation.intruders.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.intruders.model.Room;

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
}

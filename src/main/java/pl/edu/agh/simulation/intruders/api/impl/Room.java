package pl.edu.agh.simulation.intruders.api.impl;

import java.util.LinkedList;
import java.util.List;

public class Room {
	
	List<DoorNode> doorNodes;
	
	public Room() {
		doorNodes = new LinkedList<>();
	}

	public List<DoorNode> getDoorNodes() {
		return doorNodes;
	}

	public void addNode(DoorNode node) {
		doorNodes.add(node);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("$$$$$\n");
		for (DoorNode node : doorNodes) {
			sb.append(node.getName()+" ");
		}
		return sb.toString();
	}

}

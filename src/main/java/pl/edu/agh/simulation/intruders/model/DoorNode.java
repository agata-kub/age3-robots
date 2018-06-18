package pl.edu.agh.simulation.intruders.model;

import java.util.LinkedList;
import java.util.List;

public class DoorNode {
	
	private List<DoorEdge> edges;
	
	float probability;
	
	private DoorNode otherSide;
	
	private String name;
	
	private List<Robot> robotsFromTheOtherSide;
	
	private Room room;
	
	public DoorNode() {
		edges = new LinkedList<>();
		robotsFromTheOtherSide = new LinkedList<>();
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public float getProbability() {
		return probability;
	}
	
	public void setProbability(float prob) {
		this.probability = prob;
	}

	public Robot[] getRobots() {
		List<Robot> robots = new LinkedList<>();
		for (DoorEdge edge : getEdges()) {
			if (edge.getSource().equals(this)) {
				for (List<Robot> rs : edge.getRobotsQueue()) {
					robots.addAll(rs);
				}
			}
		}
		robots.addAll(robotsFromTheOtherSide);
		return robots.toArray(new Robot[robots.size()]);
	}
	
	public List<DoorNode> getNeighbors() {
		List<DoorNode> neighbors = new LinkedList<>();
		for (DoorEdge edge : getEdges()) {
			if (edge.getSource().equals(this)) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	public DoorNode getTheOtherSide() {
		return otherSide;
	}

	public List<DoorEdge> getEdges() {
		return edges;
	}

	public void move(Robot robot, DoorEdge edge) {
		// TODO Auto-generated method stub

	}

	public void passThrough(Robot robot) {
		// TODO Auto-generated method stub

	}

	public List<DoorNode> getOtherDoors() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEdge(DoorEdge edge) {
		edges.add(edge);
	}

	public void setTheOtherSide(DoorNode node) {
		this.otherSide = node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Node "+getName()+"\n");
		sb.append("Edges:\n");
		for (DoorEdge edge : edges) {
			sb.append("\t"+edge.toString());
		}
		sb.append("probability: "+probability+"\n");
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Robot> getRobotsFromTheOtherSide() {
		return robotsFromTheOtherSide;
	}

	public void setRobotsFromTheOtherSide(List<Robot> robotsFromTheOtherSide) {
		this.robotsFromTheOtherSide = robotsFromTheOtherSide;
	}
}

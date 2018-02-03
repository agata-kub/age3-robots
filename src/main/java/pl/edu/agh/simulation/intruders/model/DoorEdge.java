package pl.edu.agh.simulation.intruders.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DoorEdge {
	
	private DoorNode source;
	
	private DoorNode destination;
	
	private int length;
	
	private Queue<Float> intruderQueue;
	
	private Queue<List<Robot>> robotsQueue;
	
	public DoorEdge(DoorNode src, DoorNode dest) {
		source = src;
		destination = dest;
		intruderQueue = new LinkedList<>();
	}

	public DoorNode getSource() {
		return source;
	}

	public DoorNode getDestination() {
		return destination;
	}

	public void setSource(DoorNode source) {
		this.source = source;
	}

	public void setDestination(DoorNode destination) {
		this.destination = destination;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}

	public Queue<Float> getIntruderQueue() {
		return intruderQueue;
	}

	public Queue<List<Robot>> getRobotsQueue() {
		return robotsQueue;
	}

	public void setIntrudersQueue(Queue<Float> queue) {
		this.intruderQueue = queue;
	}

	public void setRobotsQueue(Queue<List<Robot>> queue) {
		this.robotsQueue = queue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Edge "+getName()+": ");
		for (Float i : intruderQueue) {
			sb.append(i+", ");
		}
		sb.append(" ** ");
		for (List<Robot> s : robotsQueue) {
			for (Robot r : s) {
				sb.append(r == null ? "null" : "robot");
				sb.append(", ");
			}
			sb.append(" | ");
		}
		sb.append(" ( "+getLength()+" ) ");
		sb.append("\n");
		return sb.toString();
	}

	public String getName() {
		return source.getName()+"->"+destination.getName();
	}
}

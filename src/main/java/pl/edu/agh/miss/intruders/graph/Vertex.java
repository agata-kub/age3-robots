package pl.edu.agh.miss.intruders.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Vertex {
	
	private String name;
	
	private List<Vertex> neighbors;
	
	public Vertex(String name) {
		neighbors = new LinkedList<>();
		this.name = name;
	}
	
	public Vertex() {
		this(UUID.randomUUID().toString());
	}
	
	public String getName() {
		return name;
	}
	
	public List<Vertex> getNeighbors() {
		return neighbors;
	}
	
	public void addNeighbor(Vertex v) {
		neighbors.add(v);
	}
}

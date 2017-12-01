package pl.edu.agh.simulation.intruders.graph;

import java.util.LinkedList;
import java.util.List;

public class Graph {
	
	private List<Vertex> vertices;
	
	public Graph() {
		vertices = new LinkedList<>();
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}
	
	public void addVertex(Vertex v) {
		vertices.add(v);
	}
	
	public Vertex getVertex(String name) {
		for (Vertex v : vertices) {
			if (name.equals(v.getName())) {
				return v;
			}
		}
		return null;
	}
}

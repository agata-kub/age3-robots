package pl.edu.agh.age.robot.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.simulation.intruders.graph.Graph;
import pl.edu.agh.simulation.intruders.graph.Vertex;
import pl.edu.agh.simulation.util.Commons;

public class SolutionCreator {
	
	private BuildingProvider buildingProvider;
	
	public SolutionCreator(BuildingProvider buildingProvider) {
		this.buildingProvider = buildingProvider;
	}
	
	public HashMap<String, List<String>> createSolution(int routesCount) {
		Random rand = ThreadLocalRandom.current();
		Graph graph = buildingProvider.getGraph();
		HashMap<String, List<String>> result = new HashMap<>();
		for (Vertex vertex : graph.getVertices()) {
			createSolutionForVertex(routesCount, rand, result, vertex);
		}
		return result;
	}

	private void createSolutionForVertex(int routesCount, Random rand, Map<String, List<String>> result,
			Vertex vertex) {
		result.put(vertex.getName(), new LinkedList<>());
		List<Vertex> neighbors = vertex.getNeighbors();
		for (int i = 0; i < routesCount; i++) {
			createRouteEntry(rand, result, vertex, neighbors);
		}
	}

	private void createRouteEntry(Random rand, Map<String, List<String>> result, Vertex vertex,
			List<Vertex> neighbors) {
		int index = rand.nextInt(neighbors.size() + 1);
		String route = null;
		if (index == neighbors.size()) {
			route = Commons.THROUGH;
		} else {
			route = neighbors.get(index).getName();
		}
		result.get(vertex.getName()).add(route);
	}

}

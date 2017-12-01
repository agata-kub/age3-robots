package pl.edu.agh.age.robot.evol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.simulation.intruders.graph.Graph;
import pl.edu.agh.simulation.intruders.graph.Vertex;
import pl.edu.agh.simulation.util.Commons;

public class RobotFactory {

	private EvaluatorCounter evaluatorCounter;

	private BuildingProvider buildingProvider;

	public RobotFactory(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider) {
		this.evaluatorCounter = evaluatorCounter;
		this.buildingProvider = buildingProvider;
	}

	public RobotSolution create() {
		RobotEvaluator evaluator = new RobotEvaluator(evaluatorCounter, buildingProvider);
		Map<String, List<String>> nametoRoutes = createSolution(Commons.ROUTES_COUNT);
		RobotSolution solution = new RobotSolution(nametoRoutes);
		return solution.withFitness(evaluator.evaluate(solution));
	}

	private Map<String, List<String>> createSolution(int routesCount) {
		Random rand = ThreadLocalRandom.current();
		Graph graph = buildingProvider.getGraph();
		Map<String, List<String>> result = new HashMap<>();
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

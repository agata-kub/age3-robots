package pl.edu.agh.age.robot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.miss.intruders.graph.Graph;
import pl.edu.agh.miss.intruders.graph.Vertex;
import pl.edu.agh.miss.util.ConvertUtils;

public class RobotFactory {
	
	private EvaluatorCounter evaluatorCounter;
	
	public RobotFactory(EvaluatorCounter evaluatorCounter) {
		this.evaluatorCounter = evaluatorCounter;
	}

	public RobotSolution create() {
		RobotEvaluator evaluator = new RobotEvaluator(evaluatorCounter);
		Map<String, List<String>> nametoRoutes = createSolution(ConvertUtils.ROUTES_COUNT);
		RobotSolution solution = new RobotSolution(nametoRoutes);
		return solution.updateFitness(evaluator.evaluate(solution));
	}

	private Map<String, List<String>> createSolution(int routesCount) {
		Random rand = ThreadLocalRandom.current();
		Graph graph = ConvertUtils.getGraph();
		Map<String, List<String>> result = new HashMap<>();
		for (Vertex v : graph.getVertices()) {
			result.put(v.getName(), new LinkedList<>());
			List<Vertex> neighbors = v.getNeighbors();
			for (int i=0;i<routesCount;i++) {
				int index = rand.nextInt(neighbors.size()+1);
				String route = null;
				if (index==neighbors.size()) {
					route = ConvertUtils.THROUGH;
				} else {
					route = neighbors.get(index).getName();
				}
				result.get(v.getName()).add(route);
			}
		}
		return result;
	}
}

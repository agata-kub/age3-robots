package pl.edu.agh.miss.brute;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import pl.edu.agh.age.common.BuildingProvider;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.RobotEvaluator;
import pl.edu.agh.age.robot.RobotSolution;
import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.graph.Graph;
import pl.edu.agh.miss.intruders.graph.Vertex;
import pl.edu.agh.miss.util.Commons;

public class BruteSolver {

	private Building building;
	private Graph graph;
	private BuildingProvider buildingProvider;
	
	public BruteSolver() {
		buildingProvider = new BuildingProvider("dupa");
		building = buildingProvider.getBuilding();
		graph = buildingProvider.getGraph();
	}

	public static void main(String[] args) {
		BruteSolver solver = new BruteSolver();
		List<RobotSolution> solutions = solver.generateAllPossibleSolutions(Commons.ROUTES_COUNT);
		System.out.println(solutions);
	}

	public void solve() {
		List<RobotSolution> allSolutions = generateAllPossibleSolutions(Commons.ROUTES_COUNT);
		double bestValue = Double.MAX_VALUE;
		RobotSolution bestSolution = null;
		for (RobotSolution solution : allSolutions) {
			double current = testSolution(solution);
			if (current < bestValue) {
				bestValue = current;
				bestSolution = solution;
			}
		}
		System.out.println("Best solution: " + bestSolution);
		System.out.println("Best value: " + bestValue);
	}

	private List<RobotSolution> generateAllPossibleSolutions(int routesCount) {
		List<RobotSolution> result = new LinkedList<>();
		// prepare a map of vertex-list of all ROUTES_COUNT-permutations of the neighbors
		List<Set<List<String>>> vertexToPossibleRouteLists = new LinkedList<>();
		for (Vertex v : graph.getVertices()) {
			vertexToPossibleRouteLists.add(getAllPossibleRoutesForVertex(v));
		}
		// generate solution
		Set<List<List<String>>> all = Sets.cartesianProduct(vertexToPossibleRouteLists);
		for (Iterator<List<List<String>>> it = all.iterator(); it.hasNext(); ) {
			List<List<String>> solution = it.next();
			Map<String, List<String>> nameToRoutes = new LinkedHashMap<>();
			for (int i=0;i<graph.getVertices().size();i++) {
				Vertex v = graph.getVertices().get(i);
				List<String> solutionPart = solution.get(i);
				nameToRoutes.put(v.getName(), solutionPart);
			}
			result.add(new RobotSolution(nameToRoutes));
		}
		return result;
	}

	private Set<List<String>> getAllPossibleRoutesForVertex(Vertex v) {
		List<Set<String>> all = new LinkedList<>();
		Set<String> neighborNames = v.getNeighbors().stream().map((n) -> n.getName()).collect(Collectors.toSet());
		for (int i = 0; i < Commons.ROUTES_COUNT; i++) {
			all.add(neighborNames);
		}
		return Sets.cartesianProduct(all);
	}

	private double testSolution(RobotSolution solution) {
		RobotEvaluator evaluator = new RobotEvaluator(EvaluatorCounter.empty(), buildingProvider);
		return evaluator.evaluate(solution);
	}

}

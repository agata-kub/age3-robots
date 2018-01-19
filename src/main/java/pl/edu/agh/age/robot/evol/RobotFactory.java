package pl.edu.agh.age.robot.evol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionCreator;
import pl.edu.agh.simulation.intruders.graph.Graph;
import pl.edu.agh.simulation.intruders.graph.Vertex;
import pl.edu.agh.simulation.util.Commons;

public class RobotFactory {

	private EvaluatorCounter evaluatorCounter;

	private BuildingProvider buildingProvider;
	
	private SolutionCreator solutionCreator;

	public RobotFactory(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider) {
		this.evaluatorCounter = evaluatorCounter;
		this.buildingProvider = buildingProvider;
		this.solutionCreator = new SolutionCreator(buildingProvider);
	}

	public RobotSolution create() {
		RobotEvaluator evaluator = new RobotEvaluator(evaluatorCounter, buildingProvider);
		Map<String, List<String>> nametoRoutes = solutionCreator.createSolution(Commons.ROUTES_COUNT);
		RobotSolution solution = new RobotSolution(nametoRoutes);
		return solution.withFitness(evaluator.evaluate(solution));
	}


}

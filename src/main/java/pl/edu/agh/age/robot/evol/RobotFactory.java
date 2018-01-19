package pl.edu.agh.age.robot.evol;

import java.util.List;
import java.util.Map;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionCreator;

public class RobotFactory {

	private EvaluatorCounter evaluatorCounter;

	private BuildingProvider buildingProvider;
	
	private SolutionCreator solutionCreator;
	
	private int iterations;
	
	private int timeUnits;
	
	private int routesCount;

	public RobotFactory(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider, int iterations, int timeUnits, int routesCount) {
		this.evaluatorCounter = evaluatorCounter;
		this.buildingProvider = buildingProvider;
		this.solutionCreator = new SolutionCreator(buildingProvider);
		this.iterations = iterations;
		this.timeUnits = timeUnits;
		this.routesCount = routesCount;
	}

	public RobotSolution create() {
		RobotEvaluator evaluator = new RobotEvaluator(evaluatorCounter, buildingProvider, iterations, timeUnits, routesCount);
		Map<String, List<String>> nametoRoutes = solutionCreator.createSolution(routesCount);
		RobotSolution solution = new RobotSolution(nametoRoutes);
		return solution.withFitness(evaluator.evaluate(solution));
	}


}

package pl.edu.agh.age.robot.evol;

import pl.edu.agh.age.compute.stream.problem.Evaluator;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionEvaluator;

public class RobotEvaluator implements Evaluator<RobotSolution> {
	
	private SolutionEvaluator solutionEvaluator;
	
	public RobotEvaluator(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider) {
		this.solutionEvaluator = new SolutionEvaluator(evaluatorCounter, buildingProvider);
	}

	@Override
	public double evaluate(RobotSolution value) {
		return solutionEvaluator.evaluate(value.unwrap());
	}
}

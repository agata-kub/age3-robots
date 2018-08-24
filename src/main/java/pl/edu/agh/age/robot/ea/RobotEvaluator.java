package pl.edu.agh.age.robot.ea;

import pl.edu.agh.age.compute.ea.evaluation.Evaluator;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionEvaluator;

public class RobotEvaluator implements Evaluator<RobotSolution>{

	private static final long serialVersionUID = 7582330561216268202L;

	private SolutionEvaluator solutionEvaluator;
	
	public RobotEvaluator(EvaluatorCounter evaluatorCounter,BuildingProvider buildingProvider, int iterations, int timeUnits, int routesCount) {
		this.solutionEvaluator = new SolutionEvaluator(evaluatorCounter, buildingProvider, iterations, timeUnits, routesCount);
	}

	@Override
	public double evaluate(RobotSolution value) {
		return -solutionEvaluator.evaluate(value.unwrap());
	}
}

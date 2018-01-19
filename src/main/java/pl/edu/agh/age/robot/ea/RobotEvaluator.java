package pl.edu.agh.age.robot.ea;

import pl.edu.agh.age.compute.ea.evaluation.Evaluator;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionEvaluator;

public class RobotEvaluator implements Evaluator<RobotSolution>{

	private static final long serialVersionUID = 7582330561216268202L;

	private SolutionEvaluator solutionEvaluator;
	
	public RobotEvaluator(BuildingProvider buildingProvider) {
		this.solutionEvaluator = new SolutionEvaluator(null, buildingProvider);
	}

	@Override
	public double evaluate(RobotSolution value) {
		return -solutionEvaluator.evaluate(value.unwrap());
	}
}

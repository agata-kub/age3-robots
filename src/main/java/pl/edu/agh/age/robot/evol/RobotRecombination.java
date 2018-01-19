package pl.edu.agh.age.robot.evol;

import java.util.List;
import java.util.Map;

import pl.edu.agh.age.compute.stream.emas.reproduction.recombination.Recombination;
import pl.edu.agh.age.robot.common.SolutionRecombinator;

public class RobotRecombination implements Recombination<RobotSolution> {
	
	private SolutionRecombinator recombinator = new SolutionRecombinator();

	@Override
	public RobotSolution recombine(RobotSolution firstSolution, RobotSolution secondSolution) {
		Map<String, List<String>> solution1 = firstSolution.unwrap();
		Map<String, List<String>> solution2 = secondSolution.unwrap();
		return new RobotSolution(recombinator.recombineSolutions(solution1, solution2)._1());
	}
}

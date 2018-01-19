package pl.edu.agh.age.robot.ea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vavr.Tuple2;
import pl.edu.agh.age.compute.ea.variation.recombination.Recombination;
import pl.edu.agh.age.robot.common.SolutionRecombinator;

public class RobotRecombination implements Recombination<RobotSolution> {

	private static final long serialVersionUID = -2704210845938603463L;

	private SolutionRecombinator recombinator = new SolutionRecombinator();

	@Override
	public Tuple2<RobotSolution, RobotSolution> recombine(RobotSolution firstSolution, RobotSolution secondSolution) {
		Map<String, List<String>> solution1 = firstSolution.unwrap();
		Map<String, List<String>> solution2 = secondSolution.unwrap();
		Tuple2<HashMap<String, List<String>>,HashMap<String, List<String>>> recombined = recombinator.recombineSolutions(solution1, solution2);
		return new Tuple2<RobotSolution, RobotSolution>(new RobotSolution(recombined._1()), new RobotSolution(recombined._2()));
	}

}

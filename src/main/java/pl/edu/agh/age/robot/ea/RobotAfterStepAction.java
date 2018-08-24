package pl.edu.agh.age.robot.ea;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import pl.edu.agh.age.compute.ea.AfterStepAction;
import pl.edu.agh.age.compute.ea.StatisticsKeys;
import pl.edu.agh.age.compute.ea.solution.Solution;

public class RobotAfterStepAction implements AfterStepAction<RobotSolution, StatisticsKeys>{

	private static final long serialVersionUID = 3761524034009468706L;
	
	private StatisticsWriter statisticsWriter;
	
	public RobotAfterStepAction(StatisticsWriter statisticsWriter) {
		this.statisticsWriter = statisticsWriter;
	}
	
	@Override
	public Map<StatisticsKeys, Object> apply(final Long workplaceId, final Long step,
	                                         final List<RobotSolution> population) {
		final RobotSolution bestSolution = computeBestSolution(population).getOrElseThrow(
			() -> new AssertionError("Best solution should not be empty"));
		Map<StatisticsKeys, Object> res =  HashMap.of(StatisticsKeys.STEP_NUMBER, step,
		                  StatisticsKeys.CURRENT_BEST, bestSolution);
		statisticsWriter.writeStats(res);
		return res;
	}


	// FIXME: Utility class?
	private static Option<RobotSolution> computeBestSolution(final List<RobotSolution> population) {
		return population.maxBy(Solution::evaluationValue);
	}

}

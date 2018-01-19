package pl.edu.agh.age.robot.ea;

import io.vavr.collection.List;
import pl.edu.agh.age.compute.ea.preselection.Preselection;

public class RobotPreselection implements Preselection<RobotSolution>{

	private static final long serialVersionUID = -6286560462387741814L;

	@Override
	public List<RobotSolution> preselect(List<RobotSolution> population) {
		List<RobotSolution> sorted = population.sortBy(RobotSolution::evaluationValue);
		List<RobotSolution> result = List.empty();
		return result
				.appendAll(sorted.subSequence(sorted.length()/2, sorted.length()))
				.appendAll(sorted.subSequence(sorted.length()/2, sorted.length()));
			    
			    
	}
}

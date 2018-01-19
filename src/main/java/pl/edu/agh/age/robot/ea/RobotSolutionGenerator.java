package pl.edu.agh.age.robot.ea;

import io.vavr.Function0;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionCreator;
import pl.edu.agh.simulation.util.Commons;

public class RobotSolutionGenerator implements Function0<RobotSolution>{
	
	private static final long serialVersionUID = 2022490551103299430L;

	private SolutionCreator solutionCreator;
	
	public RobotSolutionGenerator(BuildingProvider buildingProvider) {
		this.solutionCreator = new SolutionCreator(buildingProvider);
	}

	@Override
	public RobotSolution apply() {
		return new RobotSolution(solutionCreator.createSolution(Commons.ROUTES_COUNT));
	}

}

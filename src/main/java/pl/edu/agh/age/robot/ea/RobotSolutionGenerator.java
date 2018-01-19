package pl.edu.agh.age.robot.ea;

import io.vavr.Function0;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionCreator;

public class RobotSolutionGenerator implements Function0<RobotSolution>{
	
	private static final long serialVersionUID = 2022490551103299430L;

	private SolutionCreator solutionCreator;
	
	private int routesCount;
	
	public RobotSolutionGenerator(BuildingProvider buildingProvider, int routesCount) {
		this.solutionCreator = new SolutionCreator(buildingProvider);
		this.routesCount = routesCount;
	}

	@Override
	public RobotSolution apply() {
		return new RobotSolution(solutionCreator.createSolution(routesCount));
	}

}

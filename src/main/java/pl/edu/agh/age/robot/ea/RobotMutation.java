package pl.edu.agh.age.robot.ea;

import pl.edu.agh.age.compute.ea.variation.mutation.Mutation;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionMutator;

public class RobotMutation implements Mutation<RobotSolution>{

	private static final long serialVersionUID = 8126064770151608901L;

	private SolutionMutator mutator;
	
	public RobotMutation(BuildingProvider buildingProvider) {
		this.mutator = new SolutionMutator(buildingProvider);
	}

	@Override
	public RobotSolution mutate(RobotSolution solution) {
		return new RobotSolution(mutator.mutateSolution(solution.unwrap(), 1, 1));
	}

}

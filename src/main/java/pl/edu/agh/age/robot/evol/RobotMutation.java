package pl.edu.agh.age.robot.evol;

import pl.edu.agh.age.compute.stream.emas.reproduction.mutation.Mutation;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.age.robot.common.SolutionMutator;

public class RobotMutation implements Mutation<RobotSolution> {
	
	private SolutionMutator mutator;
	
	public RobotMutation(BuildingProvider buildingProvider) {
		this.mutator = new SolutionMutator(buildingProvider);
	}

	@Override
	public RobotSolution mutate(RobotSolution solution) {
		return new RobotSolution(mutator.mutateSolution(solution.unwrap(), 1, 1));
	}
}

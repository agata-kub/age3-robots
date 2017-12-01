package pl.edu.agh.age.robot.evol;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import io.vavr.collection.Stream;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.age.compute.stream.emas.PopulationGenerator;

public class RobotPopulationGenerator implements PopulationGenerator<EmasAgent> {
	
	private final RobotFactory solutionFactory;

	private final int agentsCount;

	private final double initialAgentEnergy;

	public RobotPopulationGenerator(final RobotFactory solutionFactory, final int agentsCount,
	                                final double initialAgentEnergy) {
		requireNonNull(solutionFactory);
		checkArgument(agentsCount > 0);
		checkArgument(initialAgentEnergy > 0);
		this.solutionFactory = solutionFactory;
		this.agentsCount = agentsCount;
		this.initialAgentEnergy = initialAgentEnergy;
	}

	@Override public List<EmasAgent> createPopulation() {
		return Stream.range(0, agentsCount).toJavaStream()
                     .map(i -> createAgent())
                     .collect(Collectors.toList());
	}

	private EmasAgent createAgent() {
		final RobotSolution solution = solutionFactory.create();
		return EmasAgent.create(initialAgentEnergy, solution);
	}
}

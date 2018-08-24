package pl.edu.agh.age.robot.ea;

import pl.edu.agh.age.compute.ea.Manager;
import pl.edu.agh.age.compute.ea.StopCondition;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;

public class FitnessInvocationsStopCondition implements StopCondition {
	
	private final EvaluatorCounter counter;

	private final int desiredFitnessInvocations;

	public FitnessInvocationsStopCondition(EvaluatorCounter counter, final int desiredFitnessInvocations) {
		this.desiredFitnessInvocations = desiredFitnessInvocations;
		this.counter = counter;
	}

	@Override public String toString() {
		return "FitnessInvocationsStopCondition: desiredFitnessInvocations="+desiredFitnessInvocations;
	}

	@Override
	public boolean isReached(Manager manager) {
		return counter.get() >= desiredFitnessInvocations;
	}
}

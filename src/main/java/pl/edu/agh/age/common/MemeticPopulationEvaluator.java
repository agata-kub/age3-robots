package pl.edu.agh.age.common;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import io.vavr.collection.Seq;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.age.compute.stream.emas.PopulationEvaluator;
import pl.edu.agh.age.compute.stream.emas.reproduction.improvement.Improvement;
import pl.edu.agh.age.compute.stream.emas.solution.Solution;
import pl.edu.agh.age.compute.stream.problem.Evaluator;

public class MemeticPopulationEvaluator<S extends Solution<?>> implements PopulationEvaluator<EmasAgent> {

	private final Evaluator<S> evaluator;

	private final Improvement<S> improvement;

	public MemeticPopulationEvaluator(final Evaluator<S> evaluator, final Optional<Improvement<S>> improvement) {
		this.evaluator = requireNonNull(evaluator, "Evaluator has not been defined");
		this.improvement = improvement.orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Seq<EmasAgent> evaluate(final Seq<EmasAgent> population) {
		return population.map(agent -> {
			final S solution = (S)agent.solution;
			solution.withFitness(evaluator.evaluate(solution));
			return EmasAgent.create(agent.energy, improvement != null ? improvement.improve(solution) : solution);
		});
	}


}

package pl.edu.agh.age.common;

import static java.util.Objects.requireNonNull;

import pl.edu.agh.age.compute.stream.Environment;
import pl.edu.agh.age.compute.stream.Step;
import pl.edu.agh.age.compute.stream.emas.EmasAgent;
import pl.edu.agh.age.compute.stream.emas.PairPipeline;
import pl.edu.agh.age.compute.stream.emas.Pipeline;
import pl.edu.agh.age.compute.stream.emas.PopulationEvaluator;
import pl.edu.agh.age.compute.stream.emas.Predicates;
import pl.edu.agh.age.compute.stream.emas.Selectors;
import pl.edu.agh.age.compute.stream.emas.fight.Fight;
import pl.edu.agh.age.compute.stream.emas.fight.transfer.FightEnergyTransfer;
import pl.edu.agh.age.compute.stream.emas.migration.MigrationParameters;
import pl.edu.agh.age.compute.stream.emas.reproduction.AsexualReproduction;
import pl.edu.agh.age.compute.stream.emas.reproduction.AsexualReproductionBuilder;
import pl.edu.agh.age.compute.stream.emas.reproduction.SexualReproduction;
import pl.edu.agh.age.compute.stream.emas.reproduction.SexualReproductionBuilder;
import pl.edu.agh.age.compute.stream.emas.reproduction.mutation.Mutation;
import pl.edu.agh.age.compute.stream.emas.reproduction.recombination.Recombination;
import pl.edu.agh.age.compute.stream.emas.reproduction.transfer.AsexualEnergyTransfer;
import pl.edu.agh.age.compute.stream.emas.reproduction.transfer.EnergyTransfer;
import pl.edu.agh.age.compute.stream.emas.solution.Solution;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import javaslang.Tuple2;
import javaslang.collection.List;

/**
 * The class EmasStep implementing a default step for EMAS Agents based problems.<br />
 * The following operators are required:
 * <ul>
 * <li>Recombination</li>
 * <li>Energy Transfer</li>
 * <li>PopulationEvaluator</li>
 * </ul>
 * The following operators are optional:
 * <ul>
 * <li>Mutation</li>
 * </ul>
 *
 * @param <S>
 *        the problem solution class
 */
public class EmasStep<S extends Solution<?>> implements Step<EmasAgent> {

	private final Comparator<EmasAgent> fightAgentsComparator;

	private final FightEnergyTransfer fightEnergyTransfer;

	private final Fight fight;

	private final Recombination<S> recombination;

	private final Optional<Mutation<S>> mutation;

	private final PopulationEvaluator<EmasAgent> populationEvaluator;

	private final Predicate<EmasAgent> deathPredicate;

	private final Predicate<EmasAgent> reproductionPredicate;

	private final EnergyTransfer sexualReproductionTransfer;

	private final SexualReproduction sexualReproduction;

	private final AsexualEnergyTransfer asexualReproductionTransfer;

	private final AsexualReproduction asexualReproduction;

	private final MigrationParameters migrationParameters;

	private final Predicate<EmasAgent> migrationPredicate;

	static {
		Locale.setDefault(Locale.US); // set default 'dot' decimal separator
	}

	protected EmasStep(final Comparator<EmasAgent> fightAgentsComparator, final FightEnergyTransfer fightEnergyTransfer,
	                   final Recombination<S> recombination, final Optional<Mutation<S>> mutation,
	                   final PopulationEvaluator<EmasAgent> populationEvaluator, final Predicate<EmasAgent> deathPredicate,
	                   final Predicate<EmasAgent> reproductionPredicate, final EnergyTransfer sexualReproductionTransfer,
	                   final AsexualEnergyTransfer asexualReproductionTransfer, final MigrationParameters migrationParameters) {
		this.fightAgentsComparator = requireNonNull(fightAgentsComparator,
		    "Fight agents comparator has not been defined");
		this.fightEnergyTransfer = requireNonNull(fightEnergyTransfer, "Fight energy transfer has not been defined");
		this.fight = resolveFight();
		this.recombination = requireNonNull(recombination, "Recombination has not been defined");
		this.mutation = mutation;
		this.populationEvaluator = requireNonNull(populationEvaluator, "Population evaluator has not been defined");
		this.deathPredicate = deathPredicate;
		this.reproductionPredicate = requireNonNull(reproductionPredicate,
		    "Reproduction predicate has not been defined");
		this.sexualReproductionTransfer = requireNonNull(sexualReproductionTransfer,
		    "Sexual reproduction energy transfer has not been defined");
		this.sexualReproduction = resolveSexualReproduction();
		this.asexualReproductionTransfer = requireNonNull(asexualReproductionTransfer,
		    "Asexual reproduction energy transfer has not been defined");
		this.asexualReproduction = resolveAsexualReproduction();
		this.migrationParameters = requireNonNull(migrationParameters);
		this.migrationPredicate = Predicates.random(migrationParameters.migrationProbability());
	}

	@Override public List<EmasAgent> stepOn(final long stepNumber, final List<EmasAgent> population,
	                                        final Environment environment) {
		// select pairs
		final Tuple2<PairPipeline, Pipeline> pipelines = Pipeline.on(population).selectPairs(Selectors.random());

		// encounter paired agents -> reproduce or fight depending on their fitness value
		final Tuple2<Pipeline, Pipeline> reproduced = pipelines._1.encounter(reproductionPredicate, sexualReproduction, fight);

		// self reproduce agents which have not been paired
		final Tuple2<Pipeline, Pipeline> selfReproduced = pipelines._2.selfReproduce(reproductionPredicate, asexualReproduction);

		// merge parent and child agents
		final Pipeline parentAgents = reproduced._1.mergeWith(selfReproduced._1);
		final Pipeline childAgents = reproduced._2().mergeWith(selfReproduced._2);

		// evaluate and improve new agents
		final Pipeline evaluatedAgents = childAgents.evaluate(populationEvaluator);

		// merge all agents
		final Pipeline pipeline = parentAgents.mergeWith(evaluatedAgents);

		// die
		final Tuple2<Pipeline, Pipeline> afterDeath = pipeline.dieWhen(deathPredicate);
		final List<EmasAgent> dead = afterDeath._1.extract();
		environment.logPopulation("dead", dead);

		// migrate if necessary
		if (shouldMigrate(stepNumber)) {
			final Tuple2<Pipeline, Pipeline> afterMigration = pipeline.migrateWhen(migrationPredicate);
			final List<EmasAgent> migrated = afterMigration._1.extract();
			migrated.forEach(emasAgent -> environment.migrate(emasAgent, environment.neighbours().get()._1));
			return afterMigration._2.extract();
		}

		return afterDeath._2().extract();
	}

	private Fight resolveFight() {
		return Fight.<S>builder() //
		            .withComparator(fightAgentsComparator) //
		            .withEnergyTransfer(fightEnergyTransfer) //
		            .build(); //
	}

	private SexualReproduction resolveSexualReproduction() {
		SexualReproductionBuilder<S> reproductionBuilder = SexualReproduction.<S>builder() //
		                                                                     .withRecombination(recombination) //
		                                                                     .withEnergyTransfer(sexualReproductionTransfer);
		if (mutation.isPresent()) {
			reproductionBuilder = reproductionBuilder.withMutation(mutation.get());
		}
		return reproductionBuilder.build();
	}

	private AsexualReproduction resolveAsexualReproduction() {
		AsexualReproductionBuilder<S> reproductionBuilder = AsexualReproduction.<S>builder() //
		                                                                       .withEnergyTransfer(asexualReproductionTransfer); //
		if (mutation.isPresent()) {
			reproductionBuilder = reproductionBuilder.withMutation(mutation.get());
		}
		return reproductionBuilder.build();
	}

	private boolean shouldMigrate(final long currentStepNumber) {
		final long stepInterval = migrationParameters.stepInterval();
		return stepInterval != 0 ? currentStepNumber % stepInterval == 0 : false;
	}

}

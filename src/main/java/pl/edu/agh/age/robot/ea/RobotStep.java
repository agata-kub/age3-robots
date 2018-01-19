package pl.edu.agh.age.robot.ea;

import java.util.HashMap;

import io.vavr.collection.List;
import pl.edu.agh.age.compute.ea.GeneticPipeline;
import pl.edu.agh.age.compute.ea.Step;
import pl.edu.agh.age.compute.ea.preselection.RankPreselection;
import pl.edu.agh.age.compute.ea.rand.JavaRandomGenerator;
import pl.edu.agh.age.compute.ea.scaling.SimpleScaling;
import pl.edu.agh.age.robot.common.BuildingProvider;

public class RobotStep implements Step<RobotSolution>{
	
	private RobotEvaluator evaluator;
	
	private BuildingProvider buildingProvider;
	
	private final JavaRandomGenerator generator;

	private final double chanceToRecombine;

	private final double chanceToMutate;

	private final double mutationRange;
	
	public RobotStep(RobotEvaluator evaluator, BuildingProvider buildingProvider) {
		this.evaluator = evaluator;
		this.buildingProvider = buildingProvider;
		generator = new JavaRandomGenerator();
		chanceToRecombine = 1.0;
		chanceToMutate = 0.5;
		mutationRange = 0.01;
	}

	@Override
	public List<RobotSolution> stepOn(long stepNumber, List<RobotSolution> population) {
		
		final SimpleScaling scaling = new SimpleScaling();
		final RobotPreselection robotPreselection = new RobotPreselection();
		final RankPreselection<HashMap<String,java.util.List<String>>, RobotSolution> rankPreselection = new RankPreselection<>(robotPreselection);

		GeneticPipeline<HashMap<String, java.util.List<String>>, RobotSolution> pipeline = GeneticPipeline
				.on(population);
		if (stepNumber == 1) {
			pipeline = pipeline.evaluate(evaluator);
		}

		return pipeline
			       .preselect(robotPreselection)
			       .pairedRecombine(new RobotRecombination(), chanceToRecombine)
			       .individualMutation(new RobotMutation(buildingProvider), chanceToMutate)
			       .evaluate(evaluator).extract();
	}
}

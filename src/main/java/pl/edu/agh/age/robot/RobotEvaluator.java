package pl.edu.agh.age.robot;

import pl.edu.agh.age.common.BuildingProvider;
import pl.edu.agh.age.compute.stream.problem.Evaluator;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.miss.intruders.EvolRobotsController;
import pl.edu.agh.miss.intruders.Simulator;
import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.api.Config;
import pl.edu.agh.miss.intruders.api.impl.SampleConfig;
import pl.edu.agh.miss.intruders.api.impl.SampleIntruderController;
import pl.edu.agh.miss.intruders.api.intruder.IntruderController;
import pl.edu.agh.miss.intruders.api.robots.RobotsController;
import pl.edu.agh.miss.measure.IMeasurer;
import pl.edu.agh.miss.measure.SimpleMeasurer;

public class RobotEvaluator implements Evaluator<RobotSolution> {
	
	private EvaluatorCounter evaluatorCounter;
	
	private BuildingProvider buildingProvider;
	
	public RobotEvaluator(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider) {
		this.evaluatorCounter = evaluatorCounter;
		this.buildingProvider = buildingProvider;
	}

	@Override
	public double evaluate(RobotSolution value) {
		IntruderController ic = new SampleIntruderController();
		RobotsController rc = new EvolRobotsController(value.unwrap());
		Config config = new SampleConfig();
		IMeasurer measurer = new SimpleMeasurer();
		Building building = buildingProvider.getBuilding();	
		int iterations = 1;
		int timeUnits = 20;

		Simulator simulator = new Simulator(ic, rc, config, building, null, iterations, timeUnits, null, measurer);
		simulator.simulate();
		
		evaluatorCounter.increment();
		return measurer.getFinalValue()*Math.pow(10, 5);
	}
}

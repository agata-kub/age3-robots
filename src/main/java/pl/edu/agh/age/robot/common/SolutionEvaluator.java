package pl.edu.agh.age.robot.common;

import java.util.List;
import java.util.Map;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.simulation.intruders.EvolRobotsController;
import pl.edu.agh.simulation.intruders.Simulator;
import pl.edu.agh.simulation.intruders.api.Building;
import pl.edu.agh.simulation.intruders.api.Config;
import pl.edu.agh.simulation.intruders.api.impl.SampleConfig;
import pl.edu.agh.simulation.intruders.api.impl.SampleIntruderController;
import pl.edu.agh.simulation.intruders.api.intruder.IntruderController;
import pl.edu.agh.simulation.intruders.api.robots.RobotsController;
import pl.edu.agh.simulation.measure.IMeasurer;
import pl.edu.agh.simulation.measure.SimpleMeasurer;

public class SolutionEvaluator {
	
	private EvaluatorCounter evaluatorCounter;
	
	private BuildingProvider buildingProvider;
	
	private int iterations;
	
	private int timeUnits;
	
	private int routesCount;
	
	public SolutionEvaluator(EvaluatorCounter evaluatorCounter, BuildingProvider buildingProvider, int iterations, int timeUnits, int routesCount) {
		this.evaluatorCounter = evaluatorCounter;
		this.buildingProvider = buildingProvider;
		this.iterations = iterations;
		this.timeUnits = timeUnits;
		this.routesCount = routesCount;
	}
	
	public double evaluate(Map<String, List<String>> value) {
		IntruderController ic = new SampleIntruderController();
		RobotsController rc = new EvolRobotsController(value, routesCount);
		Config config = new SampleConfig();
		IMeasurer measurer = new SimpleMeasurer();
		Building building = buildingProvider.getBuilding();	

		Simulator simulator = new Simulator(ic, rc, config, building, null, iterations, timeUnits, null, measurer);
		simulator.simulate();
		
		if (evaluatorCounter != null) {
			evaluatorCounter.increment();
		}
		return measurer.getFinalValue()*Math.pow(10, 5);
	}

}

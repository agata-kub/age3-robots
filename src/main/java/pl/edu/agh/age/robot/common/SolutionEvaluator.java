package pl.edu.agh.age.robot.common;

import java.util.List;
import java.util.Map;

import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;
import pl.edu.agh.simulation.intruders.Simulator;
import pl.edu.agh.simulation.intruders.controller.EvolRobotsController;
import pl.edu.agh.simulation.intruders.controller.IConfig;
import pl.edu.agh.simulation.intruders.controller.IntruderController;
import pl.edu.agh.simulation.intruders.controller.RobotsController;
import pl.edu.agh.simulation.intruders.controller.SampleConfig;
import pl.edu.agh.simulation.intruders.controller.SampleIntruderController;
import pl.edu.agh.simulation.intruders.model.Building;
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
		IConfig config = new SampleConfig();
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

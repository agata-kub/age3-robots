package pl.edu.agh.age.robot.evol;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import pl.edu.agh.age.compute.stream.emas.solution.Solution;

public class RobotSolution implements Solution<Map<String,List<String>>> {
	
	private static final long serialVersionUID = 3068089252524117126L;

	private Map<String,List<String>> nametoRoutes;
	
	private double fitness = Double.NaN;
	
	public RobotSolution(Map<String,List<String>> nametoRoutes) {
		this.nametoRoutes = nametoRoutes;
	}

	@Override
	public double fitnessValue() {
		return fitness;
	}

	@Override
	public Map<String, List<String>> unwrap() {
		return nametoRoutes;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(nametoRoutes);
	}

	@Override
	public RobotSolution withFitness(double fitness) {
		this.fitness = fitness;
		return this;
	}
}

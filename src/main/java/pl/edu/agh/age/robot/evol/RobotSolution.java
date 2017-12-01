package pl.edu.agh.age.robot.evol;

import java.util.List;
import java.util.Map;

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
		StringBuilder sb = new StringBuilder();
		for (String name : nametoRoutes.keySet()) {
			sb.append("*"+name+":");
			for (String route : nametoRoutes.get(name)) {
				sb.append(",");
				sb.append(route);
			}
			sb.append("*");
		}
		return sb.toString();
	}

	@Override
	public RobotSolution withFitness(double fitness) {
		this.fitness = fitness;
		return this;
	}
}

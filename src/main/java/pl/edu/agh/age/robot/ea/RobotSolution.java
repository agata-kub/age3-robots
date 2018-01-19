package pl.edu.agh.age.robot.ea;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import pl.edu.agh.age.compute.ea.solution.Solution;

public class RobotSolution implements Solution<HashMap<String,List<String>>>{

	private static final long serialVersionUID = 2573275648081077334L;
	
	private HashMap<String,List<String>> nametoRoutes;
	
	private double fitness = Double.NaN;
	
	public RobotSolution(HashMap<String,List<String>> nametoRoutes) {
		this.nametoRoutes = nametoRoutes;
	}

	@Override
	public double evaluationValue() {
		return fitness;
	}

	@Override
	public Solution<HashMap<String, List<String>>> withEvaluation(double evaluation) {
		this.fitness = evaluation;
		return this;
	}

	@Override
	public HashMap<String, List<String>> unwrap() {
		return nametoRoutes;
	}

	@Override
	public Solution<HashMap<String, List<String>>> cloneWithNewValue(HashMap<String, List<String>> value) {
		return new RobotSolution(value).withEvaluation(fitness);
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(nametoRoutes)+";"+fitness;
	}

}

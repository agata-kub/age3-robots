package pl.edu.agh.age.robot;

import pl.edu.agh.age.compute.stream.problem.ProblemDefinition;

public class RobotProblem implements ProblemDefinition {
	
	private String path;
	
	public RobotProblem(String path) {
		this.path = path;
	}

	@Override
	public String representation() {
		return String.format("Robots' movement scheme for building '%s'", path);
	}
}

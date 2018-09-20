package pl.edu.agh.simulation.intruders.model;

import java.util.UUID;

public class Robot {
	
	String name;
	
	public Robot() {
		name = UUID.randomUUID().toString();
	}
	
	@Override
	public String toString() {
		return "robot+name";
	}

}

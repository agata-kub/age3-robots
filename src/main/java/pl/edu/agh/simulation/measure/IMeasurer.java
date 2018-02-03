package pl.edu.agh.simulation.measure;

import java.io.IOException;

import pl.edu.agh.simulation.intruders.api.impl.Building;

public interface IMeasurer {
	
	void measure(String fileName) throws IOException;
	
	void addMajorState(Building building);
	
	void addMinorState(Building building);
	
	float getFinalValue();

}

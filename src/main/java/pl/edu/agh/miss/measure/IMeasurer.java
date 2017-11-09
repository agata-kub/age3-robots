package pl.edu.agh.miss.measure;

import java.io.IOException;

import pl.edu.agh.miss.intruders.api.Building;

public interface IMeasurer {
	
	void measure(String fileName) throws IOException;
	
	void addMajorState(Building building);
	
	void addMinorState(Building building);
	
	float getFinalValue();

}

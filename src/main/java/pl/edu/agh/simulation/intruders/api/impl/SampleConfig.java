package pl.edu.agh.simulation.intruders.api.impl;

import pl.edu.agh.simulation.intruders.api.Config;
import pl.edu.agh.simulation.intruders.api.Robot;

public class SampleConfig implements Config {

	@Override
	public float getNewProbability(float currentProbability, Robot robot) {
		if (robot!=null) {
			return currentProbability/100;
		}
		return currentProbability;
	}

}

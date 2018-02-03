package pl.edu.agh.simulation.intruders.api.impl;

import pl.edu.agh.simulation.intruders.api.IConfig;

public class SampleConfig implements IConfig {

	@Override
	public float getNewProbability(float currentProbability, Robot robot) {
		if (robot!=null) {
			return currentProbability/100;
		}
		return currentProbability;
	}

}

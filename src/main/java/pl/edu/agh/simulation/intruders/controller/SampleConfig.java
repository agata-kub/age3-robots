package pl.edu.agh.simulation.intruders.controller;

import pl.edu.agh.simulation.intruders.model.Robot;

public class SampleConfig implements IConfig {

	@Override
	public float getNewProbability(float currentProbability, Robot robot) {
		if (robot!=null) {
			return currentProbability/100;
		}
		return currentProbability;
	}

}

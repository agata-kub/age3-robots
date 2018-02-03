package pl.edu.agh.simulation.intruders.api;

import pl.edu.agh.simulation.intruders.api.impl.Robot;

public interface IConfig {
    float getNewProbability(float currentProbability, Robot robot);
}

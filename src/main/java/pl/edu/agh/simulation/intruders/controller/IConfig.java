package pl.edu.agh.simulation.intruders.controller;

import pl.edu.agh.simulation.intruders.model.Robot;

public interface IConfig {
    float getNewProbability(float currentProbability, Robot robot);
}

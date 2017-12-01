package pl.edu.agh.simulation.intruders.api;

public interface Config {
    float getNewProbability(float currentProbability, Robot robot);
}

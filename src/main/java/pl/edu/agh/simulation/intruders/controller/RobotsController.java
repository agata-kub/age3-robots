package pl.edu.agh.simulation.intruders.controller;

import java.util.List;

import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Room;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(List<DoorNode> doorNodes, List<Room> rooms);
    void update();
    void reduceProbabilities(IConfig config);
}

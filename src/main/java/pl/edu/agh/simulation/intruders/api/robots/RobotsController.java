package pl.edu.agh.simulation.intruders.api.robots;

import java.util.List;

import pl.edu.agh.simulation.intruders.api.Config;
import pl.edu.agh.simulation.intruders.api.DoorNode;
import pl.edu.agh.simulation.intruders.api.Room;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(List<DoorNode> doorNodes, List<Room> rooms);
    void update();
    void reduceProbabilities(Config config);
}

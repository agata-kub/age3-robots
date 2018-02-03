package pl.edu.agh.simulation.intruders.api.robots;

import java.util.List;

import pl.edu.agh.simulation.intruders.api.IConfig;
import pl.edu.agh.simulation.intruders.api.impl.DoorNode;
import pl.edu.agh.simulation.intruders.api.impl.Room;

/** Interface implemented to control the robots
 */
public interface RobotsController {
    void init(List<DoorNode> doorNodes, List<Room> rooms);
    void update();
    void reduceProbabilities(IConfig config);
}

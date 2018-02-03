package pl.edu.agh.simulation.intruders.api.intruder;

import java.util.List;

import pl.edu.agh.simulation.intruders.api.impl.DoorNode;

/** Interface implemented to control the intruder
 */
public interface IntruderController {
    void init(List<DoorNode> doorNodes);
    void update();
}

package pl.edu.agh.simulation.intruders.controller;

import java.util.List;

import pl.edu.agh.simulation.intruders.model.DoorNode;

/** Interface implemented to control the intruder
 */
public interface IntruderController {
    void init(List<DoorNode> doorNodes);
    void update();
}

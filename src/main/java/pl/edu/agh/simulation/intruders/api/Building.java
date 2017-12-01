package pl.edu.agh.simulation.intruders.api;

import java.util.List;

public interface Building {

    List<Room> getRooms();

    List<DoorNode> getDoorNodes();

    List<Robot> getRobots();
}

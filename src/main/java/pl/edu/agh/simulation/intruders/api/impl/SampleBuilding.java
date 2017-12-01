package pl.edu.agh.simulation.intruders.api.impl;

import java.util.List;

import pl.edu.agh.simulation.intruders.api.Building;
import pl.edu.agh.simulation.intruders.api.DoorNode;
import pl.edu.agh.simulation.intruders.api.Robot;
import pl.edu.agh.simulation.intruders.api.Room;

public class SampleBuilding implements Building {
    private List<Room> rooms;
    private List<DoorNode> doorNodes;

    public SampleBuilding(List<Room> rooms, List<DoorNode> doorNodes) {
        this.rooms = rooms;
        this.doorNodes = doorNodes;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<DoorNode> getDoorNodes() {
        return doorNodes;
    }

    @Override
    public List<Robot> getRobots() {
        return null;
    }
}

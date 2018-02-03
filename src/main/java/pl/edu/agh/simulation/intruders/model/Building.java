package pl.edu.agh.simulation.intruders.model;

import java.util.List;

public class Building {
    private List<Room> rooms;
    private List<DoorNode> doorNodes;

    public Building(List<Room> rooms, List<DoorNode> doorNodes) {
        this.rooms = rooms;
        this.doorNodes = doorNodes;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<DoorNode> getDoorNodes() {
        return doorNodes;
    }

    public List<Robot> getRobots() {
        return null;
    }
}

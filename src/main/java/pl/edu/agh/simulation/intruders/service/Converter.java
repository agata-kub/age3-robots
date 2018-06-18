package pl.edu.agh.simulation.intruders.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import pl.edu.agh.simulation.intruders.model.Building;
import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.model.Robot;
import pl.edu.agh.simulation.intruders.model.Room;
import pl.edu.agh.simulation.intruders.roson.model.Edge;
import pl.edu.agh.simulation.intruders.roson.model.Node;
import pl.edu.agh.simulation.intruders.roson.model.RosonBuilding;

public class Converter {
    private RosonBuilding rosonBuilding;

    public Converter(RosonBuilding rosonBuilding) {
        this.rosonBuilding = rosonBuilding;
    }


    public Building rosonToSimulation() {
        return rosonToSimulationConverter.apply(this.rosonBuilding);
    }

    public RosonBuilding simulationToRoson(Building building) {
        return simulationToRosonConverter.apply(building);
    }

    private Function<RosonBuilding, Building> rosonToSimulationConverter = building -> {
        List<DoorNode> doorNodes = new LinkedList<>();
        Map<String, DoorNode> doors = new HashMap<>();
        List<Room> rooms = new LinkedList<>();
        for (Edge edge : building.getEdges()) {
            if (building.isGate(edge.getNodeFromId()) && building.isGate(edge.getNodeToId()) && !(doors.containsKey(edge.getNodeFromId()) && !doors.containsKey(edge.getNodeToId()))) {
            	DoorNode start = new DoorNode();
	            DoorNode end = new DoorNode();
	            DoorEdge startEnd = new DoorEdge(start, end);
	            DoorEdge endStart = new DoorEdge(end, start);
	
	            startEnd.setLength((int) (edge.getCost() * 10));
	            endStart.setLength((int) (edge.getCost() * 10));
	            startEnd.setDestination(end);
	            startEnd.setSource(start);
	            endStart.setDestination(start);
	            endStart.setSource(end);
	
	            start.addEdge(startEnd);
	            start.addEdge(endStart);
	            end.addEdge(startEnd);
	            end.addEdge(endStart);
	
	            startEnd.setIntrudersQueue(generateQueue(startEnd.getLength()));
	            endStart.setIntrudersQueue(generateQueue(endStart.getLength()));
	
	            start.setName(edge.getNodeFromId());
	            start.setProbability(building.getNode(edge.getNodeFromId()).getProbability());
	            end.setName(edge.getNodeToId());
	            end.setProbability(building.getNode(edge.getNodeToId()).getProbability());
	            end.setTheOtherSide(start);
	            start.setTheOtherSide(end);
	            doors.put(edge.getNodeFromId(), start);
	            doors.put(edge.getNodeToId(), end);
            }
        }

        building.getSpaces().values().stream().forEach(space -> {
            Room room = new Room();
            // assing all doorNodes to corresponding rooms
            space.getIncidentNodes().stream().filter(n -> building.isGate(n.getNodeId())).forEach(n -> {
                if (doors.get(n.getNodeId()) != null) {
                	room.addNode(doors.get(n.getNodeId()));
                	doors.get(n.getNodeId()).setRoom(room);
                }
            });
            // link all doorNodes attached to the space with each other
            space.getIncidentNodes().stream().forEach(gate -> addBidirectionalEdges(gate, space.getIncidentNodes(), doors));
            
            space.getIncidentNodes().stream() // for all gates attached to the space
            		.filter(n -> doors.get(n.getNodeId()) != null) // filter out ones that aren't doors
                    .forEach(n -> doors.get(n.getNodeId()).getEdges().stream() // for every edge attached to each gate
                            .forEach(e -> {
                            	if (e.getRobotsQueue() == null) {
//	                            	System.out.println(e.getName());
	                                Queue<List<Robot>> q = new LinkedList<>();
	                                List<Robot> l = new LinkedList<>();
	                                if (space.isRobotThere()) {
	                                    l.add(new Robot());
	                                    space.isRobotThere(false);
	//                                    System.out.println(node);
	                                }
	                                q.add(l);
	                                while (q.size()<e.getLength()) {
	                                    q.add(new LinkedList<>());
	                                }
	                                e.setRobotsQueue(q);
                            	}
                            }));
            rooms.add(room);
        });
        doorNodes.addAll(doors.values());
        return new Building(rooms, doorNodes);
    };

    private void addBidirectionalEdges(Node gate, Set<Node> spaceIncidentNodes, Map<String, DoorNode> doors) {
        DoorNode doorNode = doors.get(gate.getNodeId());
        if (doorNode == null) return;
        spaceIncidentNodes.stream()
        	// check if it's not the same gate node
        	.filter(spaceIncidentNode -> !Objects.equals(spaceIncidentNode.getNodeId(), gate.getNodeId()))
        	.forEach(incidentDoorNode -> {
        		DoorNode other = doors.getOrDefault(incidentDoorNode.getNodeId(), null);
        		if (other != null) {
	                gate.getIncidentNodes().add(rosonBuilding.getNode(other.getName()));
	                DoorEdge startEnd = new DoorEdge(doorNode, other);
	                startEnd.setLength(1);
	                doorNode.addEdge(startEnd);
	                other.addEdge(startEnd);
	                startEnd.setIntrudersQueue(generateQueue(startEnd.getLength()));
        		}
        });
    }

    private Function<Building, RosonBuilding> simulationToRosonConverter = building -> {
        building.getDoorNodes().forEach(node -> {
            float probability = node.getProbability();
            this.rosonBuilding.getNode(node.getName()).getIncidentNodes().stream()
                    .filter(n -> this.rosonBuilding.isSpace(n.getNodeId()))
                    .forEach(n -> n.setProbability(probability));
            this.rosonBuilding.getNode(node.getName()).setProbability(probability);

            HashSet<String> nodesWithRobots = new HashSet<>();
            this.rosonBuilding.getEdges().forEach( edge -> {
                if (Objects.equals(edge.getNodeFromId(), node.getName()) ||
                        Objects.equals(edge.getNodeToId(), node.getName())) {
                    boolean onlyNullRobots = Arrays.stream(node.getRobots()).allMatch(r -> r == null);
                    if (onlyNullRobots) {
                        edge.setHasRobot(false);
                        rosonBuilding.getNode(node.getName()).isRobotThere(false);
                    } else {
                        edge.setHasRobot(true);
                        nodesWithRobots.addAll(Arrays.asList(node.getName()));
                    }
                }
            });
            nodesWithRobots.forEach((id) -> {
                if (rosonBuilding.isGate(id)) {
                    rosonBuilding.getNode(id).isRobotThere(true);
                } else rosonBuilding.getNode(id).isRobotThere(false);
            });
            nodesWithRobots.clear();
        });
        return this.rosonBuilding;
    };

    private static Queue<Float> generateQueue(int size) {
        Queue<Float> iq = new LinkedList<>();
        for (int i=0; i < size; i++) {
            iq.add(0f);
        }
        return iq;
    }
}



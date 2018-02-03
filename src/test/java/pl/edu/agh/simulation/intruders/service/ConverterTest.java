package pl.edu.agh.simulation.intruders.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import pl.edu.agh.simulation.intruders.api.Building;
import pl.edu.agh.simulation.intruders.api.DoorEdge;
import pl.edu.agh.simulation.intruders.api.DoorNode;
import pl.edu.agh.simulation.intruders.model.Edge;
import pl.edu.agh.simulation.intruders.model.Gate;
import pl.edu.agh.simulation.intruders.model.Node;
import pl.edu.agh.simulation.intruders.model.RosonBuilding;
import pl.edu.agh.simulation.intruders.model.Space;

public class ConverterTest {

	@Test
	public void testRosonToSimulationConversion() {
		// given
		RosonBuilding rosonBuilding = generateSampleRosonBuilding();
		Converter converter = new Converter(rosonBuilding);

		// when
		Building building = converter.rosonToSimulation();

		// then
		assertEquals(5, building.getRooms().size());
		assertEquals(10, building.getDoorNodes().size());
		for (DoorNode doorNode : building.getDoorNodes()) {
			assertEquals(4, doorNode.getEdges().size());
		}

	}

	@Test
	public void testRobotsPresenceConversion() {
		// given
		RosonBuilding rosonBuilding = generateSampleRosonBuilding();
		addSampleRobotsPresence(rosonBuilding);
		Converter converter = new Converter(rosonBuilding);

		// when
		Building building = converter.rosonToSimulation();

		// then
		List<DoorNode> robotDoorNodes = building.getDoorNodes()
				.stream()
				.filter(doorNode -> doorNode.getName().equals("G12") || doorNode.getName().equals("G15"))
				.collect(Collectors.toList());
		long robotsCount = 0;
		for (DoorNode robotDoorNode : robotDoorNodes) {
			List<DoorEdge> doorEdges = robotDoorNode.getEdges()
					.stream()
					.filter(doorEdge -> doorEdge.getSource().equals(robotDoorNode))
					.collect(Collectors.toList());
			for (DoorEdge edge: doorEdges) {
				long edgeRobotsCount = edge.getRobotsQueue()
						.stream()
						.map(robotList -> robotList.size())
						.reduce(0, (a,b) -> a+b);
				robotsCount += edgeRobotsCount;
			}
		};
		assertEquals(1, robotsCount);

	}

	private void addSampleRobotsPresence(RosonBuilding rosonBuilding) {
		Node robotNode = rosonBuilding.getNode("S1");
		robotNode.isRobotThere(true);
	}

	private RosonBuilding generateSampleRosonBuilding() {
		RosonBuilding rosonBuilding = new RosonBuilding();
		String[] spaceNodeIds = { "S1", "S2", "S3", "S4", "S5" };
		String[] spaceIds = { "Space1", "Space2", "Space3", "Space4", "Space5" };
		String[] gateNodeIds = { "G12", "G21", "G23", "G32", "G34", "G43", "G45", "G54", "G51", "G15" };
		String[] gateIds = { "Gate12", "Gate23", "Gate34", "Gate45", "Gate51" };
		createSpaces(rosonBuilding, spaceNodeIds, spaceIds);
		createGates(rosonBuilding, gateNodeIds, gateIds);
		createEdges(rosonBuilding, spaceNodeIds, spaceIds, gateNodeIds);

		return rosonBuilding;
	}

	private void createSpaces(RosonBuilding rosonBuilding, String[] spaceNodeIds, String[] spaceIds) {
		for (int i = 0; i < spaceIds.length; i++) {
			Space space = new Space(spaceNodeIds[i], spaceIds[i]);
			rosonBuilding.addSpace(space);
		}
	}

	private void createGates(RosonBuilding rosonBuilding, String[] gateNodeIds, String[] gateIds) {
		for (int i = 0; i < gateIds.length; i++) {
			Gate gate1 = new Gate(gateNodeIds[2 * i], gateIds[i]);
			Gate gate2 = new Gate(gateNodeIds[2 * i + 1], gateIds[i]);
			rosonBuilding.addGate(gate1);
			rosonBuilding.addGate(gate2);
		}
	}

	private void createEdges(RosonBuilding rosonBuilding, String[] spaceNodeIds, String[] spaceIds,
			String[] gateNodeIds) {
		for (int i = 0; i < spaceIds.length; i++) {
			Pair<Edge, Edge> edges1 = createEdgesPair(rosonBuilding, spaceNodeIds[i],
					gateNodeIds[Math.floorMod(2 * i - 1, gateNodeIds.length)], 1.0);
			Pair<Edge, Edge> edges2 = createEdgesPair(rosonBuilding, spaceNodeIds[i], gateNodeIds[2 * i], 3.0);
			Pair<Edge, Edge> edges3 = createEdgesPair(rosonBuilding, gateNodeIds[2 * i], gateNodeIds[2 * i + 1], 3.0);
			rosonBuilding.addEdges(Arrays.asList(edges1.getLeft(), edges1.getRight(), edges2.getLeft(),
					edges2.getRight(), edges3.getLeft(), edges3.getRight()));
		}
	}

	private Pair<Edge, Edge> createEdgesPair(RosonBuilding rosonBuilding, String firstNodeId, String secondNodeId, double length) {
		Edge edge1 = new Edge();
		edge1.setNodeFromId(firstNodeId);
		edge1.setNodeToId(secondNodeId);
		edge1.setCost(length);
		Edge edge2 = new Edge();
		edge2.setNodeFromId(secondNodeId);
		edge2.setNodeToId(firstNodeId);
		edge2.setCost(length);
		Node firstNode = rosonBuilding.getNode(firstNodeId);
		Node secondNode = rosonBuilding.getNode(secondNodeId);
		firstNode.addIncidentEdge(edge1);
		secondNode.addIncidentEdge(edge2);
		firstNode.addIncidentNode(secondNode);
		secondNode.addIncidentNode(firstNode);
		return Pair.of(edge1, edge2);
	}

}

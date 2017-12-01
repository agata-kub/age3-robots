package pl.edu.agh.simulation.intruders.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.simulation.intruders.api.DoorEdge;
import pl.edu.agh.simulation.intruders.api.DoorNode;
import pl.edu.agh.simulation.intruders.api.intruder.IntruderController;

public class SampleIntruderController implements IntruderController {
	
	private List<DoorNode> doorNodes;
	
	private Map<DoorEdge, Float> edgesProb = new HashMap<>(); 
	
	private Map<DoorNode, Float> stayProb = new HashMap<>();
	
	private Map<DoorNode, Float> passThroughProb = new HashMap<>();

	@Override
	public void init(List<DoorNode> doorNodes) {
		this.doorNodes = doorNodes;
		for (DoorNode node : doorNodes) {
			float edgeProb = 1.0f/(node.getEdges().size()+2);
			for (DoorEdge edge : node.getEdges()) {
				edgesProb.put(edge, edgeProb);
			}
			stayProb.put(node, edgeProb);
			passThroughProb.put(node, edgeProb);
		}
	}

	@Override
	public void update() {
		for (DoorNode node : doorNodes) {
			float intruderByTheDoorProb = node.getProbability();
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getDestination().equals(node) && !edge.getIntruderQueue().isEmpty()) {
					intruderByTheDoorProb += edge.getIntruderQueue().poll();
				}
			}
		
			for (DoorEdge edge : node.getEdges()) {
				if (edge.getSource().equals(node)) {
					float edgeProb = edgesProb.get(edge);
					float intruderMoveProb = intruderByTheDoorProb*edgeProb;
					edge.getIntruderQueue().add(intruderMoveProb);
				}
			}
			DoorNode otherSide = node.getTheOtherSide();
			float intruderPassThroughProb = intruderByTheDoorProb*passThroughProb.get(node);
			otherSide.setProbability(otherSide.getProbability()+intruderPassThroughProb);
			node.setProbability(intruderByTheDoorProb*stayProb.get(node));
		}
	}

}

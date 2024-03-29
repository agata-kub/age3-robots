package pl.edu.agh.age.robot.common;

import java.io.File;
import java.io.IOException;

import pl.edu.agh.simulation.intruders.Main;
import pl.edu.agh.simulation.intruders.graph.Graph;
import pl.edu.agh.simulation.intruders.graph.Vertex;
import pl.edu.agh.simulation.intruders.model.Building;
import pl.edu.agh.simulation.intruders.model.DoorEdge;
import pl.edu.agh.simulation.intruders.model.DoorNode;
import pl.edu.agh.simulation.intruders.service.Converter;
import pl.edu.agh.simulation.intruders.service.IOService;

public class BuildingProvider {
	
	private Graph graph;
	
	private String sourcePath;
	
	public BuildingProvider(String sourcePath) {
		this.sourcePath = sourcePath;
		Building building = getBuilding();
		graph = toGraph(building);
	}

	public Graph getGraph() {
		return graph;
	}
	
	public Building getBuilding() {
		File file = getResourceFile();
		try {
			Converter converter = new Converter(IOService.importFromJson(file));
			Building building = converter.rosonToSimulation();
			return building;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private File getResourceFile() {
		return new File(Main.class.getClassLoader().getResource(sourcePath).getFile());
	}
	
	private Graph toGraph(Building building) {
		Graph graph = new Graph();
		for (DoorNode node : building.getDoorNodes()) {
			Vertex v = resolveVertex(node, graph);
			for (DoorEdge edge : node.getEdges()) {
				v.addNeighbor(resolveVertex(edge.getDestination(), graph));
			}			
		}
		return graph;
	}
	
	private static Vertex resolveVertex(DoorNode node, Graph graph) {
		Vertex v = graph.getVertex(node.getName());
		if (v == null) {
			v = new Vertex(node.getName());
			graph.addVertex(v);
		}
		return v;
	}
}

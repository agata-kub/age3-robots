package pl.edu.agh.miss.util;

import java.io.File;
import java.io.IOException;

import pl.edu.agh.miss.intruders.Main;
import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.api.DoorEdge;
import pl.edu.agh.miss.intruders.api.DoorNode;
import pl.edu.agh.miss.intruders.graph.Graph;
import pl.edu.agh.miss.intruders.graph.Vertex;
import pl.edu.agh.miss.intruders.service.Converter;
import pl.edu.agh.miss.intruders.service.IOService;

public final class ConvertUtils {
	
	public static final String THROUGH = "through";
	
	public static final int ROUTES_COUNT = 2;
	
	private static Graph graph;
	
	static {
		Building building = getBuilding();
		graph = toGraph(building);
	}

	public static Graph getGraph() {
		return graph;
	}
	
	public static Building getBuilding() {
		File file = new File(Main.class.getClassLoader().getResource("roson/building1.roson").getFile());
		try {
			Converter converter = new Converter(IOService.importFromJson(file));
			Building building = converter.rosonToSimulation();
			return building;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Graph toGraph(Building building) {
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

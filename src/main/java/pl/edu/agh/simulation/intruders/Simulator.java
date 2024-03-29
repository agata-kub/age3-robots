package pl.edu.agh.simulation.intruders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.graphstream.graph.Graph;

import pl.edu.agh.simulation.intruders.controller.IConfig;
import pl.edu.agh.simulation.intruders.controller.IntruderController;
import pl.edu.agh.simulation.intruders.controller.RobotsController;
import pl.edu.agh.simulation.intruders.model.Building;
import pl.edu.agh.simulation.intruders.roson.model.Edge;
import pl.edu.agh.simulation.intruders.roson.model.RosonBuilding;
import pl.edu.agh.simulation.intruders.service.Converter;
import pl.edu.agh.simulation.intruders.service.GraphView;
import pl.edu.agh.simulation.intruders.service.utils.ColorUtils;
import pl.edu.agh.simulation.measure.IMeasurer;

public class Simulator {

	private IntruderController intruderController;

	private RobotsController robotsController;

	private IConfig config;

	private Building building;

	private Converter converter;
	
	private IMeasurer measurer;

	private int timeUnits = 5;

	private int iterations = 1;
	
	private GraphView graphView;

	public Simulator(IntruderController intruderController, RobotsController robotsController, IConfig config,
					 Building building, Converter converter, int iterations, int timeUnits, GraphView view, IMeasurer measurer) {
		this.intruderController = intruderController;
		this.robotsController = robotsController;
		this.config = config;
		this.building = building;
		this.converter = converter;
		this.timeUnits = timeUnits;
		this.graphView = view;
		this.iterations = iterations;
		this.measurer = measurer;
	}

	public void simulate() {
		Graph graph = null;
		if  (graphView != null) {
			RosonBuilding rosonBuilding = converter.simulationToRoson(building);
			graph = graphView.generate(rosonBuilding);
		}
		measurer.addMajorState(building);
		intruderController.init(building.getDoorNodes());
		robotsController.init(building.getDoorNodes(), building.getRooms());
//		printState();
		for (int j = 0; j < iterations; j++) {
			for (int i = 0; i < timeUnits; i++) {
				if (graph != null && graphView.screenshots) {
					graphView.makeScreenShot(graph, "result/images/img_" + i + ".png");
				}
				intruderController.update();
				robotsController.update();
				robotsController.reduceProbabilities(config);
				if (i<timeUnits-1) {
					measurer.addMinorState(building);
				}
			}
//			printState();
			if  (graph != null) updateGraph(graph);
			measurer.addMajorState(building);
//			sleep();
		}		
		try {
			measurer.measure("result/test_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.exit(0);
	}

	private void printState() {
		System.out.println("###########################################################################################################");
		building.getDoorNodes().forEach(System.out::println);
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void updateGraph(Graph graph) {
		RosonBuilding rosonBuilding = converter.simulationToRoson(building);
		for (org.graphstream.graph.Node node : graph) {
			String id = "Node" + node.getId().substring(0, node.getId().indexOf(" "));
			if (rosonBuilding.isGate(id)) {
				double probability = rosonBuilding.getNode(id).getProbability();
				String color = ColorUtils.getRGBString(ColorUtils.probabilityToColor(probability));
				node.setAttribute("ui.style", "fill-color:  " + color + ";");
				String label = String.valueOf(probability).length() < 5 ? String.valueOf(probability) : String.valueOf
						(probability).substring(0, 5);
				node.addAttribute("ui.label", label);
				node.addAttribute("ui.style", "text-alignment: under;");
			}
			if (rosonBuilding.getNode(id).isRobotThere()) {
				if(rosonBuilding.isGate(id))
				node.addAttribute("ui.style", "icon: url('./src/main/resources/robot.png'); icon-mode: at-left; ");
				else rosonBuilding.getNode(id).isRobotThere(false);
			} else {
				node.addAttribute("ui.style", "icon: url('./src/main/resources/empty.png'); icon-mode: at-left; ");
			}
		}

		for (org.graphstream.graph.Edge edge : graph.getEachEdge()) {
			Edge e = rosonBuilding.getEdge(edge.getAttribute("id"));
			if (e.hasRobot()) {
				edge.addAttribute("ui.style", "fill-color: rgb(255,0,0);");
			} else {
				edge.addAttribute("ui.style", "fill-color: rgb(0,0,0);");
			}
		}
	}

}

package pl.edu.agh.miss.initialize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.miss.intruders.api.Building;
import pl.edu.agh.miss.intruders.graph.Graph;
import pl.edu.agh.miss.util.ConvertUtils;

public class PopulationInitializer {
	
	public static void main(String[] args) {
		PopulationInitializer pi = new PopulationInitializer();
		RobotPath path = pi.new RobotPath();
		path.addNode("ala");
		path.addNode("ma");
		path.addNode("kota");
		path.addIntruderValue(50);
		path.addIntruderValue(10);
		RobotPath path1 = path.copy();
		System.out.println(path1.getIntruderValue());
		System.out.println(path1.getRoute());
	}
	
	public void initialize() {
		Building building = ConvertUtils.getBuilding();
		Graph graph = ConvertUtils.getGraph();
		RobotPath path = new RobotPath();
		
		
	}
	
	private class RobotPath {

		List<String> route;
		
		double intruderValue;
		
		public RobotPath() {
			route = new LinkedList<>();
		}
		
		public void addNode(String node) {
			route.add(node);
		}
		
		public List<String> getRoute() {
			return route;
		}
		
		public void addIntruderValue(int value) {
			intruderValue += value;
		}
		
		public double getIntruderValue() {
			return intruderValue;
		}
		
		
		public void setRoute(List<String> route) {
			this.route = route;
		}

		public void setIntruderValue(double intruderAccumulator) {
			this.intruderValue = intruderAccumulator;
		}
		
		public RobotPath copy() {
			RobotPath result = new RobotPath();
			result.setIntruderValue(getIntruderValue());
			for (String node : getRoute()) {
				result.addNode(node);
			}
			return result;
		}
	}
}

package pl.edu.agh.age.robot.evol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.age.compute.stream.emas.reproduction.mutation.Mutation;
import pl.edu.agh.age.robot.common.BuildingProvider;
import pl.edu.agh.simulation.intruders.graph.Graph;
import pl.edu.agh.simulation.intruders.graph.Vertex;

public class RobotMutation implements Mutation<RobotSolution> {
	
	private BuildingProvider buildingProvider;
	
	public RobotMutation(BuildingProvider buildingProvider) {
		this.buildingProvider = buildingProvider;
	}

	@Override
	public RobotSolution mutate(RobotSolution solution) {
		return new RobotSolution(mutateSolution(solution.unwrap(), 1, 1));
	}

	private Map<String, List<String>> mutateSolution(Map<String, List<String>> namesToRoutes, int elementsCount,
			int mutationPointsCount) {
		Random rand = ThreadLocalRandom.current();
		Map<String, List<String>> result = new HashMap<>();
		Graph graph = buildingProvider.getGraph();
		// get random indexes
		Set<Integer> indexes = new HashSet<>();
		for (int i=0;i<elementsCount;i++) {
			indexes.add(rand.nextInt(namesToRoutes.keySet().size()));
		}
		// prepare new solution
		int i=0;
		for (String name : namesToRoutes.keySet()) {
			if (indexes.contains(i)) {
				List<Vertex> neighbors = graph.getVertex(name).getNeighbors();
				List<String> routes = namesToRoutes.get(name);
				List<String> newRoutes = new LinkedList<>();
				newRoutes.addAll(routes);
				for (int j=0;j<mutationPointsCount;j++) {
					int index = rand.nextInt(newRoutes.size());
					String element = neighbors.get(rand.nextInt(neighbors.size())).getName();
					newRoutes.set(index, element);				
				}
				result.put(name, newRoutes);
			} else {
				result.put(name, namesToRoutes.get(name));
			}
			i++;
		}
//		System.out.println("Mutated "+result.toString());
		return result;
	}
}

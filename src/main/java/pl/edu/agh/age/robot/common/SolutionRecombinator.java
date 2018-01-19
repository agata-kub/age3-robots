package pl.edu.agh.age.robot.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.vavr.Tuple2;

public class SolutionRecombinator {

	public Tuple2<HashMap<String, List<String>>, HashMap<String, List<String>>> recombineSolutions(
			Map<String, List<String>> solution1, Map<String, List<String>> solution2) {
		Random rand = ThreadLocalRandom.current();
		HashMap<String, List<String>> result1 = new HashMap<>();
		HashMap<String, List<String>> result2 = new HashMap<>();
		for (String name : solution1.keySet()) {
			List<String> newRoutes1 = new LinkedList<>();
			List<String> newRoutes2 = new LinkedList<>();
			List<String> routes1 = solution1.get(name);
			List<String> routes2 = solution2.get(name);
			for (int i = 0; i < routes1.size(); i++) {
				if (rand.nextBoolean()) {
					newRoutes1.add(routes1.get(i));
					newRoutes2.add(routes2.get(i));
				} else {
					newRoutes1.add(routes2.get(i));
					newRoutes2.add(routes1.get(i));
				}
			}
			result1.put(name, newRoutes1);
			result2.put(name, newRoutes2);
		}
		return new Tuple2<HashMap<String, List<String>>,HashMap<String, List<String>>>(result1, result2);
	}
}

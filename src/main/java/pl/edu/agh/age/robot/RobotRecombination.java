package pl.edu.agh.age.robot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import pl.edu.agh.age.compute.stream.emas.reproduction.recombination.Recombination;

public class RobotRecombination implements Recombination<RobotSolution> {

	@Override
	public RobotSolution recombine(RobotSolution firstSolution, RobotSolution secondSolution) {
		Map<String, List<String>> solution1 = firstSolution.unwrap();
		Map<String, List<String>> solution2 = secondSolution.unwrap();
		return new RobotSolution(recombineSolutions(solution1, solution2));
	}

	private Map<String, List<String>> recombineSolutions(Map<String, List<String>> solution1,
			Map<String, List<String>> solution2) {
		Random rand = ThreadLocalRandom.current();
		Map<String, List<String>> result = new HashMap<>();
		for (String name : solution1.keySet()) {
			List<String> newRoutes = new LinkedList<>();
			List<String> routes1 = solution1.get(name);
			List<String> routes2 = solution2.get(name);
			for (int i=0;i<routes1.size();i++) {
				String nextRoute = rand.nextBoolean() ? routes1.get(i) : routes2.get(i);
				newRoutes.add(nextRoute);
			}
			result.put(name, newRoutes);
		}
		return result;
	}

}

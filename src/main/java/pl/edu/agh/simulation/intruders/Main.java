package pl.edu.agh.simulation.intruders;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import pl.edu.agh.simulation.intruders.controller.BfsRobotsController;
import pl.edu.agh.simulation.intruders.controller.IConfig;
import pl.edu.agh.simulation.intruders.controller.IntruderController;
import pl.edu.agh.simulation.intruders.controller.RobotsController;
import pl.edu.agh.simulation.intruders.controller.SampleConfig;
import pl.edu.agh.simulation.intruders.controller.SampleIntruderController;
import pl.edu.agh.simulation.intruders.controller.SampleRobotsController;
import pl.edu.agh.simulation.intruders.model.Building;
import pl.edu.agh.simulation.intruders.roson.model.RosonBuilding;
import pl.edu.agh.simulation.intruders.service.Converter;
import pl.edu.agh.simulation.intruders.service.GraphView;
import pl.edu.agh.simulation.intruders.service.IOService;
import pl.edu.agh.simulation.measure.Measurer;

public class Main {
	
	public static void main(String[] args) throws IOException, ParseException {

		Options options = new Options();
		options.addOption("file", true, "path to roson file");
		options.addOption("iterations", true, "length of simulation in iterations");
		options.addOption("times", true, "time units per iteration");
		options.addOption("merged", false, "merge edges on visualization");
		options.addOption("labels", false, "show labels under nodes");
		options.addOption("robots", false, "show robots on visualization");
		options.addOption("screenshots", false, "save screenshot after every step");
		options.addOption("v", false, "show building graph with no simulation");
		options.addOption("s", false, "run simulation without visualization");
		options.addOption("verbosity", true, "verbosity level (0-3)");
		options.addOption("auto", false, "use automatic layout");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		String path = cmd.getOptionValue("file");
		File file;
		if (path == null) {
			file = new File(Main.class.getClassLoader().getResource("roson/single_cases/sample_1.roson").getFile());
		} else {
			file = new File(Main.class.getClassLoader().getResource(path).getFile());
		}
		String times = cmd.getOptionValue("times");
		int timeUnits;
		if (times == null){
			timeUnits = 30;
		} else {
			timeUnits = Integer.parseInt(times);
		}
		String iter = cmd.getOptionValue("iterations");
		int iterations;
		if (iter == null){
			iterations = 1;
		} else {
			iterations = Integer.parseInt(iter);
		}
		String verbosityOption = cmd.getOptionValue("verbosity");
		int verbosity;
		if (verbosityOption==null) {
			verbosity = 1;
		} else {
			verbosity = Integer.parseInt(verbosityOption);
		}

		boolean labels, edges, robots, screenshots;
		labels = cmd.hasOption("labels");
		edges = cmd.hasOption("merged");
		robots = cmd.hasOption("robots");
		screenshots = cmd.hasOption("screenshots");

		RosonBuilding b = IOService.importFromJson(file);
		b.setAutomaticLayout(cmd.hasOption("auto"));
		Converter converter = new Converter(b);
		GraphView graphView = new GraphView().withMergedEdges(edges)
				.withNodeLabels(labels).withRobots(robots).withScreenshots(screenshots);
		if (cmd.hasOption("v")) {
			graphView.generate(b).display();
		} else {
			Building building = converter.rosonToSimulation();
			IntruderController ic = new SampleIntruderController();
			RobotsController rc = new BfsRobotsController();
			IConfig config = new SampleConfig();
			Measurer measurer = new Measurer(verbosity, cmd);
			Simulator s;
			if (cmd.hasOption("s")) {
				s = new Simulator(ic, rc, config, building, converter, iterations, timeUnits, null, measurer);
			} else {
				s = new Simulator(ic, rc, config, building, converter, iterations, timeUnits, graphView, measurer);
			}
			s.simulate();
		}
	}
}

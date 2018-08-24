package pl.edu.agh.age.robot.ea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.vavr.collection.Map;
import pl.edu.agh.age.compute.ea.StatisticsKeys;
import pl.edu.agh.age.compute.stream.problem.EvaluatorCounter;

public class StatisticsWriter {
	
	private EvaluatorCounter counter;
	
	private File file;
	
	private boolean propertiesLogged;
	
	public StatisticsWriter(EvaluatorCounter counter) {
		this.counter = counter;
	}
	
	public void writeStats(Map<StatisticsKeys, Object> stats) {
		
		if (file == null) {
			LocalDateTime dateTime = LocalDateTime.now();
			String dateTimeString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss.SSS"));
			file = new File("logs/test-stat-log-"+dateTimeString+".log");
		}
		try(FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			StringBuilder builder = new StringBuilder();
			String currentTime = Long.toString(new Date().getTime());
			String stepNumber = stats.get(StatisticsKeys.STEP_NUMBER).get().toString();
			String evalCount = Long.toString(counter.get());
			String currentBest = Double.toString(-((RobotSolution)stats.get(StatisticsKeys.CURRENT_BEST).get()).evaluationValue());
			builder.append("[S];"+currentTime+";"+currentBest+";"+evalCount);
			if (!propertiesLogged) {
				out.println(logProperties());
				propertiesLogged = true;
			}
			out.println(builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String logProperties() {
		StringBuilder builder = new StringBuilder();
		builder.append("Properties:\n\n");
		try(FileInputStream in = new FileInputStream("src/main/resources/pl/edu/agh/age/robot/ea/ea-robot-config.properties")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			br.lines().forEach(line -> builder.append(line+"\n"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		builder.append("\n");
		return builder.toString();
	}
}

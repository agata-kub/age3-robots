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

public final class Commons {
	
	public static final String THROUGH = "through";
	
	public static final int ROUTES_COUNT = 2;
	
	public static final String BUILDING_FILE_PATH = Main.class.getClassLoader().getResource("roson/single_cases/sample_1.roson").getFile(); 
	
}

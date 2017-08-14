package com.sna.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.MergeProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

import com.sna.gephi.EigenvectorCentrality;
import com.sna.gephi.GraphDistance;

public class MetricsCalculator {

	private Graph graph;
	private String fileDir;

	public MetricsCalculator(String fileDir) {
		this.fileDir = fileDir;
		initGephi();
	}

	public Graph getGraph() {
		return graph;
	}

	private void initGephi() {

		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();

		// Import first file
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		Container[] containers = new Container[3];
		try {
			for (int i = 0; i < 3; i++) {
				File file = new File(getClass().getResource(fileDir).toURI());
				containers[i] = importController.importFile(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		// Process the container using the MergeProcessor
		importController.process(containers, new MergeProcessor(), workspace);

		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
		graph = graphModel.getGraph();

	}

	public int vertexCountMetric() {
		return graph != null ? graph.getNodeCount() : 0;
	}

	public int axesCountMetric() {
		return graph != null ? graph.getEdgeCount() : 0;
	}

	public double calculateDensityMetric(boolean isGraphDirected) {
		double result;

		double edgesCount = graph.getEdgeCount();
		double nodesCount = graph.getNodeCount();
		double multiplier = 1;

		if (!isGraphDirected) {
			multiplier = 2;
		}
		result = (multiplier * edgesCount) / (nodesCount * nodesCount - nodesCount);
		return result;
	}

	public List<NodeMetrics> calculateNodesMetrics() {
		List<NodeMetrics> nodes = new ArrayList<>();
		calculateDistancesMetrics();
		calculateEigenvectorCentralityMetric();

		for (Node node : graph.getNodes()) {
			NodeMetrics newNode = new NodeMetrics(node.getLabel(), graph.getDegree(node),
					(double) node.getAttribute("closnesscentrality"),
					(double) node.getAttribute("betweenesscentrality"), (double) node.getAttribute("eigencentrality"));

			nodes.add(newNode);
		}
		return nodes;
	}

	public void calculateDistancesMetrics() {
		GraphDistance graphDistance = new GraphDistance();
		graphDistance.execute(graph);
	}

	public void calculateEigenvectorCentralityMetric() {
		EigenvectorCentrality graphEigen = new EigenvectorCentrality();
		graphEigen.execute(graph);
	}

	public List<NodeMetrics> findNodeMetrics(String label) {
		for (Node node : graph.getNodes()) {
			if (node.getLabel().equals(label)) {
				List<NodeMetrics> nodeMetrics = new ArrayList<>();
				NodeMetrics newNode = new NodeMetrics(node.getLabel(), graph.getDegree(node),
						(double) node.getAttribute("closnesscentrality"),
						(double) node.getAttribute("betweenesscentrality"),
						(double) node.getAttribute("eigencentrality"));
				nodeMetrics.add(newNode);
				return nodeMetrics;
			}
		}
		
		return null;
	}

}

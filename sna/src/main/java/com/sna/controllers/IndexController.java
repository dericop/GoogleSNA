package com.sna.controllers;

import java.util.ArrayList;
import java.util.List;

import org.gephi.graph.api.Node;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sna.models.GraphMetrics;
import com.sna.models.MetricsCalculator;
import com.sna.models.NodeMetrics;

@Controller
@RequestMapping
public class IndexController {
	MetricsCalculator mc;

	@GetMapping(value="test1")
	public String index(Model model) {
		return "index";
	}

	@GetMapping(value = "test1/metrics")
	public @ResponseBody GraphMetrics getMetrics() {

		mc = new MetricsCalculator("/static/test3.gexf");
		return new GraphMetrics(mc.vertexCountMetric(), mc.axesCountMetric(), mc.calculateDensityMetric(false),
				mc.calculateNodesMetrics());
	}

	@GetMapping(value = "test1/metrics/{name}")
	public @ResponseBody GraphMetrics getNodeMetrics(@PathVariable String name) {
		if(mc==null)
			mc = new MetricsCalculator("/static/test3.gexf");
		
		mc.calculateEigenvectorCentralityMetric();
		mc.calculateDistancesMetrics();
		List<NodeMetrics> node = mc.findNodeMetrics(name);
		
		return new GraphMetrics(mc.vertexCountMetric(), mc.axesCountMetric(), mc.calculateDensityMetric(false),
				node);
	}
	
	
	
	@GetMapping(value="test2")
	public String index2(Model model) {
		return "index2";
	}
	
	@GetMapping(value = "test2/metrics")
	public @ResponseBody GraphMetrics getMetrics2() {

		mc = new MetricsCalculator("/static/test4.gexf");
		return new GraphMetrics(mc.vertexCountMetric(), mc.axesCountMetric(), mc.calculateDensityMetric(false),
				mc.calculateNodesMetrics());
	}

	@GetMapping(value = "test2/metrics/{name}")
	public @ResponseBody GraphMetrics getNodeMetrics2(@PathVariable String name) {
		if(mc==null)
			mc = new MetricsCalculator("/static/test4.gexf");
		
		mc.calculateEigenvectorCentralityMetric();
		mc.calculateDistancesMetrics();
		List<NodeMetrics> node = mc.findNodeMetrics(name);
		
		return new GraphMetrics(mc.vertexCountMetric(), mc.axesCountMetric(), mc.calculateDensityMetric(false),
				node);
	}

}

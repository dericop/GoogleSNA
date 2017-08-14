package com.sna.models;

import java.util.List;

public class GraphMetrics {
	private int vertexCount;
	private int axesCount;
	private double density;
	private List<NodeMetrics> nodeMetrics;

	public GraphMetrics(int vertexCount, int axesCount, double density, List<NodeMetrics> nodeMetrics) {
		super();
		this.vertexCount = vertexCount;
		this.axesCount = axesCount;
		this.density = density;
		this.nodeMetrics = nodeMetrics;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public int getAxesCount() {
		return axesCount;
	}

	public void setAxesCount(int axesCount) {
		this.axesCount = axesCount;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public List<NodeMetrics> getNodeMetrics() {
		return nodeMetrics;
	}

	public void setNodeMetrics(List<NodeMetrics> nodeMetrics) {
		this.nodeMetrics = nodeMetrics;
	}

}

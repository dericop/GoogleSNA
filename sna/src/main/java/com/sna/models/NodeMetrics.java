package com.sna.models;

public class NodeMetrics {

	private String label;
	private int degree;
	private double closeness;
	private double betweenness;
	private double eigenvectorCentrality;

	public NodeMetrics(String label, int degree, double closeness, double betweenness, double eigenvectorCentrality) {
		super();
		this.label = label;
		this.degree = degree;
		this.closeness = closeness;
		this.betweenness = betweenness;
		this.eigenvectorCentrality = eigenvectorCentrality;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public double getCloseness() {
		return closeness;
	}

	public void setCloseness(double closeness) {
		this.closeness = closeness;
	}

	public double getBetweenness() {
		return betweenness;
	}

	public void setBetweenness(double betweenness) {
		this.betweenness = betweenness;
	}

	public double getEigenvectorCentrality() {
		return eigenvectorCentrality;
	}

	public void setEigenvectorCentrality(double eigenvectorCentrality) {
		this.eigenvectorCentrality = eigenvectorCentrality;
	}

}

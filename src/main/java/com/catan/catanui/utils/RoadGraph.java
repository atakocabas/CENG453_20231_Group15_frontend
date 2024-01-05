package com.catan.catanui.utils;

import java.util.HashSet;
import org.jgrapht.graph.DefaultUndirectedGraph;

import com.catan.catanui.entity.SettlementButton;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;


public class RoadGraph {
    private int longestPathLength = 0;
    private Graph<SettlementButton, DefaultEdge> graph;

    public RoadGraph(Graph<SettlementButton, DefaultEdge> graph) {
        this.graph = graph;
    }

    public void computeLongestPath(){
        for (SettlementButton vertex : graph.vertexSet()) {
            HashSet<SettlementButton> visited = new HashSet<>();
            findLongestPath(vertex, visited, 0);
        }
    }

    private void findLongestPath(SettlementButton current, HashSet<SettlementButton> visited, int pathLength) {
        visited.add(current);

        // Update longest path length if this path is longer
        longestPathLength = Math.max(longestPathLength, pathLength);

        for (DefaultEdge edge : graph.edgesOf(current)) {
            SettlementButton target = graph.getEdgeTarget(edge);
            if (target.equals(current)) {
                target = graph.getEdgeSource(edge);
            }
            if (!visited.contains(target)) {
                findLongestPath(target, new HashSet<>(visited), pathLength + 1);
            }
        }
    }

    public int getLongestPathLength() {
        return longestPathLength;
    }
}

package com.catan.catanui.utils;

import java.util.HashSet;
import org.jgrapht.graph.DefaultUndirectedGraph;

import com.catan.catanui.entity.RoadButton;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;


public class RoadGraph {
    private int longestPathLength = 0;
    private Graph<RoadButton, DefaultEdge> graph;

    public RoadGraph(Graph<RoadButton, DefaultEdge> graph) {
        this.graph = graph;
    }

    public void computeLongestPath(){
        for (RoadButton vertex : graph.vertexSet()) {
            HashSet<RoadButton> visited = new HashSet<>();
            findLongestPath(vertex, visited, 0);
        }
    }

    private void findLongestPath(RoadButton current, HashSet<RoadButton> visited, int pathLength) {
        visited.add(current);

        // Update longest path length if this path is longer
        longestPathLength = Math.max(longestPathLength, pathLength);

        for (DefaultEdge edge : graph.edgesOf(current)) {
            RoadButton target = graph.getEdgeTarget(edge);
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

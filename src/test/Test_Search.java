package test;

import graph.Graph;
import graph.GraphNode;
import searches.AStar;

import java.util.List;

/**
 * @author : Evgeniy
 * @project : bestRTS
 * @date : 07/02/16
 * <p>
 * Barebones search test.
 */
public class Test_Search {

    private static GraphNode startNode;
    private static int startX = 0;
    private static int startY = 0;

    private static GraphNode goalNode;
    private static int goalX = 10;
    private static int goalY = 10;

    public static void main(String[] args) {

        //duplicate from CoreEngine
        Graph graph = new Graph();

        for (int x = 0; x < Graph.WIDTH; x++) {
            for (int y = 0; y < Graph.HEIGHT; y++) {
                GraphNode node = new GraphNode(x, y);
                if (x == startX && y == startY) {
                    startNode = node;
                }
                if (x == goalX && y == goalY) {
                    goalNode = node;
                }
                graph.nodeWith(node);
            }
        }

        for (GraphNode aNode : graph.getNodes()) {
            aNode.addNeighbours(graph);
        }

        //graph made, do your test


        List<GraphNode> path = AStar.search(startNode, goalNode);

        for (int x = 0; x < path.size(); x++) {
            System.out.println("Node at: " + path.get(x).getX() + " , " + path.get(x).getY());
        }

    }

}
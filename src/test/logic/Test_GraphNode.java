package test.logic;

import graph.Graph;
import graph.GraphNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by user on 12/03/2016.
 */
public class Test_GraphNode extends Test_Logic{

    private Graph graph = createGraph();

    /**
     * Testing function to check the successors getting mechanism of graph node
     * Check if the list of successors got from the mechanism is correct
     */
    @Test
    public void graphNodeGetSuccessors() {
        GraphNode node = graph.nodeWith(new GraphNode(4, 5));
        ArrayList<GraphNode> successors = (ArrayList<GraphNode>) node.getSuccessors();
        ArrayList<GraphNode> successors_check = new ArrayList<GraphNode>();
        successors_check.add(graph.nodeWith(new GraphNode(3, 5)));
        successors_check.add(graph.nodeWith(new GraphNode(4, 4)));
        successors_check.add(graph.nodeWith(new GraphNode(4, 6)));
        successors_check.add(graph.nodeWith(new GraphNode(5, 5)));

        Assert.assertEquals(successors, successors_check);
    }
}

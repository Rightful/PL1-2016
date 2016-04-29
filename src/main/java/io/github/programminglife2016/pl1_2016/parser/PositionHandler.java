package io.github.programminglife2016.pl1_2016.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handler for nodes to calculate positions on the graph.
 */
public class PositionHandler implements PositionManager {
    /**
     * Spacing between segments in the graph.
     */
    private static final int SPACING = 250;

    /**
     * Factor used to determine node positions.
     */
    private static final int FACTOR = SPACING / 2;


    /**
     * Map containing the DNA seqments.
     */
    private NodeCollection nodeCollection;

    /**
     * Map containing columns of based on Z-index and the segments it contains.
     */
    private Map<Integer, List<Integer>> columns;

    /**
     * Create a PositionHandler.
     * @param nodeCollection Map containing all the segments which positions need to be calculated.
     * @param columns All the columns based on Z-index with the segments they contain.
     */
    public PositionHandler(NodeCollection nodeCollection, Map<Integer, List<Integer>> columns) {
        this.columns = columns;
        this.nodeCollection = nodeCollection;
    }

    /**
     * Calculate the positions of the nodes, and set the positions in each node.
     */
    public void calculatePositions() {
        int currx = 0;
        for (Map.Entry<Integer, List<Integer>> entry : columns.entrySet()) {
            List<Integer> segments = entry.getValue();
            if (segments.size() == 1) {
                nodeCollection.get(segments.get(0)).setXY(currx, 0);
                currx = currx + SPACING;
                continue;
            }
            int boundary = (segments.size() - 1) * FACTOR;
            for (Integer index
                    : segments) {
                nodeCollection.get(index).setXY(currx, boundary);
                boundary = boundary - SPACING;
            }
            currx = currx + SPACING;
        }
    }
}
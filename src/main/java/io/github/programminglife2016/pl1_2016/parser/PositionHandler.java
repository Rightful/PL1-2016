package io.github.programminglife2016.pl1_2016.parser;

/**
 * Handler for nodes to calculate positions on the graph.
 */
public class PositionHandler implements PositionManager {
    /**
     * Spacing between segments in the graph.
     */
    private static final int SPACING = 100;
    private static final String MAIN_GENOME = "MT_H37RV_BRD_V5.ref.fasta";

    /**
     * Map containing the DNA seqments.
     */
    private NodeCollection nodeCollection;
    private IGenome[] genomes;

    /**
     * Create a PositionHandler.
     * @param nodeCollection Map containing all the segments which positions need to be calculated.
     */
    public PositionHandler(NodeCollection nodeCollection, IGenome[] genomes) {
        this.genomes = genomes;
        this.nodeCollection = new NodeList(nodeCollection.getNodes().size());
        for (Node node : nodeCollection) {
            this.nodeCollection.put(node.getId(), node);
        }
    }

    /**
     * Calculate the positions of the nodes, and set the positions in each node.
     */
    public void calculatePositions() {
        int posY = 0;
        for (IGenome genome : genomes) {
            constructBranches(genome, posY);
            posY += 50;
        }
        int breakDebug = 0;
    }


    private void constructBranches(IGenome genome, int posY) {
        Node node;
        for (int nodeId : genome.getNodesIds()) {
            node = nodeCollection.get(nodeId);
            if (node.getCrdGenome().equals(genome.getName())) {//node.getOriGenomes().contains(genome.getName()) || 
                node.setXY(node.getColumn()+100, posY);
            }
        }
    }
}

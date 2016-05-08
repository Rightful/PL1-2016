package io.github.programminglife2016.pl1_2016.parser;

/**
 * Handler for nodes to calculate positions on the graph.
 */
public class PositionHandler implements PositionManager {
    /**
     * Spacing between segments in the graph.
     */
    private static final int SPACING = 100;

    /**
     * Map containing the DNA seqments.
     */
    private NodeCollection nodeCollection;
    private IGenome[] genomes;
//    private List<Mutation> mutations = new ArrayList<>();
//    public NodeCollection nodesToShow;
//    private NodeCollection bubbles;
    /**
     * Create a PositionHandler.
     * @param nodeCollection Map containing all the segments which positions need to be calculated.
     */
    public PositionHandler(NodeCollection nodeCollection, IGenome[] genomes) {
        this.genomes = genomes;
        this.nodeCollection = new NodeList(nodeCollection.getNodes().size());
        int i = 1;
        for (Node node : nodeCollection){//tempNodes) {
            this.nodeCollection.put(i, node);
            i++;
        }
    }

    /**
     * Calculate the positions of the nodes, and set the positions in each node.
     */
    public void calculatePositions() {
        if (genomes != null) {
            int posY = 0;
            for (IGenome genome : genomes) {
                constructBranches(genome, posY);
                posY += 20;
            }
        }
    }

    private void constructBranches(IGenome genome, int posY) {
        Node node;
        for (int nodeId : genome.getNodesIds()) {
            node = nodeCollection.get(nodeId);
            if (node.getCrdGenome().equals(genome.getName())) {
                node.setXY(node.getColumn(), posY);
            }
        }
    }
}
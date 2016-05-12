package io.github.programminglife2016.pl1_2016.parser;

import java.util.HashMap;

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
            HashMap<Integer, Integer> xY = new HashMap<>();
            int posY = 0;
            for (int i = 0; i < genomes.length; i++) {
                constructBranches(i, posY);
                posY += 30;
            }
        }
    }

    private void constructBranches(int genomeId, int posY) {//HashMap<Integer, Integer> xY
        Node node;
        IGenome genome = genomes[genomeId];
        if(genome.getNodesIds().size()>0) {
            int curX = nodeCollection.get(genome.getNodesIds().get(0)).getColumn();
            for (int nodeId : genome.getNodesIds()) {
                node = nodeCollection.get(nodeId);
                if (node.getCrdGenome().equals(genome.getName())) {
                    node.setXY(curX, posY);
                }
//                else{
                    node.getIdPos()[genomeId] = new Tuple(curX, posY);
//                }
//                if(node.getOriGenomes().toString().contains(genome.getName()))
                    curX += node.getData().length();
            }
        }
    }
}
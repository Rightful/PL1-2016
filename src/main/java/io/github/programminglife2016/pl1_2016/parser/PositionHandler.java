package io.github.programminglife2016.pl1_2016.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Mutation> mutations = new ArrayList<>();
    public NodeCollection nodesToShow;
    private NodeCollection bubbles;
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
        if (genomes != null) {
            int posY = 0;
            for (IGenome genome : genomes) {
                constructBranches(genome, posY);
                posY += 50;
            }
        }
    }


    private void constructBranches(IGenome genome, int posY) {
        Node node;
        int lastX = 0, newX, buffer = 0;
        for (int nodeId : genome.getNodesIds()) {
            node = nodeCollection.get(nodeId);
            if (node.getCrdGenome().equals(genome.getName())) {//node.getOriGenomes().contains(genome.getName()) ||
                newX = node.getColumn() + buffer;
                if(newX - lastX < 100 && newX - lastX != 0) {
                    buffer += 100;
                    newX += 100;
                }
                node.setXY(newX, posY);
                lastX = newX;
            }
        }
    }

    public void getMutations() {
        Mutation temp;
        List<Integer> addedPos = new ArrayList<>();
        int totalGenomes = genomes == null ? 0 : genomes.length;
        for (Node node : nodeCollection) {
            if (addedPos.contains(node.getColumn()))
                continue;
            addedPos.add(node.getColumn());
            temp = new Mutation(mutations.size(), node.getColumn(), node.getOriGenomes(), node.getCrdGenome(), totalGenomes);
            temp.setXY(node.getX(), node.getY());
//            if(node.getCrdGenome().contains("MT"))
                temp.getBackLinks().addAll(node.getBackLinks());
//            temp.setData("id: " + node.getId() + "Start: "+node.getColumn()+" Size: "+node.getData().length());
            mutations.add(temp);
        }

        setOtherLinks();
//        mutationCollection = new NodeList(mutations.size());
//        for (int i = 0; i < mutations.size(); i++)
//            mutationCollection.put(i + 1, mutations.get(i));
        setMainLinks();
        createBubbles();
    }

    private void setMainLinks(){
        Node lastNode = null;
        for (Node node : mutations) {//mutationCollection) {
            if(node.getCrdGenome() != null && node.getCrdGenome().contains(".ref")) {
                node.getBackLinks().clear();
                if (lastNode != null)
                    node.addBackLink(lastNode);
                lastNode = node;
            }
        }
    }

    private void setOtherLinks(){
        List<Mutation> tempList = new ArrayList<>();
        for (Mutation node : mutations) {
            node.getBackLinks().clear();
            node.getLinks().clear();
            if(node.getCrdGenome().contains(".ref"))
                continue;
            Mutation temp = new Mutation(mutations.size() + tempList.size());
            temp.setXY(node.getColumn(), 0);
            temp.setData("Mutations: " + node.getData());
            tempList.add(temp);
            node.getBackLinks().add(temp);
        }
        mutations.addAll(tempList);
    }
    // TODO: replace by clustering algorithm
    private void createBubbles(){
        bubbles = new NodeList(100);
        nodesToShow = new NodeList(100);
        List<Mutation> tempBubble = new ArrayList<>();
        int i = 0;
        int id = 0;
        int posX = 0;
        for(Mutation mutation: mutations){
            if(mutation.getY() == 0){
                if(i < 1000){
                    tempBubble.add(mutation);
                    i++;
                }else{
                    createBubble(tempBubble, id, posX);
                    tempBubble.clear();
                    id++;
                    posX += 200;
                    i = 0;
                }
            }
        }

        Node lastNode = null;
        for (Node node : bubbles) {
            if (lastNode != null)
                node.addBackLink(lastNode);
            lastNode = node;
        }
    }
    private void createBubble(List<Mutation> mutations, int id, int posX){
        mutations = mutations.stream().filter(x -> x.getCrdGenome() != null).collect(Collectors.toList());
        if(mutations.size() == 0)
            return;
        Bubble temp = new Bubble(id);//, mutations);
        temp.setXY(posX, 0);
        temp.setData("Mutations: " + mutations.stream().mapToInt(m -> m.getMutations()).sum() +
                " Start: " + mutations.stream().mapToInt(m -> m.getColumn()).min().getAsInt());
        nodesToShow.put(id+1, temp);
        bubbles.put(id+1, new Bubble(id, mutations));
    }
}

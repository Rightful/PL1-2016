package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.Segment;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A bubble represents a higher level node. A bubble can contain multiple segments, or multiple
 * bubbles.
 *
 * @author Ravi Autar and Kamran Tadzjbov
 */
public class Bubble implements Node {
    private int id;
    private int x;
    private int y;
    private transient final Boolean isBubble = true;
    private transient Node startNode;
    private transient Node endNode;
    private transient List<Node> container = new ArrayList<>();
    private transient Set<Node> links = new HashSet<>();
    private transient Set<Node> backLinks = new HashSet<>();
    private transient int containerid;
    private transient int level;
    private transient String data = "";
    private transient int containersize;

    public Bubble(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Create a bubble that encompasses the nodes between startNode and endNode.
     *
     * @param id id of the bubble
     * @param startNode first node of the bubble
     * @param endNode last node of the bubble
     */
    public Bubble(int id, Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.id = id;
    }

    public Bubble (int newId, int zoomLvl, Segment segment){
        this.id = newId;
        this.startNode = segment;
        this.endNode = segment;
        this.level = zoomLvl;
        this.containerid = segment.getContainerId();
        segment.setContainerId(id);
    }

    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setData(String data) {
    }

    @Override
    public void setColumn(int column) {
    }

    @Override
    public void addLink(Node node) {
        endNode.addLink(node);
    }

    @Override
    public void addBackLink(Node node) {
        startNode.addBackLink(node);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public Collection<Node> getLinks() {
        return links;
    }

    @Override
    public Collection<Node> getBackLinks() {
        return backLinks;
    }

    @Override
    public int getColumn() {
        return 0;
    }

    @Override
    public void addGenomes(Collection<String> genomes) {
    }

    @Override
    public Set<String> getGenomes() {
        return this.startNode.getGenomes();
    }

    @Override
    public Node clone() {
        try {
            return (Bubble) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getContainerId() {
        return this.containerid;
    }

    @Override
    public void setContainerId(int containerid) {
        this.containerid = containerid;
    }

    @Override
    public int getZoomLevel() {
        return this.level;
    }

    @Override
    public void setZoomLevel(int level) {
        this.level = level;
    }

    @Override
    public int getContainerSize() {
        return this.containersize;
    }

    @Override
    public void setContainerSize(int size) {
        this.containersize = size;
    }

    @Override
    public boolean isBubble(){
        return isBubble;
    }

    @Override
    public Node getStartNode() {
        return startNode;
    }

    @Override
    public Node getEndNode() {
        return endNode;
    }

    @Override
    public void setEndNode(Node node) {
        this.endNode = node;
    }

    public void setStartNode(Node node) {
        this.startNode = node;
    }

    @Override
    public List<Node> getContainer() {
        return container;
    }

    public static Node getBubble(List<Node> bubbles, int containerId) {
        return bubbles.stream().filter(x -> x.getId() == containerId).findFirst().get();
    }

    /**
     * Return highest level bubble container of the leaf node in the given bubble if it exists,
     * else creates new bubble with the startNode == endNode.
     * @param newId new Id that will be assigned to the bubble if it is just created
     * @param leaf
     * @param bubbles
     * @param boundZoom
     * @return
     */
    public static Node getBestParentNode(int newId, Node leaf, Collection<Node> bubbles, int boundZoom, boolean start){
        if(leaf instanceof Segment) {
            final Node tempLeaf = leaf;
            final int leafId = leaf.getId();
            Optional<Node> bubble;
            if(start)
            bubble = bubbles.stream().filter(x -> x.getStartNode().getId() == leafId// || x.getEndNode().getId() == leafId //
//            Optional<Node> bubble = bubbles.stream().filter(x -> x.getContainer().contains(tempLeaf)
            ).findFirst();
            else
                bubble = bubbles.stream().filter(x -> x.getEndNode().getId() == leafId).findFirst();
            if(bubble.isPresent())
                leaf = bubble.get();
            else {
                leaf = new Bubble(newId, boundZoom, (Segment) leaf);
                newId++;
            }
        }
        Node bestParent = leaf;
        for (Node newCont : bubbles){
            if(newCont.getId() == bestParent.getContainerId()) {
                if(newCont.getZoomLevel() >= boundZoom)
                    bestParent = newCont;
            }
        }
        if(bestParent.getId() != leaf.getId())
            return getBestParentNode(newId, bestParent, bubbles, boundZoom, start);
        return bestParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bubble bubble = (Bubble) o;
        return id == bubble.id;
    }

    public String toString() {
        return "Bubble{" +
                "id=" + id +
                ", startNode=" + startNode +
                ", container=" + container.stream().map(x -> x.getId()).collect(Collectors.toList()) +
                ", endNode=" + endNode +
                ", containerSize=" + containersize +
                '}';
    }
}

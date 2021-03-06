package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.metadata.Subject;
import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.Segment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bubble class that contains segments or nested bubbles.
 *
 * @author Ravi Autar
 */
public class Bubble implements Node {
    private int id;
    private int x;
    private int y;
    private transient Boolean isBubble = true;
    private transient Node startNode;
    private transient Node endNode;
    private transient List<Node> container = new ArrayList<>();
    private transient Set<Node> links = new HashSet<>();
    private transient Set<Node> backLinks = new HashSet<>();
    private transient int containerid;
    private transient int level;
    private transient String data = "";
    private transient int containersize;

    /**
     * Constructor for a bubble with start and endnode.
     * @param startNode startnode of the bubble.
     * @param endNode endnode of the bubble.
     */
    public Bubble(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    /**
     * Constructor for a bubble with id, start and endnode.
     * @param id id of the bubble.
     * @param startNode startnode of the bubble.
     * @param endNode endnode of the bubble.
     */
    public Bubble(int id, Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.id = id;

    }

    /**
     * Constructor for a bubble with id, start and endnode.
     * @param newId id of the bubble.
     * @param zoomLvl startnode of the bubble.
     * @param segment endnode of the bubble.
     */
    public Bubble(int newId, int zoomLvl, Segment segment) {
        this.id = newId;
        this.startNode = segment;
        this.endNode = segment;
        this.level = zoomLvl;
        this.containerid = segment.getContainerId();
        segment.setContainerId(id);
//        this.links.addAll(segment.getLinks());
//        this.backLinks.addAll(segment.getBackLinks());
    }

    /**
     * Set the x and y coordinates of the node.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    @Override
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate.
     *
     * @return the x coordinate
     */
    @Override
    public int getX() {
        return (startNode.getX() + endNode.getX()) / 2;
    }

    /**
     * Get the y coordinate.
     *
     * @return the y coordinate
     */
    @Override
    public int getY() {
        return (startNode.getY() + endNode.getY()) / 2;
    }

    /**
     * Set the data of the node.
     *
     * @param data data of the node
     */
    @Override
    public void setData(String data) {
    }

    /**
     * Set the file-specified column of the node.
     *
     * @param column column of the node
     */
    @Override
    public void setColumn(int column) {
    }

    /**
     * Add a link to another node, which results in a directed edge when displayed.
     *
     * @param node connected node
     */
    @Override
    public void addLink(Node node) {
        endNode.addLink(node);
    }

    /**
     * Add a predecessor node.
     *
     * @param node connected node
     */
    @Override
    public void addBackLink(Node node) {
        startNode.addBackLink(node);
    }

    /**
     * Get the id of the node.
     *
     * @return id of the node
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Get the data of the node.
     *
     * @return data of the node
     */
    @Override
    public String getData() {
        return null;
    }

    /**
     * Get the connected nodes of this node.
     *
     * @return links of this node
     */
    @Override
    public Collection<Node> getLinks() {
        return links;
    }

    /**
     * Get the predecessors of this node.
     *
     * @return links of this node
     */
    @Override
    public Collection<Node> getBackLinks() {
        return backLinks;
    }

    /**
     * Get the column of this node.
     *
     * @return the column of this node
     */
    @Override
    public int getColumn() {
        return 0;
    }

    /**
     * Add the genomes this segment belongs to.
     *
     * @param genomes the genomes this segment belongs to
     */
    @Override
    public void addGenomes(Collection<Subject> genomes) {

    }

    /**
     * Get the genomes this segment belongs to.
     *
     * @return the genomes this segment belongs to
     */
    @Override
    public Set<String> getGenomes() {
        return this.startNode.getGenomes();
    }

    /**
     * Get the subjects this segment belongs to.
     *
     * @return the subjects this segment belongs to
     */
    @Override
    public Set<Subject> getSubjects() {
        return this.startNode.getSubjects();
    }

    /**
     * Make a shallow clone of this node. Only the links are cloned one level more.
     *
     * @return the cloned node.
     */
    @Override
    public Node clone() {
        return null;
    }

    /**
     * Return the id of the container the node resides in.
     *
     * @return id of the container.
     */
    @Override
    public int getContainerId() {
        return this.containerid;
    }

    /**
     * Set the id of the container the node resides in.
     *
     * @param containerid id of the bubble in which current node is located
     */
    @Override
    public void setContainerId(int containerid) {
        this.containerid = containerid;
    }

    /**
     * Return the zoomlevel the node resides in.
     *
     * @return the depth of the level.
     */
    @Override
    public int getZoomLevel() {
        return this.level;
    }

    /**
     * Set the zoom level of the node
     */
    @Override
    public void setZoomLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the size of the container.
     *
     * @return size of the container.
     */
    @Override
    public int getContainerSize() {
        return this.containersize;
    }

    /**
     * Sets the set size of the bubble.
     *
     * @param size size of the bubble.
     */
    @Override
    public void setContainerSize(int size) {
        this.containersize = size;
    }

    /**
     * Return whether the node is a bubble.
     *
     * @return true if it is a bubble.
     */
    @Override
    public Boolean isBubble() {
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

    /**
     * Set startnode of the bubble.
     * @param node new start node of the bubble.
     */
    public void setStartNode(Node node) {
        this.startNode = node;
    }

    /**
     * Get the nodes if the nodes has a container.
     *
     * @return List of the nodes the node contains.
     */
    @Override
    public List<Node> getContainer() {
        return container;
    }


    /** Check whether a certian bubble equals this bubble.
     * @param o Object compared to
     * @return true if this equals the object, false otherwise.
     */
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

    /**
     * Returns the hashcode of the bubble.
     * @return Hashcode of the bubble.
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 1 * result + x;
        return result;
    }

    /**
     * Returns string represtation of the bubble.
     * @return String representation of Bubble.
     */
    public String toString() {
        return "Bubble{"
                + "id=" + id
                + ", startNode=" + startNode
                + ", container=" + container.stream()
                                    .map(x -> x.getId()).collect(Collectors.toList())
                + ", endNode=" + endNode
                + ", containerSize=" + containersize
                + '}';
    }
}
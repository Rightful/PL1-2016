package io.github.programminglife2016.pl1_2016.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Data structure for representing a DNA sequence
 */
public class Bubble implements Node {
    /**
     * Id of DNA segment.
     */
    private int id;

    /**
     * z-index of Segment in graph.
     */
    private int column;

    /**
     * Links to other DNA segments in the graph.
     */
    private List<Node> links;
    private List<Node> backLinks;
    private List<Mutation> mutationsList;


    private String data;
    /**


    /**
     * x position of the segment in the graph.
     */
    private int x;

    /**
     * y position of the segment in the graph.
     */
    private int y;

    /**
     * Create segment with id and sequence data.
     * @param id identifier of this segment.
     */
    public Bubble(int id, List<Mutation> mutationsList) {
        this.id = id;
        this.links = new ArrayList<Node>();
        this.backLinks = new ArrayList<Node>();
        this.mutationsList = mutationsList;
        data = getData();
    }

    /**
     * Create segment with id.
     * @param id identifier of this segment.
     */
    public Bubble(int id) {
        this.id = id;
        this.links = new ArrayList<Node>();
        this.backLinks = new ArrayList<Node>();
        this.mutationsList = new ArrayList<>();
    }

    /**
     * Add a link from this segment to other segment.
     * @param other segment to link to.
     */
    public void addLink(Node other) {
        this.links.add(other);
    }

    /**
     * Add a predecessor node.
     *
     * @param node connected node
     */
    @Override
    public void addBackLink(Node node) {
        backLinks.add(node);
    }

    /**
     * Get sequence data of this segment.
     * @return string of sequence data.
     */
    public String getData() {
        return data;
    }

    /**
     * Get links to other segments in the graph.
     * @return list of links.
     */
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
     * Get the id if this segment.
     * @return id of this segment.
     */
    public int getId() {
        return id;
    }

    /**
     * Get index of column in graph of this DNA segment.
     * @return index of column starting at 0.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Set column index if this DNA segment.
     * @param column index in graph.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Set both the x and the y position of the segment.
     * @param x x-position of the segment.
     * @param y y-position of the segment.
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x value of the segment.
     * @return x value of the segment.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y value of the segment.
     * @return y value of the segment
     */
    public int getY() {
        return y;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Get genomes of origin of this segment.
     * @return list of genomes of origin.
     */
    public List<String> getOriGenomes() {
        return null;
    }

    /**
     * Get genome supplying the coordinates of this segment.
     * @return genome name supplying the coordinates.
     */
    public String getCrdGenome() { return null; }

    /**
     * Set genome supplying the coordinates of this segment.
     *
     * @param crdGenome genome name supplying the coordinates
     */
    public void setCrdGenome(String crdGenome) { }



    public List<Mutation> getMutationsList() {
        return mutationsList;
    }

    public void setMutationsList(List<Mutation> mutationsList) {
        this.mutationsList = mutationsList;
    }

    /**
     * Return string representation of segment.
     * @return string representing segment.
     */
    @Override
    public String toString() {
        return String.format("Segment{id=%d, x=%d, y=%d, column=%d}", id, x, y, column);
    }
}
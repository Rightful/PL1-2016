package io.github.programminglife2016.pl1_2016.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Data structure for representing a DNA sequence
 */
public class Mutation implements Node {
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


    private String data;
    /**
     * Genomes of origin.
     */
    private List<String> oriGenomes;

    /**
     * Genome supplying the coordinates.
     */
    private String crdGenome;


    /**
     * x position of the segment in the graph.
     */
    private int x;

    /**
     * y position of the segment in the graph.
     */
    private int y;

    private int mutations;
    /**
     * Create segment with id and sequence data.
     * @param id identifier of this segment.
     * @param column index of this segment.
     */
    public Mutation(int id, int column, List<String> oriGenomes, String crdGenome, int genomesAmount) {
        this.id = id;
        this.column = column;
        this.links = new ArrayList<Node>();
        this.backLinks = new ArrayList<Node>();
        this.oriGenomes = oriGenomes;
        this.crdGenome = crdGenome;
        mutations = genomesAmount - oriGenomes.size();
        data = getData();
    }

    /**
     * Create segment with id.
     * @param id identifier of this segment.
     */
    public Mutation(int id) {
        this.id = id;
        this.links = new ArrayList<Node>();
        this.backLinks = new ArrayList<Node>();
        this.oriGenomes = new ArrayList<String>();
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
        return "Genome: "+crdGenome+"\\nMutations: " + mutations + "\\nUnchanged: " + getUnchanged();
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
        return oriGenomes;
    }

    /**
     * Get genome supplying the coordinates of this segment.
     * @return genome name supplying the coordinates.
     */
    public String getCrdGenome() { return crdGenome; }

    /**
     * Set genome supplying the coordinates of this segment.
     *
     * @param crdGenome genome name supplying the coordinates
     */
    public void setCrdGenome(String crdGenome) { this.crdGenome = crdGenome; }


    public int getMutations() {
        return mutations;
    }


    public void setMutations(int mutations) {
         this.mutations = mutations;
    }


    public int getUnchanged() {
        return oriGenomes.size()-1;
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

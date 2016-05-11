package io.github.programminglife2016.pl1_2016.parser.nodes;

import java.util.Collection;

/**
 * An object that represents a displayable bubble/segment.
 */
public interface Node {
    /**
     * Set the x and y coordinates of the node.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    void setXY(int x, int y);

    /**
     * Get the x coordinate.
     *
     * @return the x coordinate
     */
    int getX();

    /**
     * Get the y coordinate.
     *
     * @return the y coordinate
     */
    int getY();

    /**
     * Set the data of the node.
     *
     * @param data data of the node
     */
    void setData(String data);

    /**
     * Set the file-specified column of the node.
     *
     * @param column column of the node
     */
    void setColumn(int column);

    /**
     * Add a link to another node, which results in a directed edge when displayed.
     *
     * @param node connected node
     */
    void addLink(Node node);

    /**
     * Add a predecessor node.
     *
     * @param node connected node
     */
    void addBackLink(Node node);

    /**
     * Get the id of the node.
     *
     * @return id of the node
     */
    int getId();

    /**
     * Get the data of the node.
     *
     * @return data of the node
     */
    String getData();

    /**
     * Get the connected nodes of this node.
     *
     * @return links of this node
     */
    Collection<Node> getLinks();

    /**
     * Get the predecessors of this node.
     *
     * @return links of this node
     */
    Collection<Node> getBackLinks();

    /**
     * Get the column of this node.
     *
     * @return the column of this node
     */
    int getColumn();

    /**
     * Calculate the position of this node, and the sibling nodes (in the case of a snip). Indels
     * are not yet accounted for.
     *
     * @param nodeCollection collection of all nodes to be positioned
     * @param processed the positioned nodes
     * @param verticalSpacing spacing between snip siblings
     * @param currx the x-coordinate of this node.
     */
    void calculatePosition(NodeCollection nodeCollection, Collection<Node> processed,
                           int verticalSpacing, int currx);

    /**
     * If this node is part of an indel, then move the node down.
     *
     * @param spacing vertical distance to move indel node down
     */
    void correctIndelPosition(int spacing);
}
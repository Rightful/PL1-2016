package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by ravishivam on 16-5-16.
 */
public class Bubble implements Node {

    private Node startNode;

    private Node endNode;

    private List<Node> container = new ArrayList<>();

    public Bubble(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    @Override
    public void setXY(int x, int y) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
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
        return 0;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public Collection<Node> getLinks() {
        return endNode.getLinks();
    }

    @Override
    public Collection<Node> getBackLinks() {
        return startNode.getBackLinks();
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
        return null;
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public void calculatePosition(NodeCollection nodeCollection, Collection<Node> processed, int verticalSpacing, int currx) {

    }

    @Override
    public void correctIndelPosition(int spacing) {

    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public List<Node> getContainer() {
        return container;
    }
}

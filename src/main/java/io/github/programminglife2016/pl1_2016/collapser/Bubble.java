package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeCollection;

import java.util.*;

/**
 * Created by ravishivam on 16-5-16.
 */
public class Bubble implements Node {
    private int id;
    private int x;
    private int y;
    private Node startNode;
    private Node endNode;
    private List<Node> container = new ArrayList<>();
    private Set<Node> links = new HashSet<>();
    private Set<Node> backLinks = new HashSet<>();
    private int containerid;
    private int level;

    public Bubble(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Bubble(int id, Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.id = id;
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
    public void correctIndelPosition(int spacing) {

    }

    public Node getStartNode() {
        return startNode;
    }

    public int getLevel() {
        return this.level;
    }


    public Node getEndNode() {
        return endNode;
    }

    public List<Node> getContainer() {
        return container;
    }
}

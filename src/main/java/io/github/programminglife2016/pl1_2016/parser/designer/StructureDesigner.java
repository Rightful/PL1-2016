package io.github.programminglife2016.pl1_2016.parser.designer;

import io.github.programminglife2016.pl1_2016.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kamran Tadzjibov on 08.05.2016.
 */
public class StructureDesigner implements Designer {

    private NodeCollection nodeCollection;
    private IGenome[] genomes;
    private List<Node> nodes = new ArrayList<>();
    private NodeCollection nodesToShow;
    private NodeCollection bubbles;

    public StructureDesigner(NodeCollection nodeCollection, IGenome[] genomes){
        PositionHandler positionHandler = new PositionHandler(nodeCollection, genomes);
        positionHandler.calculatePositions();
        this.nodeCollection = nodeCollection;
        this.genomes = genomes;
    }

    public NodeCollection getNodesToShow(){
        return nodesToShow;
    }

    public void design(){
//        getMutations();
        linkMutationsToMain();
        nodes = nodeCollection.getNodes().stream().collect(Collectors.toList());
        nodesToShow = new NodeList(nodes.size());//nodeCollection;//
        Node tempNode, currentNode;
//        for(int i = 0; i < genomes.length; i++) {
        int i = 8;
            for (int nodeId : genomes[i].getNodesIds()) {// genomes[9].getNodesIds()) {
                currentNode = nodeCollection.get(nodeId);
                tempNode = new Segment(getUniqueId(nodeId, i), currentNode.getData(), currentNode.getColumn(), currentNode.getOriGenomes(), currentNode.getCrdGenome(), genomes.length);
                tempNode.setData(currentNode.getData());
                tempNode.setXY(currentNode.getIdPos()[i].getItem1(), currentNode.getIdPos()[i].getItem2());
                tempNode.getBackLinks().addAll(currentNode.getBackLinks());
                nodesToShow.put(nodeId, tempNode);
            }
//        }
//        createBubbles();
//        nodesToShow = new NodeList(mutations.size());
//        for (int i = 0; i < mutations.size(); i++)
//            nodesToShow.put(i + 1, mutations.get(i));
    }

    private String getUniqueId(int a, int b){
        return " "+(0.5*(a+b)*(a+b+1)+b);
    }

    private void linkInMainBranch(){
        Node lastNode = null;
        for (Node node : nodes) {//mutationCollection) {
            if(node.getCrdGenome() != null && node.getCrdGenome().contains(".ref")) {
                node.getBackLinks().clear();
                if (lastNode != null)
                    node.addBackLink(lastNode);
                lastNode = node;
            }
        }
    }

    private void linkMutationsToMain(){
        List<Node> nodesProjections = new ArrayList<>();
        for (Node node : nodes) {
            node.getBackLinks().clear();
            node.getLinks().clear();
            if(node.getCrdGenome().contains(".ref"))
                continue;
            Node projection = new Segment(nodes.size() + " " + nodesProjections.size(), genomes.length);
            projection.setXY(node.getX(), 0);
            projection.setData("Mutated genomes: " + node.getData());
//            projection.addLink(node);
            nodesProjections.add(projection);
            node.getBackLinks().add(projection);
        }
        nodes.addAll(nodesProjections);
        linkInMainBranch();
    }

    private void createBubbles(){
        collapseHorizontally();
    }

    // TODO: replace by clustering algorithm
    private void collapseHorizontally(){
        bubbles = new NodeList(1000);
        nodesToShow = new NodeList(1000);
        List<Node> tempBubble = new ArrayList<>();
        int i = 0;
        int id = 0;
        int posX = 0;
        int counter = 0;
        int nodesPerBubble = nodes.stream().filter(x -> x.getCrdGenome() != null && x.getY() == 0)
                .collect(Collectors.toList()).size() / 6;
        for(Node mutation: nodes){
            if(mutation.getY() == 0){
                if(i < nodesPerBubble){
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
            counter++;
        }

        connectBackLinks(bubbles);
        connectBackLinks(nodesToShow);
    }

    private void createBubble(List<Node> mutations, String id, int posX){
        mutations = mutations.stream().filter(x -> x.getCrdGenome() != null).collect(Collectors.toList());
        if(mutations.size() == 0)
            return;
        List<Node> mainBranch = mutations.stream().filter(m -> m.getCrdGenome().contains(".ref")).collect(Collectors.toList());
        int startPos;//, endPos;
        if(mainBranch != null && mainBranch.size() > 0) {
            startPos = mainBranch.stream().mapToInt(m -> m.getColumn()).min().getAsInt();
//            endPos = mainBranch.stream().mapToInt(m -> m.getColumn() + m.).max().getAsInt();
        }
        else {
            startPos = mutations.stream().mapToInt(m -> m.getColumn()).min().getAsInt();
        }
        Bubble temp = new Bubble(id);//, mutations);
        temp.setXY(posX, 0);
        temp.setData("Mutations: " + mutations.stream().mapToInt(m -> genomes.length - m.getOriGenomes().size()).sum() +
                " Start: " + startPos);
        nodesToShow.put(id+1, temp);
        bubbles.put(id+1, new Bubble(id, mutations));
    }

    private void connectBackLinks(NodeCollection nodes){
        Node lastNode = null;
        for (Node node : nodes) {
            if (lastNode != null)
                node.addBackLink(lastNode);
            lastNode = node;
        }
    }
}

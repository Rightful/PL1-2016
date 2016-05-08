package io.github.programminglife2016.pl1_2016.parser.designer;

import io.github.programminglife2016.pl1_2016.parser.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kamran Tadzjibov on 08.05.2016.
 */
public class StructureDesigner implements IDesigner {

    private NodeCollection nodeCollection;
    private IGenome[] genomes;
    private List<Mutation> mutations = new ArrayList<>();
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
        getMutations();
        linkMutationsToMain();
        createBubbles();
//        nodesToShow = new NodeList(mutations.size());
//        for (int i = 0; i < mutations.size(); i++)
//            nodesToShow.put(i + 1, mutations.get(i));
    }

    private void getMutations() {
        Mutation temp;
        List<Integer> addedPos = new ArrayList<>();
        int totalGenomes = genomes == null ? 0 : genomes.length;
        boolean isNotRefGenome;
        for (Node node : nodeCollection) {
            isNotRefGenome =  !(node.getCrdGenome() != null && node.getCrdGenome().contains(".ref"));
            if (isNotRefGenome && addedPos.contains(node.getX()))
                continue;
            addedPos.add(node.getX());
            temp = new Mutation(mutations.size(), node.getX(), node.getOriGenomes(), node.getCrdGenome(), totalGenomes);
            temp.setXY(node.getX(), node.getY());
            temp.getBackLinks().addAll(node.getBackLinks());
            mutations.add(temp);
        }
    }

    private void linkInMainBranch(){
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

    private void linkMutationsToMain(){
        List<Mutation> mutationsProjections = new ArrayList<>();
        for (Mutation node : mutations) {
            node.getBackLinks().clear();
            node.getLinks().clear();
            if(node.getCrdGenome().contains(".ref"))
                continue;
            Mutation projection = new Mutation(mutations.size() + mutationsProjections.size());
            projection.setXY(node.getX(), 0);
            projection.setData("Mutated genomes: " + node.getData());
//            projection.addLink(node);
            mutationsProjections.add(projection);
            node.getBackLinks().add(projection);
        }
        mutations.addAll(mutationsProjections);
        linkInMainBranch();
    }

    private void createBubbles(){
        collapseHorizontally();
    }

    // TODO: replace by clustering algorithm
    private void collapseHorizontally(){
        bubbles = new NodeList(1000);
        nodesToShow = new NodeList(1000);
        List<Mutation> tempBubble = new ArrayList<>();
        int i = 0;
        int id = 0;
        int posX = 0;
        int counter = 0;
        int nodesPerBubble = mutations.stream().filter(x -> x.getCrdGenome() != null && x.getY() == 0)
                .collect(Collectors.toList()).size() / 6;
        for(Mutation mutation: mutations){
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

    private void createBubble(List<Mutation> mutations, int id, int posX){
        mutations = mutations.stream().filter(x -> x.getCrdGenome() != null).collect(Collectors.toList());
        if(mutations.size() == 0)
            return;
        List<Mutation> mainBranch = mutations.stream().filter(m -> m.getCrdGenome().contains(".ref")).collect(Collectors.toList());
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
        temp.setData("Mutations: " + mutations.stream().mapToInt(m -> m.getMutations()).sum() +
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

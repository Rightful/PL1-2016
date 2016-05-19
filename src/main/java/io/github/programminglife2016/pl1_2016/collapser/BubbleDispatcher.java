package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeCollection;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeMap;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static javax.swing.text.html.HTML.Tag.LI;


public class BubbleDispatcher {

    private List<Node> bubbleCollection;

    private HashMap<Node, Node> endToBubble;

//    private Map<Integer, NodeCollection> levelCollection;

    public BubbleDispatcher(NodeCollection collection) {
        BubbleCollapser collapser = new BubbleCollapser(collection);
        collapser.collapseBubbles();
        this.bubbleCollection = collapser.getBubbles();
//        levelCollection = new HashMap<>();
//        endToBubble = new HashMap<>();
        initDispatcher();
    }

    private void initDispatcher() {
        for (int i = 0; i < bubbleCollection.size(); i++) {
            Node bubble = bubbleCollection.get(i);
            bubble.setContainerSize(getBubbleSize(bubble));
//            int currlevel = bubble.getZoomLevel();
//            if (!levelCollection.containsKey(currlevel)) {
//                levelCollection.put(currlevel, new NodeMap());
            }
//            levelCollection.get(currlevel).put(bubble.getId(), bubble);
//        }
    }

    public int getBubbleSize(Node bubble) {
        if (bubble.getContainer().size() == 0) {
            return 1;
        }
        else {
            int size = 2;
            for (int i = 0; i < bubble.getContainer().size(); i++) {
                size += getBubbleSize(bubble.getContainer().get(i));
            }
            return size;
        }
    }
    public NodeCollection getLevelBubbles(int level, int threshold) {
        List<Node> filtered = bubbleCollection.stream().filter(x -> x.getContainerSize() <= threshold).collect(Collectors.toList());
        for (Node bubble : filtered) {
            if (needReplace(bubble.getLinks(), filtered)) {
//                bubble.getLinks().addAll(bubble.getEndNode().getLinks().stream().map(x -> );
                Collection<Node> newlinks = new HashSet<>();
                for (Node endNodelink : bubble.getEndNode().getLinks()) {
                    Optional<Node> link = filtered.stream().filter(y -> y.getId() == endNodelink.getContainerId()).findFirst();
                    if(link.isPresent())
                        newlinks.add(link.get());
                }
                if(!newlinks.isEmpty()) {
                    bubble.getLinks().clear();
                    bubble.getLinks().addAll(newlinks);
                }
            }
            System.out.println("Id: " + bubble.getId() + " Contains:" + bubble.getContainer().stream().map(x -> x.getId()).collect(Collectors.toList()));
            System.out.println("Links: " + bubble.getLinks().stream().collect(Collectors.toList()));
            System.out.println("StartNode: " + bubble.getStartNode());
            System.out.println();
        }
        return listAsNodeCollection(filtered);
    }

    private boolean needReplace(Collection<Node> links, List<Node> bubble) {
        for (Node link : links) {
            if (bubble.contains(link)) {
                continue;
            }
            return true;
//            int containerId = link.getContainerId();
        }
        return false;
    }
//    public NodeCollection getLevelBubbles(int level, int threshold) {
//        List<Node> res = new ArrayList<>(levelCollection.get(level).values());
//        List<Node> nextLevel = new ArrayList<>(levelCollection.get(level+1).values());
//
//        for(Node node : res) {
//            if (node.getContainerSize() > threshold) {
//                Node start = node.getStartNode();
//                for (Node startchildren : start.getLinks()) {
//                    int idcontainer = startchildren.getContainerId();
//
//                }
//
//                res.remove(node);
//            }
//        }
//        return listAsNodeCollection(res);
////        return levelCollection.get(level);
//    }

    private NodeCollection listAsNodeCollection(List<Node> res) {
        NodeCollection collection = new NodeMap();
        for(Node node: res) {
            collection.put(node.getId(), node);
        }
        return collection;
    }

}

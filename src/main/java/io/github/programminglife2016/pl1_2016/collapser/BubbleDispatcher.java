package io.github.programminglife2016.pl1_2016.collapser;

import io.github.programminglife2016.pl1_2016.parser.nodes.Node;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeCollection;
import io.github.programminglife2016.pl1_2016.parser.nodes.NodeMap;

import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.text.html.HTML.Tag.HEAD;


/**
 * Dispatcher that determines which bubbles are more important to visualize at the given
 * (zoom) level the user is in. It un-collapses a bubble into it's smaller components if it
 * contains valuable information. It leaves the bubble intact if the information contain in
 * the bubble is not relevant for the given (zoom) level.
 */
public class BubbleDispatcher {
    public List<Node> bubbleCollection;

    private NodeCollection collection;

    /**
     * Collapse the collection, and construct a BubbleDispatcher.
     *
     * @param collection original dataset
     */
    public BubbleDispatcher(NodeCollection collection) {
        BubbleCollapser collapser = new BubbleCollapser(collection);
        collapser.collapseBubbles();
        this.bubbleCollection = collapser.getBubbles();
        this.collection = collection;
        initDispatcher();
    }

    private void initDispatcher() {
        for (int i = 0; i < bubbleCollection.size(); i++) {
            Node bubble = bubbleCollection.get(i);
            bubble.setContainerSize(getBubbleSize(bubble));
        }
    }

    /**
     * Return the number of segments in this bubble.
     *
     * @param bubble bubble to calculate the size of
     * @return the number of segments in this bubble
     */
    public int getBubbleSize(Node bubble) {
        if (bubble.getContainer().size() == 0) {
            return 1;
        } else {
            int size = 2;
            for (int i = 0; i < bubble.getContainer().size(); i++) {
                size += getBubbleSize(bubble.getContainer().get(i));
            }
            return size;
        }
    }

    /**
     * Get the bubbles on a certain level, and expand the bubbles with size greater than the
     * threshold.
     *
     * @param level level of the bubble
     * @param threshold size threshold
     * @return the bubbles that comply with level and threshold.
     */
    public NodeCollection getLevelBubbles(int level, int threshold) {
        List<Node> filtered = bubbleCollection.stream()
                .filter(x -> x.getContainerSize() <= threshold)
                .collect(Collectors.toList());
        List<Node> tempList = new ArrayList<>();
        for (Node bubble : filtered) {
            if (needReplace(bubble.getLinks(), filtered)) {
                Collection<Node> newlinks = new HashSet<>();
                for (Node endNodelink : bubble.getEndNode().getLinks()) {
                    Optional<Node> link = filtered.stream().filter(y -> y.getId()
                            == endNodelink.getContainerId()).findFirst();
                    if (link.isPresent()) {
                        newlinks.add(link.get());
                    }
                }
                if (!newlinks.isEmpty()) {
                    bubble.getLinks().clear();
                    bubble.getLinks().addAll(newlinks);
                }
            }
            List<Node> intersection = new ArrayList<>(filtered);
            intersection.retainAll(bubble.getLinks());
            if (intersection.size() == 0) {
                List<Node> x = getMissingNodes(bubble.getLinks());
                tempList.addAll(x);
            }
        }
        filtered.addAll(tempList);
        return listAsNodeCollection(filtered);
    }

    private List<Node> getMissingNodes(Collection<Node> links) {
        return links.stream().filter(node -> !bubbleCollection.contains(node))
                .collect(Collectors.toList());
    }

    private boolean needReplace(Collection<Node> links, List<Node> bubble) {
        for (Node link : links) {
            if (bubble.contains(link)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private NodeCollection listAsNodeCollection(List<Node> res) {
        NodeCollection collection = new NodeMap();
        for (Node node : res) {
            collection.put(node.getId(), node);
        }
        return collection;
    }
}

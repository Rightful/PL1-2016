package io.github.programminglife2016.pl1_2016.parser.nodes;

import io.github.programminglife2016.pl1_2016.parser.JsonSerializable;

import java.util.Collection;
import java.util.Iterator;

/**
 * A data structure that represents the segments.
 */
public interface NodeCollection extends JsonSerializable, Iterable<Node> {
    /**
     * Add a segment to the collection.
     *
     * @param id      id of the segment
     * @param node the actual segment
     * @return the segment parameter
     */
    Node put(Integer id, Node node);

    /**
     * Get a segment by id.
     *
     * @param id id of the segment
     * @return segment
     */
    Node get(Object id);

    /**
     * Checks if the collection contains a particular key.
     *
     * @param id id
     * @return true if the collection contains an item with this id, otherwise false
     */
    boolean containsKey(Object id);

    /**
     * Return all segments.
     * @return all segments.
     */
    Collection<Node> getNodes();

    /**
     * Removes a node from the collection.
     * @param node Node to be removed.
     */
    void removeNode(Node node);

    /**
     * Iterator for the collection.
     * @return iterator
     */
    default Iterator<Node> iterator() {
        return getNodes().iterator();
    }

    /**
     * Get the size of the nodecollection
     * @return size of the nodeCollection
     */
    default int size() {
        return getNodes().size();
    }
}

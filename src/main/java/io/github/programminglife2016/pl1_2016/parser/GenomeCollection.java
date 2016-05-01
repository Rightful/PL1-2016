package io.github.programminglife2016.pl1_2016.parser;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by adam on 4/29/16.
 */
public interface GenomeCollection {
    /**
     * Add a Genome to the collection.
     *
     * @param id      id of the genome
     * @param genome the actual genome
     * @return the genome parameter
     */
    Genome put(String id, Genome genome);

    /**
     * Add a segment to the genome with id id.
     *
     * gen@param id      id of the genome
     * @param segment the actual genome
     * @return the genome parameter
     */
    Segment put(String id, Segment segment);

    /**
     * Get a genome by id.
     *
     * @param id id of the genome
     * @return genome
     */
    Genome get(Object id);

    /**
     * Checks if the collection contains a particular key.
     *
     * @param id id
     * @return true if the collection contains an item with this id, otherwise false
     */
    boolean containsKey(Object id);

    /**
     * Return all genomes.
     * @return all genomes.
     */
    Collection<Genome> getGenomes();

    /**
     * Iterator for the collection.
     * @return iterator
     */
    default Iterator<Genome> iterator() {
        return getGenomes().iterator();
    }
}

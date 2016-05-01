package io.github.programminglife2016.pl1_2016.parser;

import java.util.Collection;

/**
 * Created by adam on 4/29/16.
 */
public interface Genome {
    
    void add(Segment segment);

    Collection<Segment> getCollection();

    String getId();

    String toString(Integer size);

}

package io.github.programminglife2016.pl1_2016.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by adam on 4/30/16.
 */
public class SimpleGenome  implements Genome{
    /**
     * Id of genome.
     */
    private String id;

    /**
     * Segments in this genome
     */
    private List<Segment> segments;

    public SimpleGenome(String id){
        this.id = id;
        this.segments = new ArrayList<Segment>();
    }

    public void add(Segment segment) {
        segments.add(segment);
    }

    public Collection<Segment> getCollection() {
        return segments;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Segment segment: segments) {
            sb.append(segment.getId()).append("\t");
        }
        return sb.toString();
    }

    public String toString(Integer maxSizeOfSegments){
        StringBuilder sb = new StringBuilder();
        String[] segmentsarray = new String[maxSizeOfSegments+1];
        for (int i = 0; i < maxSizeOfSegments; i++) {
            segmentsarray[i] = " ";
        }
        for (Segment s:
             segments) {
            segmentsarray[s.getId()] = Integer.toString(s.getId());
        }
        for (int i = 0; i < maxSizeOfSegments; i++) {
            sb.append(segmentsarray[i] + "\t\t");
        }

        return sb.toString();
    }
}

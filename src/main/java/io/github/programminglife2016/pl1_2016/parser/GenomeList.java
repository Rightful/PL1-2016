package io.github.programminglife2016.pl1_2016.parser;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by adam on 4/30/16.
 */
public class GenomeList implements GenomeCollection {
    private HashMap<String, Genome> genomes = new HashMap<>();

    public Genome put(String id, Genome genome) {
        return genomes.put(id, genome);
    }

    public Segment put(String id, Segment segment) {
        genomes.get(id).add(segment);
        return segment;
    }

    public Genome get(Object id) {
        return genomes.get(id);
    }

    public boolean containsKey(Object id) {
        return genomes.containsKey(id);
    }

    public Collection<Genome> getGenomes() {
        return genomes.values();
    }

    public String toString(Integer size){
        StringBuilder sb = new StringBuilder();
        for (Genome gnome: genomes.values()) {
            sb.append(gnome.getId()).append("\t").append(gnome.toString(size)).append("\n");
        }
        return sb.toString();
    }

}

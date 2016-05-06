package io.github.programminglife2016.pl1_2016.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamran Tadzjibov on 06.05.2016.
 */
public class Genome implements IGenome {

    private String name;
    private List<Integer> nodesIds = new ArrayList<>();
    private List<String> mutations = new ArrayList<>();

    public Genome(String name) {
        this.name = name;
    }

    public List<Integer> getNodesIds() {return nodesIds;}

    public List<String> getMutations() {return mutations;}

    public String getName() {return name;}
}

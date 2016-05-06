package io.github.programminglife2016.pl1_2016.parser;

import java.util.List;

/**
 * Created by Kamran Tadzjibov on 06.05.2016.
 */
public interface IGenome {

    String getName();

    List<Integer> getNodesIds();

    List<String> getMutations();
}

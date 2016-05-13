package io.github.programminglife2016.pl1_2016.parser;

import io.github.programminglife2016.pl1_2016.parser.nodes.NodeCollection;

public interface NodeReductionStrategy {
    NodeCollection reduce(NodeCollection original);
}
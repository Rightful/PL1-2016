package io.github.programminglife2016.pl1_2016;

import io.github.programminglife2016.pl1_2016.collapser.BubbleGraph;
import io.github.programminglife2016.pl1_2016.parser.JsonSerializable;
import io.github.programminglife2016.pl1_2016.parser.Parser;
import io.github.programminglife2016.pl1_2016.parser.SimpleParser;
import io.github.programminglife2016.pl1_2016.server.BasicServer;
import io.github.programminglife2016.pl1_2016.server.api.RestServer;
import io.github.programminglife2016.pl1_2016.server.Server;

import java.io.IOException;

/**
 * Reads the input and launches the server.
 */
public final class Launcher {
    private Launcher() {
    }
    /**
     * Read the input data and startsthe server on the default port.
     * @param args ignored
     * @throws IOException thrown if the port is in use.
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        BubbleGraph graph = new BubbleGraph(Launcher.class.getResourceAsStream("/genomes/TB10.gfa"));
        JsonSerializable jsonSerializable = graph.getCurrentGraph();

        Server server = new BasicServer(jsonSerializable.toJson());
        server.startServer();
        Server server2 = new RestServer(jsonSerializable);
        server2.startServer();
    }
}


package io.github.programminglife2016.pl1_2016.parser.nodes;

import io.github.programminglife2016.pl1_2016.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Graph parser to parse input obtained by a certain input stream.
 * @author Ravi Autar
 */
public class GraphvizParser implements Parser {
    private static final int FACTOR = 100;
    private NodeCollection nodeCollection;

    /**
     * Contructs a new parser object to parse an given inputstream.
     */
    public GraphvizParser(NodeCollection nodeCollection) {
        this.nodeCollection = nodeCollection;
    }

    @Override
    public final NodeCollection parse(InputStream inputStream) throws IOException {
        readFile(inputStream);
        return nodeCollection;
    }

    /**
     * Reads the given inputstream and handles found lines accordingly.
     * @param inputStream stream that is supposed to be parsed.
     */
    private void readFile(InputStream inputStream) {
        System.out.println("Parsing...");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Parses a string line and handles each found input accordingly.
     * @param line line that contains information to be parsed.
     */
    private void parseLine(String line) {
        String[] data = line.split(" ");
        switch (data[0]) {
            case "node" :
                handleNode(data);
                break;
            case "edge" :
                handleEdge(data);
                break;
            case "stop" :
                break;

        }
    }

    /**
     * Handler for when the line to be parsed is a node.
     * @param data data that contains information about the node.
     */
    private void handleNode(String[] data) {
        int id = Integer.parseInt(data[1]);
        int x = (int) Double.parseDouble(data[2]) * FACTOR;
        int y = (int) Double.parseDouble(data[3]) * FACTOR;
        nodeCollection.get(id).setXY(x, y);
    }

    /**
     * Handler for when the line to be parsed is a edge.
     * @param data data that contains information about an edge.
     */
    private void handleEdge(String[] data) {
        int from = Integer.parseInt(data[1]);
        int to = Integer.parseInt(data[2]);
        this.nodeCollection.get(from).addLink(this.nodeCollection.get(to));
    }
}

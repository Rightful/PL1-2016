package io.github.programminglife2016.pl1_2016.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Temporary simple parser for parsing .gfa files.
 */
public class SimpleParser implements Parser {
    private static final int SIZE = 900000;
    private static final String ATTR_ZINDEX = "START:Z:";
    private static final String ORIG_GENOME = "ORI:Z:";
    private static final String REFER_GENOME = "CRD:Z:";

    /**
     * Map containing the DNA seqments.
     */
    private NodeCollection nodeCollection;

    private NodeCollection nodesToShow;

    /**
     * Array containing all genomes.
     */
    private IGenome[] genomes;

    /**
     * Create parser object.
     */
    public SimpleParser() {
        nodeCollection = new NodeList(SIZE);
    }

    /**
     * Read and parse the data from the input stream
     * @param inputStream input data
     * @return Data structure with parsed data.
     */
    public JsonSerializable parse(InputStream inputStream) {
        read(inputStream);
//        return nodeCollection;
        return nodesToShow;
    }

    /**
     * Parse data from inputStream.
     * @param inputStream stream of data.
     */
    private void read(InputStream inputStream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
            PositionHandler positionHandler = new PositionHandler(this.nodeCollection, genomes);
            positionHandler.calculatePositions();
            positionHandler.getMutations();
            nodesToShow = positionHandler.nodesToShow;
            System.out.println("Done bubbling");
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
     * Parse one line of a .gfa file.
     * @param line contents of the specific line.
     */
    private void parseLine(String line) {
       line = line.trim();
       String[] data = line.split("\\s+");
       switch (data[0].charAt(0)) {
       case 'H': collectAllGenomes(data);
           break;
       case 'S': parseSegmentLine(data);
           break;
       case 'L': parseLinkLine(data);
           break;
       default:
           break;
       }
    }

    /**
     * Parse a segment line according to the GFA specification.
     * @param data contents of line separated by whitespace.
     */
    private void parseSegmentLine(String[] data) {
        int id = Integer.parseInt(data[1]);
        String seq = data[2];
        int column = 0;
        List<String> oriGenomes = new ArrayList<>();
        String crdGenome;
        String[] tmpCrd;
        if (data[data.length - 1].contains(ATTR_ZINDEX)) {
            column = Integer.parseInt(data[data.length - 1].split(":")[2]);
        }
        if (data[4].contains(ORIG_GENOME)) {
            oriGenomes = Arrays.asList(data[4].replaceAll(ORIG_GENOME, "").split(";"));
        }
        if (data[5].contains(REFER_GENOME)) {
            tmpCrd = data[5].replaceAll(REFER_GENOME, "").split(";");
            if(tmpCrd.length > 1)
                throw new IllegalArgumentException ("Multiple CRD's in segment " + id);
            crdGenome = tmpCrd[0];
        }
        else{
            throw new IllegalArgumentException ("No CRD's found in segment " + id);
        }
        if (!nodeCollection.containsKey(id)) {
            nodeCollection.put(id, new Segment(id, seq, column, oriGenomes, crdGenome));
        } else {
            nodeCollection.get(id).setData(seq);
            nodeCollection.get(id).setColumn(column);
            nodeCollection.get(id).getOriGenomes().addAll(oriGenomes);
            nodeCollection.get(id).setCrdGenome(crdGenome);
        }
        fillGenomes(id, oriGenomes);
    }

    /**
     * Parse a link line according to the GFA specification.
     * @param data contents of line separated by whitespace.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private void parseLinkLine(String[] data) {
        int from = Integer.parseInt(data[1]);
        int to = Integer.parseInt(data[3]);

        if (!nodeCollection.containsKey(to)) {
            nodeCollection.put(to, new Segment(to));
        }
        nodeCollection.get(from).addLink(nodeCollection.get(to));
        nodeCollection.get(to).addBackLink(nodeCollection.get(from));
    }

    /**
     * Get the nodeCollection containing all the segments.
     * @return hashmap of segments.
     */
    public NodeCollection getSegmentCollection() {
        return nodeCollection;
    }

    private void collectAllGenomes(String[] data) {
        if(!data[1].contains(ORIG_GENOME))
            return;
        genomes = Arrays.asList(data[1].replaceAll(ORIG_GENOME, "").split(";")).stream().map(x -> new Genome(x)).toArray((IGenome[]::new));
    }

    private void fillGenomes(int Id, List<String> oriGenomes) {
        if (genomes != null)
            for(IGenome genome : genomes) {
                if(oriGenomes.contains(genome.getName()))
                    genome.getNodesIds().add(Id);
            }
    }
}

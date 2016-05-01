package io.github.programminglife2016.pl1_2016.parser;

import io.github.programminglife2016.pl1_2016.Launcher;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class PhyloGeneticTreeParser implements Parser {

    @Override
    public JsonSerializable parse(InputStream inputStream) {
        String s = inputStreamToString(inputStream);
        StringTokenizer tokenizer = new StringTokenizer(s, "(:,);", true);
        TreeNode root = constructTree(null, tokenizer);
        root.toString("");
        return null;
    }

    private String inputStreamToString(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        String s = sc.useDelimiter("\\A").next();
        sc.close();
        return s;
    }

    public TreeNode constructTree(TreeNode parent, StringTokenizer tokenizer) {
        TreeNode current = new TreeNode();
        String next;
        double weight;
        List<TreeNode> nodes = new ArrayList<>();
        while (tokenizer.hasMoreElements()) {
            next = tokenizer.nextToken();
            switch (next) {
                case "(":
                    TreeNode newNode = constructTree(current, tokenizer);
                    nodes.add(newNode);
                    break;
                case ":":
                    current.setWeight(Double.parseDouble(tokenizer.nextToken()));
                    break;
                case ",":
                    nodes.add(current);
                    current = new TreeNode();
                    break;
                case ")":
                    nodes.add(current);
                    weight = 0;
                    if (tokenizer.hasMoreTokens() && tokenizer.nextToken().equals(":")) {
                        weight = Double.parseDouble(tokenizer.nextToken());
                    }
                    return new TreeNode("-", weight, nodes, parent);
                case ";":
                    current.setChildren(nodes);
                    return current;
                default:
                    current.setId(next);
                    break;
            }
        }
        current.setChildren(nodes);
        return current;
    }

    public static void main(String[] args) {
        System.out.println("PhylgoGenetic Tree Parser");
        PhyloGeneticTreeParser parser = new PhyloGeneticTreeParser();
        String s = "(B:6.0,(A:5.0,C:3.0,E:4.0):5.0,D:11.0);";
        parser.parse(Launcher.class.getResourceAsStream("/genomes/340tree.rooted.TKK.nwk"));
//        parser.parse(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
    }
}
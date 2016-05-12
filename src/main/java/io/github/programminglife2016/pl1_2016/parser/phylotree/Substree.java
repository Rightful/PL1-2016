package io.github.programminglife2016.pl1_2016.parser.phylotree;

import sun.reflect.generics.tree.Tree;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import static javax.swing.UIManager.get;

/**
 * Created by ravishivam on 12-5-16.
 */
public class Substree {
    public static void main(String[] args) throws FileNotFoundException {
        PhyloGeneticTreeParser parser = new PhyloGeneticTreeParser();
        InputStream inputStream = Substree.class.getResourceAsStream("/genomes/TB10.nwk");
        TreeNode root = parser.parse(inputStream);
        Substree subtree = new Substree();
        System.out.println(root.getChildren().get(0).getChildren());
        List<String> names = subtree.getNamesList();
        List<TreeNode> found = new ArrayList<>();
//        String s =  "101010101111";
//        String s2 = "10101";
//        System.out.println(findCommonPrefix(s,s2));
//        System.out.println(distances.get(nodes.get(0)));
//        System.out.println(distances.get(nodes.get(1)));
//        calculateDistance(distances.get(nodes.get(0)), distances.get(nodes.get(1)));
//        System.out.println(distances.get(nodes.get(0)));
//        System.out.println(distances.get(nodes.get(1)));

//        for (Map.Entry entry : distances.entrySet()) {
//            System.out.println(entry);
//        }
//        System.out.println();
//        TreeNode closest = findClosestPair(distances);
//        System.out.println(closest);
    }

    private static TreeNode findSubTree(HashMap<TreeNode, String> distances, TreeNode root) {
        while (distances.size() != 2) {
            TreeNode closest = findClosestPair(distances);
            String common = findCommonPrefix(distances.get(closest.getChildren().get(0)), distances.get(closest.getChildren().get(1)));
            distances.remove(closest.getChildren().get(0));
            distances.remove(closest.getChildren().get(1));
            distances.put(closest, common);
        }
        List<TreeNode> lst = new ArrayList<>(distances.keySet());
        root.setChildren(lst);
        return root;
    }

    private static String findCommonPrefix(String s1, String s2) {
        StringBuilder b1 = new StringBuilder(s1);
        StringBuilder b2 = new StringBuilder(s2);
        StringBuilder b3 = new StringBuilder();
        int i = 0;
        while (b1.charAt(i) == b2.charAt(i)) {
            b3.append(b1.charAt(i));
            i++;
            if (b1.length() == i || b2.length() == i){
                break;
            }
        }
        return b3.toString();
    }

    private static TreeNode findClosestPair(HashMap<TreeNode, String> distances) {
        List<Map.Entry<TreeNode, String>> distancelist = new ArrayList<>(distances.entrySet());
        TreeNode clos1 = distancelist.get(0).getKey();
        TreeNode clos2 = distancelist.get(1).getKey();
        int currclos = calculateDistance(distances.get(clos1), distances.get(clos2));
        for (int i = 0; i < distancelist.size(); i++) {
            for (int j = i+1; j < distancelist.size(); j++) {
                int distnew = calculateDistance(distancelist.get(i).getValue(), distancelist.get(j).getValue());
                if (distnew < currclos) {
                    clos1 = distancelist.get(i).getKey();
                    clos2 = distancelist.get(j).getKey();
                    currclos = distnew;
                }
            }
        }
        TreeNode res = new BaseTreeNode("-",clos1.getWeight()+clos2.getWeight());
        List<TreeNode> childs = new ArrayList<>();
        childs.add(clos1);
        childs.add(clos2);
        res.setChildren(childs);
        return res;
    }

    private static int calculateDistance(String s1, String s2) {
        StringBuilder b1 = new StringBuilder(new String(s1));
        StringBuilder b2 = new StringBuilder(new String(s2));
        while (b1.charAt(0) == b2.charAt(0)) {
            b1.deleteCharAt(0);
            b2.deleteCharAt(0);
            if(b1.toString().length()==0 || b2.toString().length()==0){
                break;
            }
        }
        int size = b1.toString().length() + b2.toString().length() + 1;
        return size;
    }

    private HashMap<TreeNode,String> getDistances(TreeNode root, List<TreeNode> nodes) {
        HashMap<TreeNode, String> dists = new HashMap<>();
        for (TreeNode node : nodes) {
            TreeNode curr = node;
            TreeNode parent = curr.getParent();
            StringBuilder builder = new StringBuilder();
            while(curr != root) {
               for(TreeNode child : parent.getChildren()) {
                   if (child.equals(curr)) {
                       builder.append(0);
                   }
                   else {
                       builder.append(1);
                   }
               }
                curr = parent;
                parent = curr.getParent();
            }
            dists.put(node, builder.reverse().toString());
        }
        return dists;
    }

    private List<TreeNode> findNodes(TreeNode root, List<String> names, List<TreeNode> found) {
        if (root.getChildren().size() == 0) {
            if(names.contains(root.getName())) {
                found.add(root);
                return found;
            }
            else {
                return found;
            }
        }
        List<TreeNode> tree1 = findNodes(root.getChildren().get(0), names, found);
        List<TreeNode> tree2 = findNodes(root.getChildren().get(1), names, tree1);
        return tree2;
    }

    private List<String> getNamesList() throws FileNotFoundException {
        Scanner scan = new Scanner(Substree.class.getResourceAsStream("/genomes/names.txt"));
        List<String> name = new ArrayList<>();
        while(scan.hasNext()){
            name.add(scan.next());
        }
        return name;
    }
}

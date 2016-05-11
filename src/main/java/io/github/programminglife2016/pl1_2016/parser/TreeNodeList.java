package io.github.programminglife2016.pl1_2016.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.programminglife2016.pl1_2016.parser.phylotree.TreeNode;

import java.util.ArrayList;

public class TreeNodeList extends ArrayList<TreeNode> implements TreeNodeCollection {
    private TreeNode root;

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().registerTypeAdapter(TreeNodeList.class, new TreeNodeCollectionSerializer()).create();
        return gson.toJson(this);
    }

    @Override
    public TreeNode getRoot() {
        return root;
    }

    @Override
    public void setRoot(TreeNode root) {
        this.root = root;
    }
}
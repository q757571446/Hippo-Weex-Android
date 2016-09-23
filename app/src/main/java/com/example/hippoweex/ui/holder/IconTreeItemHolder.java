package com.example.hippoweex.ui.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hippoweex.R;
import com.example.hippoweex.ui.widget.iconfont.PrintView;
import com.google.gson.internal.LinkedTreeMap;
import com.unnamed.b.atv.model.TreeNode;

public class IconTreeItemHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;

    public IconTreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_icon_node, null, false);
        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.getNode().getName());

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        view.findViewById(R.id.btn_addFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TreeNode newFolder = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "New Folder"));
//                getTreeView().addNode(node, newFolder);
            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getTreeView().removeNode(node);
            }
        });

        if (node.getLevel() == 1) {
            view.findViewById(R.id.btn_delete).setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public int icon;
        public PageNode node;

        public IconTreeItem(int icon, PageNode node) {
            this.icon = icon;
            this.node = node;
        }

        public PageNode getNode() {
            return node;
        }
    }

    public static class PageNode{
        String name;
        String relativePath;

        public PageNode(){
            this.name = "当前项目架构";
            this.relativePath = "";
        }

        public PageNode(String relativePath, LinkedTreeMap map){
            this.relativePath = relativePath;
            this.name = map.get("name").toString();
        }

        public String getName(){
            return name;
        }

        public String getRelativePath() {
            return relativePath;
        }
    }
}
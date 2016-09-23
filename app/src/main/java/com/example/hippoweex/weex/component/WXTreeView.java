package com.example.hippoweex.weex.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.utils.GsonTools;
import com.example.hippoweex.R;
import com.example.hippoweex.ui.holder.IconTreeItemHolder;
import com.google.gson.internal.LinkedTreeMap;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXViewUtils;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.Map;

/**
 * Created by Kevin on 2016/8/27.
 */
public class WXTreeView extends WXComponent {
    private LinkedTreeMap<String, LinkedTreeMap> structure;
    private TextView statusBar;
    private ViewGroup containerView;
    private AndroidTreeView tView;

    public WXTreeView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
    }

    @Override
    protected View initComponentHostView(Context context) {
        View view = View.inflate(context, R.layout.fragment_structure, null);
        containerView = (ViewGroup) view.findViewById(R.id.container);
        statusBar = (TextView) view.findViewById(R.id.status_bar);

        tView = new AndroidTreeView(mContext);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        tView.setDefaultViewHolder(IconTreeItemHolder.class);
        tView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
                statusBar.setText(item.getNode().getName() + ":   " + item.getNode().getRelativePath());
            }
        });
        tView.setDefaultNodeLongClickListener(new TreeNode.TreeNodeLongClickListener() {
            @Override
            public boolean onLongClick(TreeNode node, Object value) {
                IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
                Toast.makeText(mContext, "Long click: " + item.getNode().getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }

    private void populate(StringBuffer path, TreeNode node, LinkedTreeMap<String,LinkedTreeMap> root){
        for(Map.Entry<String,LinkedTreeMap> entry : root.entrySet()){
            String key = entry.getKey();//路径
            LinkedTreeMap value = entry.getValue();//路径所对应对象
            StringBuffer subPath = new StringBuffer(path).append("/").append(key);

            String s = subPath.toString();
            IconTreeItemHolder.PageNode pageNode = new IconTreeItemHolder.PageNode(s, value);
            IconTreeItemHolder.IconTreeItem iconTreeItem = new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, pageNode);
            TreeNode subNode = new TreeNode(iconTreeItem);
            if(value.containsKey("subRouters")){
                //如果包含子节点，递归遍历
                populate(subPath,subNode,(LinkedTreeMap<String, LinkedTreeMap>) value.get("subRouters"));
            }
            node.addChild(subNode);
        }
    }

    @WXComponentProp(name= "items")
    public void setContent(String tabUri){
        structure = GsonTools.convertJsonToNestLinkedMap(tabUri);

        structure.remove("manager");//移除界面管理

        TreeNode root = TreeNode.root();

        TreeNode treeNode = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, new IconTreeItemHolder.PageNode()));

        populate(new StringBuffer(),treeNode, structure);

        root.addChild(treeNode);

        tView.setRoot(root);
        containerView.addView(tView.getView());
    }


    /**
     * Measure the size of the recyclerView.
     *
     * @param width  the expected width
     * @param height the expected height
     * @return the result of measurement
     */
    @Override
    protected MeasureOutput measure(int width, int height) {
        int screenH = WXViewUtils.getScreenHeight(WXEnvironment.sApplication);
        int weexH = WXViewUtils.getWeexHeight(mInstanceId);
        int outHeight = height > (weexH >= screenH ? screenH : weexH) ? weexH - mAbsoluteY : height;
        if (outHeight == 0){
            outHeight = weexH - mAbsoluteY;
        }
        return super.measure(width, outHeight);
    }

}

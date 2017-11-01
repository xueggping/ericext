package com.summit.util;

import java.util.ArrayList;
import java.util.List;

import com.summit.util.CommonUtil.TreeNodeClass;



public class TreeNode implements TreeNodeClass{
	private String id;
	private String pid;
	private String text;
	private Object data;// 附加参数
	private Boolean leaf = true; // 是否子叶子节点，默认是
	private Boolean checked; // 复选框控制（null 为不出现复选框，false 出现复选框不选中，true 出现复选框并选中）
	private Boolean expanded; // 展开
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TreeNode(String id, String pid, String text) {
		super();
		this.id = id;
		this.pid = pid;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see com.summit.mtmews.monitor.util.CommonUtil.TreeNodeClass#getNodeData()
	 */
	public Object getNodeData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	/* (non-Javadoc)
	 * @see com.summit.mtmews.monitor.util.CommonUtil.TreeNodeClass#getNodeId()
	 */
	public String getNodeId() {
		// TODO Auto-generated method stub
		return id;
	}

	/* (non-Javadoc)
	 * @see com.summit.mtmews.monitor.util.CommonUtil.TreeNodeClass#getNodePid()
	 */
	public String getNodePid() {
		// TODO Auto-generated method stub
		return pid;
	}

	/* (non-Javadoc)
	 * @see com.summit.mtmews.monitor.util.CommonUtil.TreeNodeClass#getNodeText()
	 */
	public String getNodeText() {
		// TODO Auto-generated method stub
		return text;
	}

	/* (non-Javadoc)
	 * @see com.summit.mtmews.monitor.util.CommonUtil.TreeNodeClass#getNodeChecked()
	 */
	public Boolean getNodeChecked() {
		// TODO Auto-generated method stub
		return checked;
	}
	
	public String getAdcd(){
		return id;
	}
}

import java.util.List;

public abstract class TreeNode {
	static int count; // this is a comment
	private String id; // this is a comment
	private List<TreeNode> children; // this is a comment
	private TreeNode parent; // this is a comment

	public TreeNode(List<TreeNode> children) {
		count++;
		id = "" + count;
		this.children = children;
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				((TreeNode) children.get(i)).setParent(this);
			}
		}
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		TreeNode.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void addChild(TreeNode child) {
		children.add(child);
		child.setParent(this);
	}
}

import java.util.ArrayList;

public class FileParser {
	private TagNode root; // this is a comment

	public void createTree(ArrayList<String> lines) {
		TagNode current = null;
		for (int i = 0; i < lines.size(); i++) {
			if (!lines.get(i).contains("</") && !lines.get(i).contains("/>") && lines.get(i).contains("<")) {
				TagNode node1 = new TagNode(lines.get(i).substring(1, lines.get(i).length() - 1));
				if (i == 0) {
					root = node1;
					current = node1;
				} else {
					current.addChild(node1);
					current = node1;
				}
			} else if (lines.get(i).contains("</")) {
				current = (TagNode) current.getParent();
			} else if (lines.get(i).contains("<") && lines.get(i).contains("/>")) {
				String[] parts1 = lines.get(i).split(" ");
				String[] parts2 = lines.get(i).split("=");
				TagNode node2 = new TagNode(parts1[0].substring(1));
				int index = 1;
				for (int j = 1; j < parts2.length; j++) {
					String name = parts1[index].substring(0, parts1[index].indexOf("="));
					String value = parts2[j].substring(parts2[j].indexOf("\"") + 1, parts2[j].lastIndexOf("\""));
					node2.addAttribute(name, value);
					index++;
				}
				current.addChild(node2);
			} else {
				TextNode node3 = new TextNode(lines.get(i));
				current.addChild(node3);
			}
		}
	}

	public TagNode getRoot() {
		return root;
	}

	public void setRoot(TagNode root) {
		this.root = root;
	}

	public void mineImages(ArrayList<TagNode> images, TreeNode node) {
		if (node instanceof TagNode) {
			if (((TagNode) node).getTag().equals("img")) {
				images.add((TagNode) node);
			}
		}
		if (node.getChildren() != null) {
			for (TreeNode child : node.getChildren()) {
				mineImages(images, child);
			}
		}
	}

	public String getKeywordsForImage(String filename) {
		String keywords = "";
		ArrayList<TreeNode> image = new ArrayList<TreeNode>();
		getKeywordsForImageHelper(filename, getRoot(), image);
		for (int i = 0; i < image.size(); i++) {
			keywords += ((TagNode) image.get(i)).mineCloseText();
		}
		return keywords;
	}

	public void getKeywordsForImageHelper(String filename, TreeNode node, ArrayList<TreeNode> images) {
		if (node instanceof TagNode) {
			if (((TagNode) node).getTag().equals("img")) {
				if (((TagNode) node).getAttributes().get("src").substring(2).equals(filename)) {
					images.add(node);
				}
			}
		}
		if (node.getChildren() != null) {
			for (int i = 0; i < node.getChildren().size(); i++) {
				getKeywordsForImageHelper(filename, node.getChildren().get(i), images);
			}
		}
	}
}

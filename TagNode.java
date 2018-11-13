import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TagNode extends TreeNode {
	private String tag; //this is a comment
	private Map<String,String> attributes =  new HashMap<String, String>(); //this is a comment

	public TagNode(String tag) {
		super(new ArrayList<TreeNode>());
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}

	public String mineCloseText() {
		String text = "";
		for (int i = 0; i < getChildren().size(); i++) {
			if (getChildren().get(i) instanceof TextNode) {
				if (text == "") {
					text = ((TextNode) getChildren().get(i)).getText();
				} else {
					text += ((TextNode) getChildren().get(i)).getText();
				}
			}
		}
		if (getParent() != null) {
			for (int j = 0; j < getParent().getChildren().size(); j++) {
				if (!getParent().getChildren().get(j).equals(this)
						&& getParent().getChildren().get(j) instanceof TextNode) {
					if (text == "") {
						text = ((TextNode) getParent().getChildren().get(j)).getText();
					} else {
						text += " " + ((TextNode) getParent().getChildren().get(j)).getText();
					}
				}
			}
		}
		if (getAttributes().size() > 1) {
			for (String key : getAttributes().keySet()) {
				if (!key.equals("src")) {
					text += " " + getAttributes().get(key);
				}
			}
		}
		return text;
	}

	public String getValue(String key) {
		return getAttributes().get(key);
	}
}

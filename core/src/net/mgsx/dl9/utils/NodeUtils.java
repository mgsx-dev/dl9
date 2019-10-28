package net.mgsx.dl9.utils;

import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;

public class NodeUtils {

	public static void enable(Iterable<Node> nodes, boolean enabled){
		for(Node node : nodes){
			enable(node, enabled);
		}
	}
	public static void enable(Node node, boolean enabled){
		for(NodePart part : node.parts){
			part.enabled = enabled;
		}
		for(Node child : node.getChildren()){
			enable(child, enabled);
		}
	}
}

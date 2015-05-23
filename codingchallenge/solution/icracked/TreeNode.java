package codingchallenge.solution.icracked;
/**
 * @author Vignesh
 * 
 * TREENODE CLASS
 * 
 * This is the main TreeNode class which contains the basic structure 
 * of the TreeNode and all the required methods used by the test driver
 * 
 * It consists of helpers to:
 * a)	Add a new child node
 * b)	Remove a child node
 * c)	Lookup a particular node
 * d)	Get the grandparent of a specific node
 * e)	List the only children without siblings
 * f)	List the parents without children
 * g)	Getting the member with the most grandkids
 * h)	Printing the tree
 * 
 * The Treenode works under the assumption that all member names are UNIQUE
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {
	String name;
	TreeNode parent = null;
	List<TreeNode> children;

	TreeNode(String data){
		this.name = data;
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Method to add child node to member
	 * @param parentName	Name of the parent to whom the child is attached
	 * @param childName		Name of the child node to attach
	 * @return childNode
	 */
	public TreeNode addChild(String parentName, String childName){
		TreeNode parent = find(parentName);
		assert parent != null;
		TreeNode child = new TreeNode(childName);
		child.parent = parent;
		parent.children.add(child);
		return child;
	}
	
	/**
	 * Method to remove the child node. This severs the connection of all
	 * subsequent children of the removed child.
	 * 
	 * @param childName		Name of the child to be removed
	 */
	public void removeChild(String childName){
		TreeNode childToRemove = find(childName);
		assert childToRemove != null;
		
		TreeNode parentOfRemovedChild = childToRemove.parent;
		parentOfRemovedChild.children.remove(childToRemove);
	}
	
	/**
	 * Method that finds a specific child node in the tree.
	 * 
	 * @param childName		Node to search the family tree
	 * @return TreenNode
	 */
	public TreeNode find(String childName){
		if (this.name.equals(childName))
			return this;
		//Implementing using BFS
		Queue<TreeNode> searchQueue = new LinkedList<TreeNode>();
		searchQueue.add(this);
		while(!searchQueue.isEmpty()){
			TreeNode currentNode = searchQueue.remove();	
			for(TreeNode child : currentNode.children){
				if(child.name.equals(childName))
					return child;
				else
					searchQueue.add(child);
				}
		}
		return null;
	}
	
	/**
	 * Method that gets the grandparent of the specified member
	 * 
	 * @param name		Member name for which we need to find the grandparent of
	 * @return String	Name of the grandparent
	 */
	public String getGrandParent(String name){
		int stepsAbove = FamilyTreeConstants.GRANDFATHER_LEVEL;
		TreeNode currentNode = find(name);
		assert currentNode != null;
		while(currentNode != null && stepsAbove > 0){
			currentNode = currentNode.parent;
			stepsAbove--;
		}
		return (String) (currentNode == null ? "No Grandparent" : currentNode.name);
	}
	
	/**
	 * Method that generates a list of node without siblings
	 * @return	List containing names of the members without siblings
	 */
	public List<String> getOnlyChildren(){
		List<String> onlyChildList = new ArrayList<String>();
		Queue<TreeNode> traverseQueue = new LinkedList<TreeNode>();
		if(hasSiblings(this))
			onlyChildList.add(this.name);
		traverseQueue.add(this);
		while(!traverseQueue.isEmpty()){
			TreeNode currentNode = traverseQueue.remove();	
			for(TreeNode child : currentNode.children){
				if(hasSiblings(child))
					onlyChildList.add(child.name);
				traverseQueue.add(child);
			}
		}
		return onlyChildList;
	}
	
	/**
	 * Checks if a node has any siblings
	 * @param node	Node to check if they have siblings
	 * @return	boolean to check if it has sibings
	 */
	private boolean hasSiblings(TreeNode node) {
		if(node.parent == null || node.parent.children.size() == 1)
			return true;
		return false;
	}
	
	/**
	 * Method that returns a list of people who don't have children (leaf nodes)
	 * @return List	Names of people without kids
	 */
	public List<String> getPeopleWithoutKids(){
		List<String> peopleWithoutChildren = new ArrayList<String>();
		Queue<TreeNode> traverseQueue = new LinkedList<TreeNode>();
		traverseQueue.add(this);
		while(!traverseQueue.isEmpty()){
			TreeNode currentNode = traverseQueue.remove();	
			for(TreeNode child : currentNode.children){
				if(child.children.size() == 0)
					peopleWithoutChildren.add(child.name);
				traverseQueue.add(child);
			}
		}
		return peopleWithoutChildren;
	}
	
	/**
	 * Method that returns the name of the member with the most grandkids.
	 * This method returns only one name even if two/more people have 
	 * the same number of grandkids
	 * 
	 * @return	String	Person with the most grandkids
	 */
	public String mostGrandKids(){
		TreeNode mostGrandKids = null;
		int maxGrandKids = 0;
		Queue<TreeNode> traverseQueue = new LinkedList<TreeNode>();
		traverseQueue.add(this);
		while(!traverseQueue.isEmpty()){
			TreeNode currentNode = traverseQueue.remove();	
			for(TreeNode child : currentNode.children){
				int currentGrandKids = countGrandKids(child);
				if(currentGrandKids > maxGrandKids){
					maxGrandKids = currentGrandKids;
					mostGrandKids = child;
				}
				traverseQueue.add(child);
			}
		}
		return mostGrandKids.name;
	}
	
	/**
	 * Method that counts the number of grandkids for a member
	 * @param node	Member for which we need to find the number of grandkids
	 * @return	int	number of grandkids
	 */
	private int countGrandKids(TreeNode node){
		int num = 0;
		if(node.children.size() == 0)
			return 0;
		for(TreeNode children : node.children){
			if(children.children != null)
				num += children.children.size();
		}
		return num;
	}

	/**
	 * Public helper to print the tree
	 */
	public void print() {
        print("", true);
    }

	/**
	 * Sub helper to print tree. Printing is done in a vertical manner
	 * @param prefix	prefix string to print
	 * @param isTail	Is this node a leaf node
	 */
    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}

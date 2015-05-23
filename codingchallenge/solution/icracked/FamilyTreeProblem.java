package codingchallenge.solution.icracked;
/**
 * @author Vignesh
 * 
 * TEST RUNNER CLASS
 * 
 * This class runs sanity tests for the family tree
 * The tests include:
 * a)	Finding the grandparent of a particular member
 * b)	Finding out the only children in the family tree
 * c)	Finding the children without siblings in the tree
 * d)	Finding the person with the most grandkids
 * e)	Printing the family tree
 * 
 */
import java.util.List;

public class FamilyTreeProblem {
	static TreeNode root;
	public static void main(String args[]){
		//Create the required test family tree
		root = createFamilyTree();
		
		//Find and display the grandparent information of a Node
		System.out.format("The grandparent of Kevin is %s\n", root.getGrandParent("Kevin"));
		
		//Finding out people with no siblings
		List<String> onlyChildren = root.getOnlyChildren();
		System.out.print("The people who have no siblings are: ");
		for(String s : onlyChildren){
			System.out.print(s + " ");
		}
		System.out.println();
		
		//Finding all the people without kids
		List<String> parentsWithoutKids = root.getPeopleWithoutKids();
		System.out.print("The parents who don't have kids are: ");
		for(String s : parentsWithoutKids){
			System.out.print(s + " ");
		}
		System.out.println();
		
		//Finding the person with the most number of grandkids
		System.out.println(root.mostGrandKids() + " has the most grandkids");
		
		//Print the tree
		root.print();
	}
	
	/**
	 * Simple driver method that populates the Family Tree as per the example
	 * @return root	Root node of the family tree
	 */
	static TreeNode createFamilyTree(){
		TreeNode root = new TreeNode("Nancy");
		root.addChild("Nancy", "Adam");
		root.addChild("Nancy", "Jill");
		root.addChild("Nancy", "Carl");
		root.addChild("Jill", "Kevin");
		root.addChild("Carl", "Catherine");
		root.addChild("Carl", "Joseph");
		root.addChild("Kevin", "Samuel");
		root.addChild("Kevin", "George");
		root.addChild("Kevin", "James");
		root.addChild("Kevin", "Aaron");
		root.addChild("George", "Patrick");
		root.addChild("George", "Robert");
		root.addChild("James", "Mary");
		return root;
	}
}

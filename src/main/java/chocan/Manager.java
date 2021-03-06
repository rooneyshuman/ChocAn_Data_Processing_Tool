package chocan;

import static java.lang.System.out;

/**
 * A manager of the ChocAn software system.
 */
public class Manager extends Member {
	
	/**
	 * Creates a new manager data object.
	 * @param id The ID of the manager.
	 * @param name The name of the manager.
	 * @param address The address of the manager.
	 * @param city The city of the manager.
	 * @param state The state of the manager.
	 * @param zip The zip of the manager.
	 */
	public Manager(final int id, final boolean active, final String name,
	               final String address, final String city, final String state, final String zip) {
		super(id, active, name, address, city, state, zip);
	}

	public Manager goLeft() {
		return (Manager) super.left;
	}

	public Manager goRight() {
		return (Manager) super.right;
	}

	void setLeft(Manager left) {
		super.left = left;
	}

	void setRight(Manager right) {
		super.right = right;
	}

	public void display() {

		out.println("Manager Name: " + name);
		out.println("Manager Number: " + id);
		out.println("Manager Address: " + address);
		out.println("Manager City: " + city);
		out.println("Manager State: " + state);
		out.println("Manager Zip Code: " + zip);

		if (active) out.println("Manager Status: Active");
		else out.println("Manager Status: Inactive");

		out.println("-----------------------------------------");
	}
}

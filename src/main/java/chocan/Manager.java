package chocan;

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
	public Manager(final int id, final String name, 
	               final String address, final String city, final String state, final String zip) {
		super(id, true, name, address, city, state, zip);
	}
	
}

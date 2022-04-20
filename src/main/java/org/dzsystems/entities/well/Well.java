package org.dzsystems.entities.well;

import ru.ep.sdo.Entity;
import ru.ep.sdo.annotations.Xml;

@Xml(name = "ROW")
public class Well extends Entity implements Comparable<Well> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2670680845292730820L;
	public static final String PROPERTYNAME_WELL_ID = "id";
	public static final String PROPERTYNAME_WELL_NAME = "name";

	@Xml(name = "id")
	private int id;

	@Xml(name = "name")
	private String name;

	public Well() {
		super();
	}

	public Well(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Well(String wellName) {
		super();
		this.name = wellName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		int oldValue = this.id;
		this.id = id;
		firePropertyChange(PROPERTYNAME_WELL_ID, oldValue, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROPERTYNAME_WELL_NAME, oldValue, name);
	}

	@Override
	public int compareTo(Well o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getName());
	}

	
}

package org.dzsystems.entities.equipment;

import ru.ep.sdo.Entity;
import ru.ep.sdo.annotations.Xml;

@Xml(name = "ROW")
public class Equipment extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4267771372512818762L;
	public static final String PROPERTYNAME_EQ_ID = "id";
	public static final String PROPERTYNAME_EQ_NAME = "name";
	public static final String PROPERTYNAME_EQ_WELL_ID = "wellId";

	@Xml(name = "id")
	private int id;

	@Xml(name = "name")
	private String name;

	@Xml(name = "well_id")
	private int wellId;

	public Equipment() {
		super();
	}

	public Equipment(int id, String name, int wellId) {
		super();
		this.id = id;
		this.name = name;
		this.wellId = wellId;
	}

	public Equipment(String name) {
		super();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		int oldValue = this.id;
		this.id = id;
		firePropertyChange(PROPERTYNAME_EQ_ID, oldValue, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		firePropertyChange(PROPERTYNAME_EQ_NAME, oldValue, name);
	}

	public int getWellId() {
		return wellId;
	}

	public void setWellId(int wellId) {
		int oldValue = this.wellId;
		this.wellId = wellId;
		firePropertyChange(PROPERTYNAME_EQ_WELL_ID, oldValue, wellId);
	}

}

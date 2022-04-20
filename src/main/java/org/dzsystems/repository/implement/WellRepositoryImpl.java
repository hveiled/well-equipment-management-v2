/**
 * 
 */
package org.dzsystems.repository.implement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dzsystems.entities.equipment.Equipment;
import org.dzsystems.entities.well.Well;
import org.dzsystems.repository.WellRepository;

import ru.ep.sdo.Session;
import ru.ep.sdo.SessionFactory;
import ru.ep.sdo.filter.EqualFilter;
import ru.ep.sdo.list.XMLListModel;

/**
 * @author andrew ivanov
 *
 */
public class WellRepositoryImpl implements WellRepository {

	private static volatile WellRepositoryImpl instance;
	private static Session session = SessionFactory.getInstance().createSessionFromFile("sqlite.session", null);

//	XMLListModel equipListModel = session.getListModel("Equipment");
//	XMLListModel wellListModel = session.getListModel("Well");

	/**
	 * Instantiation method
	 * 
	 * @return Singleton instance of the class
	 */
	public static synchronized WellRepositoryImpl getInstance() {
		if (instance == null)
			synchronized (WellRepositoryImpl.class) {
				if (instance == null)
					instance = new WellRepositoryImpl();
			}
		return instance;
	}

	@Override
	public List<Equipment> findAllEquipmentByWellID(int wellId) {
		XMLListModel equipListModel = session.getListModel("Equipment");
		equipListModel.setFilter(new EqualFilter(Equipment.PROPERTYNAME_EQ_WELL_ID, wellId));
		Iterator iterator = equipListModel.iterator();
		List<Equipment> equipments = new ArrayList<>();
		while (iterator.hasNext()) {
			Equipment equipment = (Equipment) iterator.next();
			if (equipment.getWellId() == wellId) {
				equipments.add(equipment);
			}
		}
		return equipments;
	}

	@Override
	public Well findWellByName(String name) {
//		wellListModel.fetchAll();
		XMLListModel wellListModel = session.getListModel("Well");
		Iterator iterator = wellListModel.iterator();
		while (iterator.hasNext()) {
			Well well = (Well) iterator.next();
			if (well.getName().equals(name)) {
				return well;
			}
		}
		return null;
	}

	@Override
	public String findLastRecordNameFromEquipment() {
		XMLListModel equipListModel = session.getListModel("Equipment");
		int lastIndex = equipListModel.getSize() - 1;
		if (lastIndex < 0) {
			return null;
		}
		Equipment equipment = (Equipment) equipListModel.get(lastIndex);
		return equipment.getName();
	}

	@Override
	public List<Well> findAllWells() {
		XMLListModel wellListModel = session.getListModel("Well");
		List<Well> wells = new ArrayList<>();
		wellListModel.asList(Well.class);
		Iterator iterator = wellListModel.iterator();
		while (iterator.hasNext()) {
			wells.add((Well) iterator.next());
		}
		return wells; 
		
	}

	@Override
	public boolean isWellExists(Well well) {
		XMLListModel wellListModel = session.getListModel("Well");
		return wellListModel.contains(well);
	}

	@Override
	public void saveAllEquipmentWithWellId(List<Equipment> equipmentList, int wellId) {
//		equipListModel.fetchAll();
		XMLListModel equipListModel = session.getListModel("Equipment");
		int lastIndex = equipListModel.getRowCount();
		for (Equipment equipment : equipmentList) {
			equipment.setId(++lastIndex);
			equipment.setWellId(wellId);
		}
		equipListModel.addAll(equipmentList);
		session.commit();
	}

	public Well findWellById(int wellId) {
//		wellListModel.fetchAll();
		XMLListModel wellListModel = session.getListModel("Well");
		wellListModel.setFilter(new EqualFilter(Well.PROPERTYNAME_WELL_ID, wellId));
		Iterator iterator = wellListModel.iterator();
		while (iterator.hasNext()) {
			Well well = (Well) iterator.next();
			if (well.getId() == wellId) {
				return well;
			}
		}
		return null;
	}

	@Override
	public Well saveWell(Well well) {
//		wellListModel.fetchAll();
		XMLListModel wellListModel = session.getListModel("Well");
		int lastIndex = wellListModel.getRowCount();
		well.setId(++lastIndex);
		if (wellListModel.add(well)) {
			session.commit();
			return (Well) wellListModel.find(well);
		}
		return null;
	}

	@Override
	public List<Well> findAllWellsByNameSet(Set<String> wellNameSet) {
		List<Well> wellList = new ArrayList<>();
		for (String name : wellNameSet) {
			Well well = findWellByName(name);
			if (well != null) {
				wellList.add(well);
			}
		}
		return wellList;
	}
	
	public static void closeSession() {
		session.commit();
		session.close();
		System.out.println("Database session is closed");
	}

}

package org.dzsystems.repository;

import java.util.Set;
import java.util.List;

import org.dzsystems.entities.equipment.Equipment;
import org.dzsystems.entities.well.Well;


/**
 * @author andrew ivanov
 * 
 * @version 1.0
 *
 */
public interface WellRepository {

	public List<Equipment> findAllEquipmentByWellID(int wellId);
	public void saveAllEquipmentWithWellId(List<Equipment> equipmentList, int wellId);
	public String findLastRecordNameFromEquipment();
	public Well saveWell(Well well);
	public Well findWellByName(String name);
	public List<Well> findAllWellsByNameSet(Set<String> wellNameSet);
	public List<Well> findAllWells();
	public boolean isWellExists(Well well);
}


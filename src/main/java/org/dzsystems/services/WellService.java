package org.dzsystems.services;

import org.dzsystems.entities.equipment.Equipment;
import org.dzsystems.entities.well.Well;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WellService {
	boolean createEquipment(String wellName, int equipmentUnitNumber);
	Map<Well, List<Equipment>> getAllWellsByNameSet(Set<String> names);
	Map<Well, List<Equipment>> getAllWells();
}


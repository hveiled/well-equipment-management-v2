package org.dzsystems.services.implement;

import org.dzsystems.repository.WellRepository;
import org.dzsystems.repository.implement.WellRepositoryImpl;
import org.dzsystems.services.EquipmentNamingService;

/**
 * Equipment naming service
 *
 * @author Andrei Ivanov
 * @version 1.0
 */
public class EquipmentNamingServiceImpl implements EquipmentNamingService {

	private static volatile EquipmentNamingService instance;
	private static final WellRepository repository = new WellRepositoryImpl();
	private static final String name_prefix = "EQ";
	private static volatile int name_postfix = 0;


	public static synchronized EquipmentNamingService getInstance() {
		if (instance == null)
			synchronized (EquipmentNamingService.class) {
				if (instance == null)
					instance = new EquipmentNamingServiceImpl();
			}
		return instance;
	}

	/**
	 * Creates unique equipment name
	 *
	 * @return String unique name;
	 */
	public synchronized String createEquipmentName() {
		String postfix = "";
		if (name_postfix == 0) {
			postfix = repository.findLastRecordNameFromEquipment();
			postfix = (postfix == null) ? "0" : postfix.substring(2);
			name_postfix = Integer.parseInt(postfix);
		}
		postfix = String.valueOf(++name_postfix);
		return name_prefix + String.format("%1$" + 4 + "s", postfix).replace(' ', '0');
	}
}
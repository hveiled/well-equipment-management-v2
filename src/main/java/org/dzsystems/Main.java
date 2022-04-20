package org.dzsystems;

import org.dzsystems.entities.equipment.Equipment;
import org.dzsystems.entities.well.Well;
import org.dzsystems.repository.implement.WellRepositoryImpl;
import org.dzsystems.utils.DataBaseHandler;
import org.dzsystems.utils.ExportHandler;
import org.dzsystems.utils.PrintHandler;
import org.dzsystems.services.WellService;
import org.dzsystems.services.implement.WellServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

public class Main implements Runnable {

	private final Scanner scanner = new Scanner(System.in);

	private WellService wellService = WellServiceImpl.getInstance();
	private boolean running = true;
	private final String GREETING_PROMPT = "" 
			+ "=================================================\n"
			+ "||                 DZ * SYSTEMS                ||\n"
			+ "||                    WELCOME                  ||\n"
			+ "||              Oil Wells equipment            ||\n"
			+ "||             record and management           ||\n"
			+ "||                                             ||\n"
			+ "|| enter 'h' for Help                          ||\n"
			+ "=================================================";

	private final String COMMAND_PROMPT = "\n" 
			+ " AVAILABLE COMMANDS:                             \n"
			+ " 1 to Create equipment on the Well               \n"
			+ " 2 to Print equipment info                       \n"
			+ " 3 to Export equipment info                      \n"
			+ " 4 to Exit                                       \n"
			+ "=================================================";

	private final String BYE_PROMPT = ""
			+ "=================================================\n"
			+ " Exiting...                                      \n"
			+ " Bye!                                            \n"
			+ "=================================================";

	public void run() {
		DataBaseHandler.initialise();				//create tables;
		System.out.println(GREETING_PROMPT);
		while (running) {
			System.out.print("Enter a command > ");
			String prompt = scanner.nextLine().toLowerCase(Locale.ROOT);
			if ("1".equals(prompt)) {
				createEquipment();
			} else if ("2".equals(prompt)) {
				printEquipmentTable();
			} else if ("3".equals(prompt)) {
				exportToXML();
			} else if ("4".equals(prompt)) {
				running = false;
			} else if ("h".equals(prompt)) {
				System.out.println(COMMAND_PROMPT);
			} else {
				System.out.println("Invalid command! Enter 'h' for 'Help'");
			}
		}
		WellRepositoryImpl.closeSession();
		System.out.println(BYE_PROMPT);
	}

	private void createEquipment() {
		System.out.println("Creating equipment");
		String wellName = "";
		int equipmentAmount = 0;
		try {
			System.out.print("Please enter Well name: ");
			wellName = scanner.nextLine().trim();
			if (wellName.contains(" ") || wellName.matches("\\s++") || wellName.length() == 0) {
				throw new IllegalArgumentException("Please enter valid name");
			}
			System.out.print("Please enter equipment amount: ");
			equipmentAmount = Integer.parseInt(scanner.nextLine());
			wellService.createEquipment(wellName, equipmentAmount);
			System.out.println("Equipment created on the well: " + wellName);
		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		}
	}

	private void printEquipmentTable() {
		System.out.print("Please enter well names separated by spaces: ");
		Set<String> wantedNames = Arrays.stream(scanner.nextLine().split("\\s++")).collect(Collectors.toSet());
		PrintHandler.printEquipmentTableMap(wellService.getAllWellsByNameSet(wantedNames));
	}

	private void exportToXML() {
		System.out.print("Please enter name of the file: ");
		String fileName = scanner.nextLine().toLowerCase(Locale.ROOT);
		Map<Well, List<Equipment>> listMap = wellService.getAllWells();
		ExportHandler.export(fileName, listMap);
	}
}

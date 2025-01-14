/*
 Copyright (C) 2020. Doug Estep -- All Rights Reserved.
 Copyright Registration Number: TXU002159309.

 This file is part of the Tag My Code application.

 This application is protected under copyright laws and cannot be used, distributed, or copied without prior written
 consent from Doug Estep.  Unauthorized distribution or use is strictly prohibited and punishable by domestic and
 international law.

 Proprietary and confidential.
 */
package edu.institution;

import java.io.File;

import edu.institution.actions.asn3.SerializedUserRepository;

/**
 * The command line interface for LinkedIn.
 */
public class LinkedInCLI {
	private static String PATH = System.getProperty("user.home") + File.separator + "Java2" + File.separator;
	private static String FILE_NAME = "LinkedInUsers.dat";

	public static void main(String[] args) {
		UserRepository userRepository = new SerializedUserRepository();
		userRepository.init(PATH, FILE_NAME);
		ApplicationController controller = new ApplicationController(userRepository);
		controller.process();
	}
}

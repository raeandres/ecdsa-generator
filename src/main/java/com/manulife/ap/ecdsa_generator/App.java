package com.manulife.ap.ecdsa_generator;

import java.security.GeneralSecurityException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


import com.google.crypto.tink.config.TinkConfig;

/**
 *
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
	    
		TinkConfig.register();

//	    Commands commands = new Commands();
		KeyUtil keyUtil = new KeyUtil();
	    CmdLineParser parser = new CmdLineParser(keyUtil);
	    try {
	      parser.parseArgument(args);
	    } catch (CmdLineException e) {
	      System.out.println(e);
	      e.getParser().printUsage(System.out);
	      System.exit(1);
	    }
	    try {
	      keyUtil.keyCommand.run();
	    } catch (GeneralSecurityException e) {
	      System.out.println("Cannot encrypt or decrypt, got error: " + e.toString());
	      System.exit(1);
	    }
	  }
}

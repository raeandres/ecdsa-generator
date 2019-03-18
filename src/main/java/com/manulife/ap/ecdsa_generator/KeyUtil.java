package com.manulife.ap.ecdsa_generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.PublicKeySign;
import com.google.crypto.tink.signature.SignatureKeyTemplates;


public class KeyUtil {
	
	public interface KeyCommand{
		//interface for command-line
		public void run() throws Exception;
	}
	
	static class PairOptions{
		
		@Option(name = "--keyType",
				required = true,
				usage = "Key Type (public / private)")
		String type;
		
		@Option(name = "--env",
				required = true,
				usage = "Environment name")
		String environment;
		
		@Option(name = "--country",
				required = true,
				usage = "Country prefix")
		String country;
	}
	
	public static class GeneratePublicPair extends PairOptions implements KeyCommand{

		@Override
		public void run() throws Exception {
			// TODO Auto-generated method stub
			
			KeyUtil util = new KeyUtil();
			
			//Generate public key file
			if(type.equalsIgnoreCase("public")) {
				util.createPairFile(generateKey(), KeyType.PUBLIC, environment, country, ".b64");
			}
			//Generate private key file
			if(type.equalsIgnoreCase("private")) {
				util.createPairFile(generateKey(), KeyType.PRIVATE, environment, country, ".b64");
			}
			
		}
		
	}
	
	enum KeyType{
    	PRIVATE,
    	PUBLIC
    }
	
	
	static KeyPair generateKey() {
		
		Security.addProvider(new BouncyCastleProvider());
		
    	try {
    		ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("B-571");
    		
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			
			keyGen.initialize(ecSpec, random);
			
			return keyGen.generateKeyPair();
			
    	} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
    		e.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return null;
    }
	
	void createPairFile(KeyPair keyPair, KeyType type,String envName, String countryName, String fileExtension) throws IOException {
		
		File outputFile = null;
		
		FileOutputStream stream = null;
		
		switch (type) {
		
		case PRIVATE:
			outputFile = new File("PRIVATE_" + envName + "_" + countryName + fileExtension);
			stream = new FileOutputStream(outputFile);
			try {
			stream.write(keyPair.getPrivate().getEncoded());
			} finally {
			stream.close();	
			}
			break;
		case PUBLIC:
			outputFile = new File("PUBLIC_" + envName + "_" + countryName + fileExtension);
			stream = new FileOutputStream(outputFile);
			try {
			stream.write(keyPair.getPublic().getEncoded());
			} finally {
			stream.close();	
			}
			break;
		}
		
	}
	
	static class Options {

	    @Option(
	      name = "--keyset",
	      required = true,
	      usage = "Keyset Output File")
	    File keyset;

	    @Option(name = "--from", 
	    		required = true, 
	    		usage = "Target source file")
	    File inFile;
	    
	    @Option(name = "--env",
				required = true,
				usage = "Environment name")
		String environment;
		
		@Option(name = "--country",
				required = true,
				usage = "Country prefix")
		String country;
	    
	    @Option(name = "--extension",
	    		required = true,
	    		usage = "For assigning a file extension to the created signed file")
	    String extension;

	}
	
	public static class Encrypt extends Options implements KeyCommand{

		@Override
		public void run() throws Exception {
			// TODO Auto-generated method stub
			KeyUtil util = new KeyUtil();
			
			util.createSignedFile(signECDSAKeySet(generateKeysetHandle(keyset),inFile),environment,country,"." + extension);
			
		}
		
	}
    
    
    static KeysetHandle generateKeysetHandle(File keyset) throws GeneralSecurityException, IOException {
    	
    	if(keyset.exists()) {
    		return CleartextKeysetHandle.read(JsonKeysetReader.withFile(keyset));
    	}
    	KeysetHandle handle = KeysetHandle.generateNew(SignatureKeyTemplates.ECDSA_P256);
        CleartextKeysetHandle.write(handle, JsonKeysetWriter.withFile(keyset));
        return handle;
    }
    
    static byte[] signECDSAKeySet(KeysetHandle keysetHandle, File input) throws IOException {
    	try {
			
			
			System.out.println("Private Key: " + keysetHandle);
			
			// 2. Get the primitive.
			PublicKeySign signer = keysetHandle.getPrimitive(PublicKeySign.class);
		    
		    
		    // 3. Use the primitive to sign.
		  return signer.sign(Files.readAllBytes(input.toPath()));
      
			
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    
    void createSignedFile(byte[] signedKeySet, String envName, String countryName, String fileExtension) throws IOException {
    	
    	File signedOutFile = new File(envName + "_" + countryName + fileExtension); 
    	
    	FileOutputStream stream = new FileOutputStream(signedOutFile);
    	
    	String signedKeySetToBase64 = Base64.getEncoder().encodeToString(signedKeySet);
		
		try {
			stream.write(signedKeySetToBase64.getBytes());
			
		} finally {
			stream.close();
		}
    	
    	
    }
    
   
    
    @Argument(
    	    metaVar = "command",
    	    required = true,
    	    handler = SubCommandHandler.class,
    	    usage = "The subcommand to run"
    	  )
    	  @SubCommands({
    		@SubCommand(name = "genkeypair", impl = GeneratePublicPair.class),
    	    @SubCommand(name = "encrypt", impl = Encrypt.class)
    	  })
    	  KeyCommand keyCommand;
	

}

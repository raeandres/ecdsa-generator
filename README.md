
/*CLEAN THE PROJECT*/
mvn clean

/*PACKAGE THE PROJECT*/
mvn package

/*COMMANDS*/

 ****GENERATE PUBLIC KEY PAIR************
 —-type : Public Key Type.
	    @param = KeyPair type (public/private)

 —-env : Abbreviated Environment Name.
	    @param = EnvironmentName (DEV/SIT/UAT/PROD/PRE-PROD)
 —-country : Country Prefix.
	    @param = CountryName (PH/HK/US/JP/CN/VN)


 /*TO EXECUTE JAR IN TERMINAL*/
 	java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar
	genkeypair
	—-type {PublicKeyType}
	—-env {ENVIRONMENT_NAME}
	—-country {COUNTRY_PREFIX}

 //sample: java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar genkeypair --keyType public --env DEV --country VN

 /*TO EXECUTE IN MAVEN*/
	mvn exec:java -Dexec.args="
	genkeypair
	—-type {PublicKeyType}
	—-env {ENVIRONMENT_NAME}
	—-country {COUNTRY_PREFIX}


 //sample: mvn exec:java -Dexec.args="genkeypair --keyType public --env DEV --country VN"
	   mvn exec:java -Dexec.args="genkeypair --keyType private --env DEV --country VN"


 *****SIGNING*******

 —-keyset : The file that will be created by the program. It is a clearText public key file 
	    @param = FileName.FileExtension (.b64)
 —-from : Target data source to be encrypted by the public keyset.
	    @param = SourceFileName.FileExtension (.txt/.json/.cfg/.b64/etc)
 —-env : Abbreviated Environment Name.
	    @param = EnvironmentName (DEV/SIT/UAT/PROD/PRE-PROD)
 —-country : Country Prefix.
	    @param = CountryName (PH/HK/US/JP/CN/VN)
 —-extension : File extension of the created signed public key file.
	    @param = FileExtension (.b64/.cfg/.der)

/*TO EXECUTE JAR IN TERMINAL:*/ 
	java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar 
	encrypt 
	—-keyset {FileName.FileExtension} 
	—-from {TargetFile.txt} 
	—-env {ENVIRONMENT_NAME} 
	—-country {COUNTRY_PREFIX} 
	—-extension {fileExtension(b64/cfg/txt/etc)}

//sample: java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar encrypt --keyset keySet.b64 --from keyFile.txt --env DEV --country VN -—extension b64

/*TO EXECUTE VIA MAVEN:*/
	mvn exec:java -Dexec.args”
	encrypt 
	—-keyset {FileName.b64} 
	—-from {TargetFile.txt} 
	—-env {ENVIRONMENT_NAME} 
	—-country {COUNTRY_PREFIX} 
	—-extension {fileExtension(b64/cfg/txt/etc)}”

//sample: mvn exec:java -Dexec.args="encrypt --keyset keySet.b64 --from keyFile.txt --env DEV --country VN --extension b64"










# ECDSA Generator
This project is a simple java program that aims to create Public-Private key-pair and Sign a data message using Elliptic Curve Algorithm (EC)


## To pull the repository 
```bash
git pull https://git.ap.manulife.com/scm/~andrera_mfcgd.com/ecdsa-generator.git
```

## Clean the package
```bash
mvn clean
```

## Create Package from project
```bash
mvn package
```
This will create ECDSA_GENERATOR-jar-with-dependencies.jar

# Generate Public / Private key-pair

## Commands
```java
—-type : Public Key Type.
	    @param = KeyPair type

 —-env : Abbreviated Environment Name.
	    @param = EnvironmentName (DEV/SIT/UAT/PROD/PRE-PROD)

 —-country : Country Prefix.
	    @param = CountryName (PH/HK/US/JP/CN/VN)

```

### To execute Jar in terminal:
```java 

java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar
	genkeypair
	—-type {PublicKeyType}
	—-env {ENVIRONMENT_NAME}
	—-country {COUNTRY_PREFIX}

```

#### Sample:
```java
java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar genkeypair --keyType public --env DEV --country VN
```

### To execute from maven project through terminal:
```mvn exec:java -Dexec.args="
	genkeypair
	—-type {PublicKeyType}
	—-env {ENVIRONMENT_NAME}
	—-country {COUNTRY_PREFIX}
```
#### Sample:
```bash
mvn exec:java -Dexec.args="genkeypair --keyType public --env DEV --country VN"
mvn exec:java -Dexec.args="genkeypair --keyType private --env DEV --country VN"
```

# Signing
## Commands
```java
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
```

### To execute Jar in terminal:
```bash
    java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar 
	encrypt 
	—-keyset {FileName.FileExtension} 
	—-from {TargetFile.txt} 
	—-env {ENVIRONMENT_NAME} 
	—-country {COUNTRY_PREFIX} 
	—-extension {fileExtension(b64/cfg/txt/etc)}
```
#### Sample:
```bash 
java -jar target/ECDSA_GENERATOR-jar-with-dependencies.jar encrypt --keyset keySet.b64 --from keyFile.txt --env DEV --country VN -—extension b64
```

### To execute from maven project through terminal:
```bash
    mvn exec:java -Dexec.args”
	encrypt 
	—-keyset {FileName.b64} 
	—-from {TargetFile.txt} 
	—-env {ENVIRONMENT_NAME} 
	—-country {COUNTRY_PREFIX} 
	—-extension {fileExtension(b64/cfg/txt/etc)}”
```
#### Sample:
```bash
mvn exec:java -Dexec.args="encrypt --keyset keySet.b64 --from keyFile.txt --env DEV --country VN --extension b64"
```

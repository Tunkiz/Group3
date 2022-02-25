/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackchain;

import java.security.MessageDigest;
import java.util.Date;

/**
 *
 * @author tunki
 */
public class BlockChain {

    /**
     * @param args the command line arguments
     */
        public String hash;
	public String previousHash;
	private final String data; //our data will be medical records.
	private final long timeStamp;

	//Block Constructor.
	public BlockChain(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
                this.hash = calculateHash();
	}
        
        public String calculateHash() {
	String calculatedhash = applySha256( 
			previousHash +
			Long.toString(timeStamp) +
			data 
			);
	return calculatedhash;
        }
        public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuilder hexString = new StringBuilder(); 
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
        
    public static void main(String[] args) {
                BlockChain genesisBlock = new BlockChain("medical_history,treatments_received, test_results, diagnoses, and medications_taken", "0");
		
                System.out.println("Hash for block 1 : " + genesisBlock.hash);
		BlockChain secondBlock = new BlockChain("medical_history,treatments_received, test_results, diagnoses, and medications_taken",genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		BlockChain thirdBlock = new BlockChain("medical_history,treatments_received, test_results, diagnoses, and medications_taken",secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);
    }
    
    
}

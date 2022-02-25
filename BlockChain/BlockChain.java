/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackchain;

/**
 *
 * @author tunki
 */
public class BlockChain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                Block genesisBlock = new Block("0000", "0");
		
                System.out.println("Hash for block 1 : " + genesisBlock.hash);
		Block secondBlock = new Block("medical_history,treatments_received, test_results, diagnoses, and medications_taken",genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		Block thirdBlock = new Block("medical_history,treatments_received, test_results, diagnoses, and medications_taken",secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);
    }
    
}

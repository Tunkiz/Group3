/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tunki
 */
class Clients{
    private String Name, Id, medHistory, testResults, diagnoses,medTaken;

    public Clients(String Name, String Id, String medHistory, String testResults, String diagnoses, String medTaken) {
        this.Name = Name;
        this.Id = Id;
        this.medHistory = medHistory;
        this.testResults = testResults;
        this.diagnoses = diagnoses;
        this.medTaken = medTaken;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getMedHistory() {
        return medHistory;
    }

    public void setMedHistory(String medHistory) {
        this.medHistory = medHistory;
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    public String getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }

    public String getMedTaken() {
        return medTaken;
    }

    public void setMedTaken(String medTaken) {
        this.medTaken = medTaken;
    }
    
    
}
class Block{
    public String hash;
	public String previousHash;
	private final Clients client; //our data will be medical records.
	private final long timeStamp;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public Clients getClient() {
        return client;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
        
    public Block(Clients client) {
        this.client = client;
	this.previousHash = "0000";
	this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
	}
    public String calculateHash() {
	String calculatedhash = applySha256(previousHash +
			Long.toString(timeStamp) +
			client
			);
	return calculatedhash;
        }
    public static String applySha256(String input){		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuilder hexString = new StringBuilder(); // This will contain hash as hexidecimal
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
}
public final class BlockChain {

    /**
     * @param args the command line arguments
     */
    private final List<Block> chain = new ArrayList<>();
    public BlockChain(){
        this.chain.add(genesisBlock());
    }
    public void addBlocks(Block newBlock){
        int chainSize = this.chain.size();
        newBlock.setPreviousHash(this.chain.get(chainSize-1).getHash());
        newBlock.setHash(newBlock.calculateHash());
        this.chain.add(newBlock);
    }
    
    public boolean isChainValid(){
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previouBlock = this.chain.get(i-1);
            if (currentBlock.getPreviousHash() != previouBlock.getHash()) {
                return false;
            }
        }
        return true;
    }
    public Block genesisBlock(){
        return new Block(null);
    }

    public List<Block> getChain() {
        return chain;
    }
    
    public static void main(String[] args) {
        Clients clientOne = new Clients("Tunkis", "1235", "Addict", "Positive", "D1", "M1");
        Clients clientTwo = new Clients("Tunkis", "1235", "Addict", "Positive", "D1", "M1");
        Clients clientThree = new Clients("Tunkis", "1235", "Addict", "Positive", "D1", "M1");
        Clients clientFour = new Clients("Tunkis", "1235", "Addict", "Positive", "D1", "M1");
        Clients clientFive = new Clients("Tunkis", "1235", "Addict", "Positive", "D1", "M1");
        
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(new Block(clientOne));
        blocks.add(new Block(clientTwo));
        blocks.add(new Block(clientThree));
        blocks.add(new Block(clientFour));
        blocks.add(new Block(clientFive));
        
        BlockChain blockchain = new BlockChain();
        for (Block block : blocks) {
            blockchain.addBlocks(block);
        }
        int count = 0;
        for (Block block : blockchain.getChain()) {
            System.out.println("......................\n"
                    +"Block "+count+"\n"
                    +"Time stamp "+block.getTimeStamp()+"\n"
                    +"Clint Information "+block.getClient()+"\n"
                    +"Previous Hash "+block.getPreviousHash()+"\n"
                    +"Hash "+block.getHash()
            );
            count++;
            
        }
        
        System.out.println("Is chain valid "+blockchain.isChainValid());
        
    }
   
    
}


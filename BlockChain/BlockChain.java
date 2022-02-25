
package blackchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tunki
 */
public class BlackChain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // First of all we instantiate the BlockChain class itself.
        BlockChain blockChain = new BlockChain();
        // we will instantiate the Miner class to fetch the minor object.
        BlockAdd miner = new BlockAdd();


        InsurancePay ip1 = new InsurancePay("Tunkis", true, new Date(),20000);
        InsurancePay ip2 = new InsurancePay("Rendi", true, new Date(),20000);
        InsurancePay ip3 = new InsurancePay("phumlane", true, new Date(),20000);

        Block block0 = new Block(0,"transaction1",Constants.GENESIS_PREV_HASH,ip1);

        miner.mine(block0, blockChain);
        Block block1 = new Block(1,"transaction2",blockChain.getBlockChain().get(blockChain.size()-1).getHash(),ip2);
        miner.mine(block1, blockChain);
        Block block2 = new Block (2,"transaction3",blockChain.getBlockChain().get(blockChain.size()-1).getHash(),ip3);
        miner.mine(block2, blockChain);
        System.out.println("\n"+ "BLOCKCHAIN:\n"+blockChain);
        
    }
    
}
class InsurancePay{
    String name;
    boolean isPaid = false;
    Date date;
    long amount;

    public InsurancePay(String name, boolean isPaid, Date date, long amount){
        this.name = name;
        this.isPaid = isPaid;
        this.date = date;
        this.amount = amount;

    }
}
class Constants {

    private Constants() {

    }

    public static final int DIFFICULTY = 1;
    public static final double MINER_REWARD = 10;
    public static final String GENESIS_PREV_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

}
class SHA256Helper {

    public static String generateHash(String data) {
        try {
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            StringBuffer hexadecimalString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hexadecimal = Integer.toHexString(0xff & hash[i]);
                if (hexadecimal.length() == 1) hexadecimalString.append('0');
                hexadecimalString.append(hexadecimal);
            }
            return hexadecimalString.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
class Block {
    private int id;
    private int nonce;
    
    private long timeStamp;
    private String hash;
    private String previousHash;
    private String transaction;
    private InsurancePay insurancePay;

    public Block(int id, String transaction, String previousHash, InsurancePay insurancePay) {

        this.id = id;

        this.transaction = transaction;
        this.insurancePay = insurancePay;

        this.previousHash = previousHash;

        this.timeStamp = new Date().getTime();

        generateHash();

    }
    public void generateHash() {

        String dataToHash = Integer.toString(id) + previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transaction.toString();

        String hashValue = SHA256Helper.generateHash(dataToHash);

        this.hash = hashValue;

    }


    public String getHash() {

        return this.hash;

    }

    public void setHash(String hash) {

        this.hash = hash;

    }

    public String getPreviousHash() {

        return this.previousHash;

    }

    public void setPreviousHash(String previousHash) {

        this.previousHash = previousHash;
    }

    public void incrementNonce() {

        this.nonce++;
    }

    @Override

    public String toString() {

        return this.id+"-"+this.transaction+"-"+this.hash+"-"+this.previousHash+"-";

    }

}
class BlockChain {
    //instantiating the list of blocks
    private List<Block> blockChain;

    public BlockChain() {
        
        this.blockChain = new ArrayList<>();
    }

    public void addBlock(Block block) {
        this.blockChain.add(block);
    }
    public List<Block> getBlockChain() {
        return this.blockChain;
    }
    public int size() {
        return this.blockChain.size();
    }

    @Override
    public String toString() {
        String blockChain = "";
        for(Block block : this.blockChain)
            blockChain+=block.toString()+"\n";
        return blockChain;
    }

}
class BlockAdd {

    public void mine(Block block, BlockChain blockChain) {

        while(notGoldenHash(block)) {
            //generating the block hash
            block.generateHash();
            block.incrementNonce();
        }

        
        
        //appending the block to the blockchain
        blockChain.addBlock(block);
        
        

    }

    
    public boolean notGoldenHash(Block block) {

        String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');

        return !block.getHash().substring (0, Constants.DIFFICULTY).equals (leadingZeros);
    }
    

}
    

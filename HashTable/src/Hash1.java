public class Hash1 extends HashTable {

    public Hash1() {
        super(1000);
    }

    @Override
    protected int hashFunction(String str) {
        int hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return Math.abs(hash);
    }

}

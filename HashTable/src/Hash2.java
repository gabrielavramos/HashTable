public class Hash2 extends HashTable {

    public Hash2() {
        super(1000);
    }

    @Override
    protected int hashFunction(String str) {
        int hash = 0;
        int base = 37;
        for (char c : str.toCharArray()) {
            hash = hash * base + (int) c;
        }
        return Math.abs(hash);
    }
}

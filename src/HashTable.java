/****************
Roman Alonzo
CS 241 A3
11/21/2018
Implementation of hashmap, to be used in heap for quick value lookups.
****************/

package heap;

/** A hash table modeled after java.util.Map. It uses chaining for collision
 * resolution and grows its underlying storage by a factor of 2 when the load
 * factor exceeds 0.8. */
public class HashTable<K,V> {

    protected Pair[] buckets; // array of list nodes that store K,V pairs
    protected int size; // how many items currently in the map


    /** class Pair stores a key-value pair and a next pointer for chaining
     * multiple values together in the same bucket, linked-list style*/
    public class Pair {
        protected K key;
        protected V value;
        protected Pair next;

        /** constructor: sets key and value */
        public Pair(K k, V v) {
            key = k;
            value = v;
            next = null;
        }

        /** constructor: sets key, value, and next */
        public Pair(K k, V v, Pair nxt) {
            key = k;
            value = v;
            next = nxt;
        }

        /** returns (k, v) String representation of the pair */
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    /** constructor: initialize with default capacity 17 */
    public HashTable() {
        this(17);
    }

    /** constructor: initialize the given capacity */
    public HashTable(int capacity) {
        buckets = createBucketArray(capacity);
    }

    /** Return the size of the map (the number of key-value mappings in the
     * table) */
    public int getSize() {
        return size;
    }

    /** Return the current capacity of the table (the size of the buckets
     * array) */
    public int getCapacity() {
        return buckets.length;
    }

    /** Return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * Runtime: average case O(1); worst case O(size) */
    public V get(K key) {
        // TODO 2.1 - do this together with put.
        //calculates index
        int index = key.hashCode() % getCapacity();
        //index used for locations in buckets
        Pair per = buckets[index];
        while (per != null){
           if (per.key.equals(key)){
              //return the pair's value if it exists.
              return per.value;
           }
           per = per.next;
        }
        return null;
    }
        
        
     /** Associate the specified value with the specified key in this map. If
     * the map previously contained a mapping for the key, the old value is
     * replaced. Return the previous value associated with key, or null if
     * there was no mapping for key. If the load factor exceeds 0.8 after this
     * insertion, grow the array by a factor of two and rehash.
     * Runtime: average case O(1); worst case O(size^2 + a.length)*/
    public V put(K key, V val) {
        // TODO 2.2 - do this together with get. For now, don't worry about
        //            growing the array and rehashing.
        
        if (val == null){
         throw new NullPointerException();
        }
        
        
        int index = key.hashCode() % getCapacity();
        
        Pair currPair = buckets[index];
        
        while (currPair != null){
         if (currPair.key.equals(key)){
            //return old value if there's a duplicate found.
            V old = currPair.value;
            currPair.value = val;
            return old;
         }
         currPair = currPair.next;
        }
        //create new entry
        Pair e = buckets[index];
        buckets[index] = new Pair(key, val, e);
        size++;
        
// TODO 2.5 - modify this method to grow and rehash if the load factor
// exceeds 0.8.

        if ((1.0*size)/getCapacity() >= 0.8){
         growIfNeeded();
        }
        return null;
   } 
 
    /** Return true if this map contains a mapping for the specified key.
     *  Runtime: average case O(1); worst case O(size) */
    public boolean containsKey(K key) {
        int index = key.hashCode() % getCapacity();
        Pair currPair = buckets[index];
        while (currPair != null){
           if (currPair.key.equals(key)){
              //if a pair with the corresponding key is found,
              return true;
           }
           currPair = currPair.next;
        }
        return false;
    }

    /** Remove the mapping for the specified key from this map if present.
     *  Return the previous value associated with key, or null if there was no
     *  mapping for key.
     *  Runtime: average case O(1); worst case O(size)*/
    public V remove(K key) {
        // TODO 2.4
        int index = key.hashCode() % getCapacity();
        Pair per = buckets[index];
        Pair last = null;
        
        while (per != null){
           if (per.key.equals(key)){
              if (last == null){
                 //if the key is found and last has yet to be assigned,
                 buckets[index] = per.next;
              }
              else{
                 //otherwise last's next becomes new pair's next
                 last.next = per.next;
              }
              //decrease the size after removal, and return that value.
              size--;
              return per.value;
           }
           last = per;
           per = per.next;
        }
        return null;
    }      
        

    // suggested helper method:
    /* check the load factor; if it exceeds 0.8, double the array size
     * (capacity) and rehash values from the old array to the new array */
    private void growIfNeeded() {
        // suggested: implement and use this method in put for TODO 2.5
        Pair[] old = buckets;
        int newCapacity = getCapacity() * 2;
        
        Pair[] newBuckets = createBucketArray(newCapacity);
        //for each bucket b
        for (int i=0; i < old.length; i++){
         Pair e;
         e = old[i];
         //for each element in buckets
         if (e != null){
            int index = e.key.hashCode() % getCapacity();
            newBuckets[index] = e;
         }
        }        
        
    }
                                

    /* useful method for debugging - prints a representation of the current
     * state of the hash table by traversing each bucket and printing the
     * key-value pairs in linked-list representation */
    protected void dump() {
        System.out.println("Table size: " + getSize() + " capacity: " +
                getCapacity());
        for (int i = 0; i < buckets.length; i++) {
            System.out.print(i + ": --");
            Pair node = buckets[i];
            while (node != null) {
                System.out.print(">" + node + "--");
                node = node.next;

            }
            System.out.println("|");
        }
    }

    /*  Create and return a bucket array with the specified size, initializing
     *  each element of the bucket array to be an empty LinkedList of Pairs.
     *  The casting and warning suppression is necessary because generics and
     *  arrays don't play well together.*/
    @SuppressWarnings("unchecked")
    protected Pair[] createBucketArray(int size) {
        return (Pair[]) new HashTable<?,?>.Pair[size];
    }
}

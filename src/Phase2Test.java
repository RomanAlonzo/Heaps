
package heap;

import java.util.Set;
import java.util.Random;
import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Phase2Test {

    /* make a hash table containing the giving key-value mappings */
    private static <K,V> HashTable<K,V> make(
            K[] keySet, V[] valSet, int initCap) {
        assert keySet.length == valSet.length;
        HashTable<K,V> hm = new HashTable<K,V>(initCap);
        for (int i = 0; i < keySet.length; i++) {
            hm.put(keySet[i], valSet[i]);
        }

        return hm;
    }

    private static <K,V> HashTable<K,V> make(K[] keySet, V[] valSet) {
        return make(keySet, valSet, 17);
    }

    /* check that the hash table contains the appropriate mappings */
    private static <K,V> void check(K[] keySet, V[] valSet, HashTable<K,V> hm) {
        assert keySet.length == valSet.length;

        assertEquals(keySet.length, hm.getSize());
        for (int i = 0; i < keySet.length; i++) {
            assertEquals(valSet[i], hm.get(keySet[i]));
        }
    }


    //@Test
    //[>* Test zero-param constructor <]
    //public void test200Constructor() {
        //HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>();
        //assertEquals(0, hm.getSize());
        //assertEquals(17, hm.getCapacity());
    //}

    //@Test
    //[>* Test constructor that takes a capacity <]
    //public void test201Constructor() {
        //HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>(31);
        //assertEquals(0, hm.getSize());
        //assertEquals(31, hm.getCapacity());
    //}


    @Test
    /** Test put and get with no and no mod*/
    public void test210PutGet() {
        HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>();
        for (int i = 0; i < 8; i++) {
            assertEquals(null, hm.put(i,i));
            assertEquals(i+1, hm.getSize());
            assertEquals(17, hm.getCapacity());
        }
        for (int i = 0; i < 8; i++) {
            assertEquals(new Integer(i), hm.get(i));
        }
        for (int i = 8; i < 17; i++) {
            assertEquals(null, hm.get(i));
        }
    }

    @Test
    /** Test put and get with no collisions but mod needed */
    public void test211PutGet() {
        Integer[] keys = new Integer[]{0,1,2,3,22,23,24,25};
        Integer[] vals = new Integer[]{0,1,2,3,22,23,24,25};
        HashTable<Integer,Integer> hm = make(keys, vals);
        check(keys, vals, hm);
    }

    @Test
    /** Test that put overwrites and returns a pre-existing value */
    public void test212Put() {
        Integer[] keys = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        Integer[] vals = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        HashTable<Integer,Integer> hm = make(keys, vals);

        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(i), hm.put(i,i+1));
            assertEquals(10, hm.getSize());
        }
    }

    @Test
    /** Test put/get with collisions */
    public void test230PutGet() {
        Integer[] keys = new Integer[]{0,1,2,3,17,18,19};
        Integer[] vals = new Integer[]{0,1,2,3,17,18,19};
        HashTable<Integer,Integer> hm = make(keys, vals);
        check(keys, vals, hm);
    }

    @Test
    /** Test that put overwrites and returns a pre-existing value, with
     * collisions */
    public void test231Put() {
        Integer[] keys = new Integer[]{0,1,2,3,17,18,19};
        Integer[] vals = new Integer[]{0,1,2,3,17,18,19};
        HashTable<Integer,Integer> hm = make(keys, vals);

        for (int i = 0; i < keys.length; i++) {
            assertEquals(vals[i], hm.put(keys[i],keys[i]+1));
            assertEquals(7, hm.getSize());
        }
        keys = new Integer[]{0,1,2,3,17,18,19};
        vals = new Integer[]{1,2,3,4,18,19,20};
        check(keys, vals, hm);
    }

    @Test
    /** Test containsKey (no collisions) */
    public void test240ContainsKey() {
        Integer[] keys = new Integer[]{0,1,2,3,4,5,26,27,28,29};
        Integer[] vals = new Integer[]{0,1,2,3,4,5,26,27,28,29};
        HashTable<Integer,Integer> hm = make(keys, vals);

        int i = 0;
        int ki = 0;
        while (i < 35) {
            if (ki < keys.length && keys[ki] == i) {
                assertEquals(true, hm.containsKey(i));
                ki++;
            } else {
                assertEquals(false, hm.containsKey(i));
            }
            i++;
        }
    }

    @Test
    /** Test containsKey (with collisions) */
    public void test241ContainsKey() {
        Integer[] keys = new Integer[]{0,1,2,17,18,19};
        Integer[] vals = new Integer[]{0,1,2,17,18,19};
        HashTable<Integer,Integer> hm = make(keys, vals);

        int i = 0;
        int ki = 0;
        while (i < 34) {
            if (ki < keys.length && keys[ki] == i) {
                assertEquals(true, hm.containsKey(i));
                ki++;
            } else {
                assertEquals(false, hm.containsKey(i));
            }
            i++;
        }
    }

    @Test
    /** Test that remove works (no collisions) */
    public void test250Remove() {
        Integer[] keys = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        Integer[] vals = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        HashTable<Integer,Integer> hm = make(keys, vals);

        for (int i = 0; i < 10; i++) {
            assertEquals(vals[i], hm.remove(keys[i]));
            assertEquals(10-i-1, hm.getSize());
        }

    }


    @Test
    /** Test that remove works (with collisions) */
    public void test251Remove() {
        Integer[] keys = new Integer[]{0,1,2,17,18,19};
        Integer[] vals = new Integer[]{0,1,2,17,18,19};
        HashTable<Integer,Integer> hm = make(keys, vals);


        assertEquals(vals[0], hm.remove(keys[0]));
        assertEquals(5, hm.getSize());
        assertEquals(vals[5], hm.remove(keys[5]));
        assertEquals(4, hm.getSize());
        assertEquals(vals[4], hm.remove(keys[4]));
        assertEquals(3, hm.getSize());
        assertEquals(vals[2], hm.remove(keys[2]));
        assertEquals(2, hm.getSize());
        assertEquals(vals[1], hm.remove(keys[1]));
        assertEquals(1, hm.getSize());
        assertEquals(vals[3], hm.remove(keys[3]));
        assertEquals(0, hm.getSize());

    }



    @Test
    /** Test that table grows when load factor exceeds 0.8 */
    public void test280Grow() {
        Integer[] keys = new Integer[]{0,1,2,17,18,19,20,21,5,6,7,8,9,11};
        Integer[] vals = new Integer[]{0,1,2,17,18,19,20,21,5,6,7,8,9,11};
        HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>();

        for (int i = 0; i < 13; i++) {
            hm.put(keys[i], vals[i]);
            assertEquals(17, hm.getCapacity());
        }
        hm.put(keys[13], vals[13]);
        assertEquals(34, hm.getCapacity());
    }

    @Test
    /** Test that rehashing works after growth */
    public void test281Rehash() {
        Integer[] keys = new Integer[]{0,1,2,17,18,19,20,21,5,6,7,8,9,11,12,63,64};
        Integer[] vals = new Integer[]{0,1,2,17,18,19,20,21,5,6,7,8,9,11,12,63,64};
        HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>();

        for (int i = 0; i < keys.length; i++) {
            hm.put(keys[i], vals[i]);
        }
        assertEquals(34, hm.getCapacity());
        check(keys, vals, hm);
    }


    @Test
    /** Test a small example with strings */
    public void test290Strings() {
        String[] keys = new String[]{"iztf", "uiu", "eqm", "rzh", "vjw", "ris", "tut", "wbb", "sjb", "lii", "urv", "fhm"};
        Integer[] vals = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};

        HashTable<String,Integer> hm = make(keys, vals);
        check(keys, vals, hm);

    }

    @Test
    /** Test a big example with strings, including put (new and replace), get,
     * and remove */
    public void test291Strings() {
        HashMap<String,String> truth = new HashMap<String,String>();
        HashTable<String,String> hm = new HashTable<String,String>();

        try {
            Scanner sc = new Scanner(new File("P2TestInput.txt"));
            while (sc.hasNext()) {
                String k = sc.next();
                String v = sc.next();
                hm.put(k, v);
                truth.put(k, v);
            }
        } catch (FileNotFoundException e) {
            assertTrue("File P2TestInput.txt from skeleton repo not found.", false);
        }

        Set<String> trueKeySet = truth.keySet();
        Random rand = new Random(0);
        Iterator<Map.Entry<String,String>> iter = truth.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,String> entry = iter.next();
            String k = entry.getKey();
            double roll = rand.nextDouble();
            if (roll > 0.9) {
                // replace k's mapped value with a new random one
                String newVal = "";
                for (int i = 0; i < 4; i++) {
                    newVal = newVal + (char) (97 + rand.nextInt(26));
                }
                hm.put(k, newVal);
                truth.put(k, newVal);
            } else if (roll < 0.1) {
                // remove k from the mapping
                hm.remove(k);
                iter.remove();
            }
        }
        // check that the mappings are equivalent:
        assertEquals(truth.size(), hm.getSize());
        for (String k : truth.keySet()) {
            assertTrue(hm.containsKey(k));
            assertEquals(truth.get(k), hm.get(k));
        }
        assertEquals(hm.getCapacity(), 2176);
    }

    //@Test
    //public void example() {
        //HashTable<Integer,Integer> hm = new HashTable<Integer,Integer>(4);
        //hm.put(0,0);
        //hm.put(4,1);
        //hm.put(19,1);
        //hm.put(19,4);
        //hm.dump();
    //}


}

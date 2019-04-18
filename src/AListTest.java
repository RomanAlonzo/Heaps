/*************
Roman Alonzo
CSCI 241
Lab6, 11/6/2018
Testing the methods in AList.java
*************/

package heap;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AListTest {

    @Test
    /** Test constructors, making sure they're sizing the array correctly.
     * */
    public void test00Constructors() {
        AList<Integer> al = new AList<Integer>();
        assertEquals(0, al.size());
        assertEquals(8, al.getCap());

        al = new AList<Integer>(18);
        assertEquals(18, al.getCap());
    }


    @Test
    /** Test resize, which indirectly tests growIfNeeded. */
    public void test10Resize() {
        AList<Integer> al = new AList<Integer>(16);
        al.resize(4);
        assertEquals(4, al.size());
        assertEquals(16, al.getCap()); // shouldn't have grown

        al.resize(18);
        assertEquals(18, al.size());
        assertEquals(32, al.getCap()); // should have grown

        al.resize(32);
        assertEquals(32, al.size());
        assertEquals(32, al.getCap());
    }


    @Test
    /** Test put and get.  */
    public void test20PutGet() {
        AList<Integer> al = new AList<Integer>(16);
        al.resize(16);

        for (int i = 0; i < 16; i++) {
            al.put(i, i);
            assertEquals(new Integer(i), al.get(i));
        }

        try {
            al.get(32);
            al.put(16, 4);
            fail("Didn't throw an exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            // This is supposed to happen
        } catch (Throwable e){
            fail("Threw something other than ArrayIndexOutOfBoundsException: " + e);
        }
    }


    @Test
    /** Test append and pop, making sure it grows as needed */
    public void test30Append() {
        AList<Integer> al = new AList<Integer>(8);

        for (int i = 0; i < 16; i++) {
            al.append(i);
            assertEquals(new Integer(i), al.get(i));
            assertEquals(i+1, al.size());
        }
        assertEquals(16, al.getCap());

        for (int i = 15; i >= 0; i--) {
            assertEquals(new Integer(i), al.pop());
            assertEquals(i, al.size());
        }
        assertEquals(16, al.getCap());
    }
}

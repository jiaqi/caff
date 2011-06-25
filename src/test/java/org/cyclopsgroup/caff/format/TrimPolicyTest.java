package org.cyclopsgroup.caff.format;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link TrimPolicy}
 * 
 * @author jiaqi
 */
public class TrimPolicyTest
{
    /**
     * Verify trim method
     */
    @Test
    public void testTrimForward()
    {
        assertEquals("abcd", TrimPolicy.FORWARD.trim("abcde", 4, AlignPolicy.LEFT));
        assertEquals("bcde", TrimPolicy.FORWARD.trim("abcde", 4, AlignPolicy.RIGHT));
    }
}

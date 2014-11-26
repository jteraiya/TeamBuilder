package com.nibr;

import org.junit.Test;

import static org.junit.Assert.*;

public class TeamBuilderTest {

    @Test
    public void testSpecialLocations() throws Exception {

        TeamBuilder teamBuilder = new TeamBuilder();

        String[] threeElemPaths = {"010", "000", "110"};
        int[] threeElemResults = {1, 1};
        assertArrayEquals(threeElemResults, teamBuilder.specialLocations(threeElemPaths));

        String[] fourElemPaths = {"0010","1000","1100","1000"};
        int[] fourElemResults = {1, 3};
        assertArrayEquals(fourElemResults, teamBuilder.specialLocations(fourElemPaths));

        String[] fiveElemPaths = {"01000","00100","00010","00001","10000"};
        int[] fiveElemResults = {5, 5};
        assertArrayEquals(fiveElemResults, teamBuilder.specialLocations(fiveElemPaths));


    }
}
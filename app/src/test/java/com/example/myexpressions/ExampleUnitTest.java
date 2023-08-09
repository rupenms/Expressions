package com.example.myexpressions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import com.example.myexpressions.fragments.HistoryFragment;
import com.example.myexpressions.fragments.HomeFragment;
import com.example.myexpressions.network.DatabaseBoss;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

   @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}
package com.examine;

import com.google.inject.Inject;
import org.junit.Before;
import org.junit.jupiter.api.*;


public class ExamineDisplayTest {
//    @Inject
//    ExaminePanel examinePanel = new ExaminePanel();
    private static ExaminePanel examinePanel;

    @BeforeAll
    public static void startUp(){
        examinePanel = new ExaminePanel();
    }

    @Test
    @DisplayName("Test to make sure panels are Labels are initially not visible")
    public void testVisible(){
        Assertions.assertAll(() -> Assertions.assertFalse(ExaminePanel.type.isVisible()),
                () -> Assertions.assertFalse(ExaminePanel.name.isVisible()),
                () -> Assertions.assertFalse(ExaminePanel.wikiLnk.isVisible()));
    }

    @Test
    void testName() {
        Assertions.assertEquals(1,1);
    }
}

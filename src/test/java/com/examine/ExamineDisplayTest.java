package com.examine;

import com.google.inject.Inject;
import org.junit.Before;
import org.junit.jupiter.api.*;


public class ExamineDisplayTest {
    @Test
    @DisplayName("Link output of NPC name")
    void testNPCLink() {
        Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Goblin\">Goblin</a>",
                ExaminePanel.getWikiLink("Goblin"));
    }

    @Test
    @DisplayName("Link output of Item name")
    void testItemLink() {
        Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Knife\">Knife</a>",
                ExaminePanel.getWikiLink("Knife"));
    }

    @Test
    @DisplayName("Link output of Object name")
    void testObjectLink() {
        Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Tree\">Tree</a>",
                ExaminePanel.getWikiLink("Tree"));
    }

    @Test
    @DisplayName("Link output of Objects, NPC's, etc. with spaces in name")
    void testNameWithSpaces() {
        Assertions.assertAll(() -> Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Adventurer_Jon\">Adventurer Jon</a>",
                ExaminePanel.getWikiLink("Adventurer Jon")),
                () -> Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Cave_kraken\">Cave kraken</a>",
                        ExaminePanel.getWikiLink("Cave kraken")),
                () ->  Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Bronze_pickaxe\">Bronze pickaxe</a>",
                        ExaminePanel.getWikiLink("Bronze pickaxe")),
                () -> Assertions.assertEquals("<a href=\"https://oldschool.runescape.wiki/w/Robe_bottom_of_darkness\">Robe bottom of darkness</a>",
                        ExaminePanel.getWikiLink("Robe bottom of darkness"))
        );
    }

    @Test
    @DisplayName("Check for header in Panel")
    void headerTest(){
        Assertions.assertTrue(ExaminePanel.heading.isVisible());
    }
}

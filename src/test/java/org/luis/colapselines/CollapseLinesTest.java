package org.luis.colapselines;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CollapseLinesTest {

    /**
     * Test cases:
     * 1: 0 - 1 - 2.
     *   Entrada: ""; Salida: IndexOutOfBoundsException;
     * 2: 0 - 1 - 3 - 4 - 5 - 6 - 8 - 4 - 9
     *   Entrada: "\n"; Salida: "";
     * 3: 0 - 1 - 3 - 4 - 5 - 7 - 8 - 4 - 9
     *   Entrada: "a"; Salida: "a";
     * 4: 0 - 1 - 3 - 4 - 5 - 6 - 8 - 4 - 5 - 7 - 8 - 4 - 9
     *   Entrada: "\na"; Salida: "a";
     * 5: 0 - 1 - 3 - 4 - 5 - 6 - 8 - 4 - 5 - 6 - 8 - 4 - 9
     *   Entrada: "\n\n"; Salida: "";
     * 6: 0 - 1 - 3 - 4 - 5 - 7 - 8 - 4 - 5 - 7 - 8 - 4 - 5 - 7 - 8 - 4 - 9
     *   Entrada: "aaa"; Salida: "aaa";
     * 7: 0 - 1 - 3 - 4 - 5 - 7 - 8 - 4 - 5 - 7 - 8 - 4 - 5 - (6 -) 7 - 8 - 4 - 5 - 6 - 8 - 4 - 9 (Asumo que se pasa por el nodo marcado entre paréntesis, si no, es inalcanzable)
     *   Entrada: "aa\n\n"; Salida: "aa\n";
     * 8: 0 - 1 - 3 - 4 - 5 - 6 - 8 - 4 - 5 - 6 - 8 - 4 - 5 - 7 - 8 - 4 - 9
     *   Entrada: "\n\na"; Salida: "a";
     */

    @Test
    public void collapseLinesOfEmptyStringRaisesException() {
        assertThatThrownBy(() -> CollapseLines.collapseNewLines("")).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void collapseLinesOfOneLineReturnsEmptyString() {
        assertThat(CollapseLines.collapseNewLines("\n")).isEqualTo("");
    }

    @Test
    public void collapseLinesOfOneCharacterReturnsCharacter() {
        assertThat(CollapseLines.collapseNewLines("a")).isEqualTo("a");
    }

    @Test
    public void collapseLinesOfOneLineAndOneCharacterReturnsCharacter() {
        assertThat(CollapseLines.collapseNewLines("\na")).isEqualTo("a");
    }

    @Test
    public void collapseLinesOfTwoLinesReturnsEmptyString() {
        assertThat(CollapseLines.collapseNewLines("\n\n")).isEqualTo("");
    }

    @Test
    public void collapseLinesOfThreeCharactersReturnsTheThreeCharacters() {
        assertThat(CollapseLines.collapseNewLines("aaa")).isEqualTo("aaa");
    }

    @Test
    public void collapseLinesOfTwoCharactersAndTwoLinesReturnsTheTwoCharactersAndOneLine() {
        assertThat(CollapseLines.collapseNewLines("aa\n\n")).isEqualTo("aa\n");
    }

    @Test
    public void collapseLinesOfTwoLinesAndOneCharacterReturnsCharacter() {
        assertThat(CollapseLines.collapseNewLines("\n\na")).isEqualTo("a");
    }

}
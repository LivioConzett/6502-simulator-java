package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompilerTest {

    @Test
    void removeComments(){

        Compiler compiler = new Compiler();

        String input[] = {
                "  and ;this is a comment",
                "  ;hello",
                ";what are you doing",
                "nop   ;yo",
                "hello the     "
        };

        String output[] = {
                "  and",
                "",
                "",
                "nop",
                "hello the     "

        };

        Assertions.assertArrayEquals(output, compiler.removeComments(input));

    }

}

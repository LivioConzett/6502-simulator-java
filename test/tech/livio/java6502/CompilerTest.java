package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompilerTest {

    @Test
    void removeComments(){

        Compiler compiler = new Compiler();

        String[] input = {
                "  and ;this is a comment",
                "  ;hello",
                ";what are you doing",
                "nop   ;yo",
                "hello the     "
        };

        String[] output = {
                "  and",
                "",
                "",
                "nop",
                "hello the     "

        };

        Assertions.assertArrayEquals(output, compiler.removeComments(input));
    }
    @Test
    void replaceVariables(){

        Compiler compiler = new Compiler();

        String[] input = {
                "test=1",
                "nothing = @34",
                "and 123",
                "nop",
                "adc test",
                "lda nothing",
                "bbc   23",
                "adc nothing",
                ".word 'this is a test'"
        };

        String[] output = {
                "",
                "",
                "and 123",
                "nop",
                "adc 1",
                "lda @34",
                "bbc   23",
                "adc @34",
                ".word 'this is a test'"
        };

        Assertions.assertArrayEquals(output, compiler.replaceVariables(input));
    }

    @Test
    void convertNumbers(){

        Compiler compiler = new Compiler();

        String[] input = {
                "test=1",
                "nothing = $a9",
                "nothing = $A9",
                "and @251",
                "ldc %10101001",
                ".word 'this is a test'"
        };

        String[] output = {
                "test=1",
                "nothing = 169",
                "nothing = 169",
                "and 169",
                "ldc 169",
                ".word 'this is a test'"
        };

        Assertions.assertArrayEquals(output, compiler.convertNumbers(input));
    }

}

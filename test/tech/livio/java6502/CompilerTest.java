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
                "test = 169",
                "nothing = $a9",
                "nothing = $A9",
                "and @251",
                "ldc %10101001",
                "char = 'P'",
                ".word 'this is a test'"
        };

        String[] output = {
                "test = $a9",
                "nothing = $a9",
                "nothing = $a9",
                "and $a9",
                "ldc $a9",
                "char = $50",
                ".word 'this is a test'"
        };

        Assertions.assertArrayEquals(output, compiler.convertNumbers(input));
    }

    @Test
    void convertStrings(){

        Compiler compiler = new Compiler();

        String[] input = {
                "test=1",
                "nothing = $a9",
                "nothing = $A9",
                "and @251",
                "ldc %10101001",
                ".word 'this is a test'",
                ".word \"don't you\""
        };

        String[] output = {
                "test=1",
                "nothing = $a9",
                "nothing = $A9",
                "and @251",
                "ldc %10101001",
                ".word 74 68 69 73 20 69 73 20 61 20 74 65 73 74",
                ".word 64 6f 6e 27 74 20 79 6f 75"
        };

        Assertions.assertArrayEquals(output, compiler.convertString(input));
    }

    @Test
    void handelOpCodeTest(){
        Compiler compiler = new Compiler();

        String input = "  adc #$69";
        String output = "69 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc $69";
        output = "65 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc $69,X";
        output = "75 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc $6969";
        output = "6d 69 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc $6969,X";
        output = "7d 69 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc $6969,Y";
        output = "79 69 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc ($69,X)";
        output = "61 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

        input = "  adc ($69),Y";
        output = "71 69";

        Assertions.assertEquals(output, compiler.handelOpCode(input));

    }

}

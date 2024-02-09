package name.zasenko.battlesnake.mapper.asciimatrix;

import name.zasenko.battlesnake.entities.AsciiMatrix;

import java.util.Arrays;

public class GridDecorator {
    public static int LabelWidthX = 1;
    public static int LabelWidthY = 1;
    public static int RullerPaddingX = 1;
    public static int RullerPaddingY = 0;

    public static AsciiMatrix execute(AsciiMatrix asciiMatrix) {
        String[][]grid = new String[asciiMatrix.grid().length + LabelWidthY + RullerPaddingY][];
        final int origWidth = asciiMatrix.grid()[0].length;
        final int newWidth = origWidth + LabelWidthX + RullerPaddingX;
        final int origHeight = asciiMatrix.grid().length;

        for (int i = 0; i < origHeight; ++i) {
            String[] line = asciiMatrix.grid()[i];
            String[] newLine = new String[newWidth];

            newLine[0] = String.valueOf(i % 10);
            newLine[LabelWidthX] = " ";
            System.arraycopy(line, 0, newLine, LabelWidthX + RullerPaddingX, line.length);

            grid[i] = newLine;
        }

        final String[] emptyLine = new String[newWidth];
        Arrays.fill(emptyLine, " ");
        for (int i = 0; i < RullerPaddingY; ++i) {
            grid[i + origHeight] = emptyLine;
        }

        grid[origHeight + RullerPaddingY] = createRuler(asciiMatrix);


        return new AsciiMatrix(grid);
    };

    public static String[] createRuler(AsciiMatrix asciiMatrix) {
        final int boardWidth = asciiMatrix.grid()[0].length;
        String[] ruler = new String[boardWidth + LabelWidthX + RullerPaddingX];

        ruler[0] = " ";
        for (int i = 0; i < RullerPaddingX; ++i) {
            ruler[i + LabelWidthX] = " ";
        }

        for (int i = 0; i < boardWidth; ++i) {
            ruler[i + LabelWidthX + RullerPaddingX] = String.valueOf(i % 10);
        }

        return ruler;
    }

}

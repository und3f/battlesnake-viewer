package name.zasenko.battlesnake.mapper.stringifier;

import name.zasenko.battlesnake.entities.AsciiMatrix;

public class AsciiMatrixStringifier {

    public String stringify(AsciiMatrix grid) {
        final StringBuilder sb = new StringBuilder();

        // Display in reverse order to convert battlesnake coordinates
        for (int y = grid.grid().length - 1; y >= 0; y--) {
            sb.append(String.join("", grid.grid()[y]));
            sb.append("\n");
        }
        return sb.toString();
    }
}

package Application.Utility.Widgets.Battlepass;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class BattlepassModel {
    private static SVGUniverse images = new SVGUniverse();

    public static SVGIcon getIcon(String iconName) {
        String filePath = "src/main/resources/Icons/" + iconName + ".svg";
        FileInputStream inFS;

        try {
            inFS = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        URI imageURI;
        try {
            imageURI = images.loadSVG(inFS, iconName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SVGIcon output = new SVGIcon();
        output.setSvgUniverse(images);
        output.setSvgURI(imageURI);
        output.setAntiAlias(true);

        return output;
    }
}

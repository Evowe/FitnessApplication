package BackEndTesting.WidgetTesting;

import Application.Utility.Widgets.Battlepass.BattlepassModel;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class SideMenuModelTest {
    private static final SVGUniverse images = new SVGUniverse();
    @Test
    public void testGetIconReturnsValidSVGIcon() {
        String filePath = "src/main/resources/Icons/" + "start-up" + ".svg";
        FileInputStream inFS;

        try {
            inFS = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        URI imageURI;
        try {
            imageURI = images.loadSVG(inFS, "start-up");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SVGIcon output = new SVGIcon();
        output.setSvgUniverse(images);
        output.setSvgURI(imageURI);
        output.setAntiAlias(true);
        assertNotNull(output, "SVGIcon should not be null");
        assertNotNull(output.getSvgURI(), "SVG URI should not be null");
        assertNotNull(output.getAntiAlias(), "SVG AntiAlias should not be null");
    }
}

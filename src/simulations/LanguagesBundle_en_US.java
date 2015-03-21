package simulations;

import java.util.ListResourceBundle;


/**
 * Class defines en_US language resources used in GUI text
 *
 * @author allankiplagat
 *
 */
public class LanguagesBundle_en_US extends ListResourceBundle {

    protected Object[][] contents = {
                                     { "Load simulation", "Load simulation" },
                                     { "Start", "Start" },
                                     { "Pause", "Pause" },
                                     { "Resume", "Resume" },
                                     { "Step forward", "Step forward" },
                                     { "Speed up", "Speed up" },
                                     { "Slow down", "Slow down" },
                                     { "Status", "Status" }
    };

    @Override
    protected Object[][] getContents () {
        return contents;
    }
}

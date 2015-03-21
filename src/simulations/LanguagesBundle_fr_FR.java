package simulations;

import java.util.ListResourceBundle;


/**
 * Class defines fr_FR language resources used in GUI text
 *
 * @author allankiplagat
 *
 */
public class LanguagesBundle_fr_FR extends ListResourceBundle {

    protected Object[][] contents = {
                                     { "Load simulation", "Simulation de la charge" },
                                     { "Start", "Début" },
                                     { "Pause", "Pause" },
                                     { "Resume", "Continuer" },
                                     { "Step forward", "S'avancer" },
                                     { "Speed up", "Accélérer" },
                                     { "Slow down", "Ralentissez" },
                                     { "Status", "Statut" }
    };

    @Override
    protected Object[][] getContents () {
        return contents;
    }

}

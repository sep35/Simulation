package simulations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Class used to read in XML files
 *
 * @author stevenpierre
 *
 */
public class XMLFileReader {
    protected String simulation, cellType, patchType, parameters;
    protected int[] dimension = new int[2];
    protected List<String> cellinfo = new ArrayList<String>(), patchinfo = new ArrayList<String>();

    public static final String FIELD_SEPARATOR = "/";
    
    protected void readFile (Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML File");
        File xml = fileChooser.showOpenDialog(stage);
        // File xml = new File("src/XML/Segregation.XML");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder Parser = factory.newDocumentBuilder();
            Document doc = Parser.parse(xml);
            doc.getDocumentElement().normalize();

            simulation = doc.getDocumentElement().getNodeName();
            cellType = doc.getDocumentElement().getAttribute("CellType");
            patchType = doc.getDocumentElement().getAttribute("PatchType");
            parameters = doc.getDocumentElement().getAttribute("Parameters");

            try {
                dimension[0] = Integer.parseInt(doc.getDocumentElement().getAttribute("xmax"));
                dimension[1] = Integer.parseInt(doc.getDocumentElement().getAttribute("ymax"));
            }
            catch (Exception e) {
                dimension[0] = 15;
                dimension[1] = 15;
                System.out.println("No dimensions given, set to default");
            }

            cellinfo.clear();
            patchinfo.clear();

            if (doc.getDocumentElement().getAttribute("Generate").equals("Random")) {
                randomGrid();
            }
            else {
                readGrid(doc.getElementsByTagName("Gridc"), cellinfo, true);
                readGrid(doc.getElementsByTagName("Grid"), patchinfo, false);
            }

        }
        catch (Exception e) {
            System.out.println("Incorrect file format");
        }
    }

    private void readGrid (NodeList nList, List<String> info, boolean spec) {
        int ylocation = 0;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Element row = (Element) nList.item(temp);
            String[] states = ("" + row.getTextContent()).split(",");
            if (states.length <= dimension[0] && nList.getLength() <= dimension[1]) {
                for (int i = 0; i < states.length; i++) {
                    if ((states[i].equals("0") || states[i].equals("1") || states[i].equals("2")) &&
                        spec) {
                        info.add(i + FIELD_SEPARATOR + ylocation + FIELD_SEPARATOR + states[i] + FIELD_SEPARATOR + parameters);
                    }
                    else if (!spec) {
                        info.add(i + FIELD_SEPARATOR + ylocation + FIELD_SEPARATOR + states[i] + FIELD_SEPARATOR + parameters);
                    }
                }
            }
            else {
                System.out.println("Cell location outside of grid size");
            }
            ylocation++;
        }
    }

    private void randomGrid () {
        Random r = new Random();
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                int value = r.nextInt(3);
                cellinfo.add(i + FIELD_SEPARATOR + j + FIELD_SEPARATOR + value + FIELD_SEPARATOR + parameters);
                if (value > 1 && !simulation.equals("Fire")) {
                    patchinfo.add(i + FIELD_SEPARATOR + j + FIELD_SEPARATOR + 0 + FIELD_SEPARATOR + parameters);
                }
                if (value > 2) {
                    patchinfo.add(i + FIELD_SEPARATOR + j + FIELD_SEPARATOR + 0 + FIELD_SEPARATOR + parameters);
                }
                if (value <= 1) {
                    patchinfo.add(i + FIELD_SEPARATOR + j + FIELD_SEPARATOR + 1 + FIELD_SEPARATOR + parameters);
                }
                if (value <= 2 && simulation.equals("Fire")) {
                    patchinfo.add(i + FIELD_SEPARATOR + j + FIELD_SEPARATOR + 1 + FIELD_SEPARATOR + parameters);
                }
            }
        }
    }
}

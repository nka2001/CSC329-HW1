package edu.farmingdale.m01circulardeque;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
// Classgraph, for reflection (finding all test programs)
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import javafx.scene.text.Font;
/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Font displayFont = javafx.scene.text.Font.font("Courier", 12);
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        final String FAILED_TEXT = "Test Failed At Location ";
        // two parallel panes. Left will be the buttons, right the results.
        TilePane testTiles = new TilePane(Orientation.VERTICAL);
        testTiles.setVgap(1);
        TilePane resultTiles = new TilePane(Orientation.VERTICAL);
        resultTiles.setVgap(1);

        //     resultTiles.setPrefColums(1);
        testTiles.setMinWidth(testTiles.USE_PREF_SIZE);

        testTiles.setMaxWidth(Double.MAX_VALUE);
        resultTiles.setMinWidth(testTiles.USE_PREF_SIZE);

        resultTiles.setMaxWidth(Double.MAX_VALUE);
        testTiles.setMaxHeight(Double.MAX_VALUE);
        resultTiles.setMaxHeight(Double.MAX_VALUE);
        // Use ClassGraph to find all classes extending RunTest and instanciate them here (instead of having to list them)
        ArrayList<RunTest> listOfTests = getAllTests();
        // set our array size
        resultTiles.setPrefColumns(1);
        testTiles.setPrefColumns(1);
        resultTiles.setPrefRows(listOfTests.size() + 1);
        testTiles.setPrefRows(listOfTests.size() + 1);

        // Now make a button for each test, along with an indicator
        for (RunTest theTest : listOfTests) {
            String s = theTest.getTestName();
            Button b = new Button(s);
            b.setFont(displayFont);
            //          b.setStyle("-fx-font-family: Courier; -fx-font-size: 12pt");
            Button resultButton = new Button("Not yet run");
            // set the button to run the test and get the result
            b.setOnAction(e -> { // lambda
                System.out.println("'" + s + "' Button Pressed");
                b.setDisable(true);
                String result = theTest.runTest();
                if (result == "") {
                    resultButton.setText("Test Passed");
                    resultButton.setStyle("-fx-background-color: LightSeaGreen; -fx-text-fill: White");
                    resultButton.setFont(displayFont);
                } else {
                    resultButton.setText(FAILED_TEXT + result);
                    resultButton.setStyle("-fx-background-color: LightSalmon; -fx-text-fill: Chocolate");
                    resultButton.setFont(displayFont);
                }
            });

            b.setMinWidth(Button.USE_PREF_SIZE);
            b.setMaxWidth(Double.MAX_VALUE);
            testTiles.getChildren().add(b);
            // This is really an indicator, not a button
            resultButton.setDisable(true);
            // but javafx greys it out, so we undo that
            resultButton.setOpacity(1.0);
            resultButton.setMinWidth(Button.USE_PREF_SIZE);
            resultButton.setMaxWidth(Double.MAX_VALUE);
            // for now, tests are not done, so color yellow
            resultButton.setStyle("-fx-background-color: Yellow");
            resultButton.setFont(displayFont);
            resultTiles.getChildren().add(resultButton);
        }
        Button quitButton = new Button("Quit");
        quitButton.setFont(displayFont);
        quitButton.setOnAction(e -> {
            System.exit(0);
        });
        quitButton.setMinWidth(Button.USE_PREF_SIZE);
        quitButton.setMaxWidth(Double.MAX_VALUE);
        testTiles.getChildren().add(quitButton);

        // now, to get the right hand side the same width no matter the message, we add an invisible button of the correct width
        Button qr = new Button(FAILED_TEXT + "E9,999");
        qr.setFont(displayFont);
        qr.setOpacity(0.0);
        resultTiles.getChildren().add(qr);

        HBox hbox = new HBox();
        hbox.getChildren().add(testTiles);

        hbox.getChildren().add(resultTiles);
        VBox vbox = new VBox();
        var title = new Label("This is the Test Infrastructure, running on Java " + javaVersion + " with JavaFX " + javafxVersion + ".");
        title.setFont(displayFont);
        vbox.getChildren().add(title);
        vbox.getChildren().add(hbox);

        // now put the file hashes in the vbox
        // we do App.java
        ArrayList<String> files = new ArrayList<>();
        files.add("App.java");
        // and every class file that has the word "Test" in it
        // I could also use ClassGraph to find all class that implement RunTest
        // (a more elegent solution I'll add next year)
        for (RunTest theTest : listOfTests) {
            if (theTest.getClass().getSimpleName().contains("Test")) {
                String testClassName = theTest.getClass().getSimpleName() + ".java";
                files.add(testClassName);
            }
        }
        for (String s : files) {
            Label l = new Label(s + " : Hash is " + hashAFile(s));
            l.setFont(displayFont);
            vbox.getChildren().add(l);
        }
        // var borderWidths = new BorderWidths(10,10,10,10, true, true, true, true);
        // vbox.setBorder(new Bord`er(new BorderStroke()));
        String vboxBorderLayout = "-fx-border-width: 4; -fx-border-insets: 10; -fx-border-color: blue; -fx-border-style: solid;";
        vbox.setStyle(vboxBorderLayout);
        // Height is dynamic based on # of tests
        var scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Given a filename string, returns the string of a SHA-256 hash of the
     * contents of the file (under the assumption that the file is in the source
     * directory of the project)
     *
     * @param fileName simple String name of the file
     * @return A string of the file hash (SHA-256 as configured)
     */
    // returns SHA-256 of a the file, in either root or ActualTestClasses directory
    String hashAFile(String fileName) {
        String project_name = "M01CircularDeque";
        try {
            var basePath = (new File((App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath())).toPath().getParent().getParent();

            var totalPath = basePath.resolve("src").resolve("main").resolve("java").resolve("edu").resolve("farmingdale").resolve(project_name).resolve(fileName);
            System.out.println("The path of the file we're examining is " + totalPath);

            byte[] data = Files.readAllBytes(totalPath);
            MessageDigest md = MessageDigest.getInstance("SHA-256");//"MD5");
            md.update(data);
            byte[] processedData = md.digest();
            String result = "";
            for (int i = 0; i < processedData.length; ++i) {
                result += Integer.toString((processedData[i] & 0xff) + 0x100, 16).substring(1);
            }
            return result;

        } catch (Exception e) {
            // NoSuchAlgorithmException
            // IOException
            System.err.println("Exception : " + e.getClass().getSimpleName());
        }
        return "NONE--Error";

    }

    /**
     * Uses io.github.ClassGraph to examine all classes in the app
     *
     * @return Returns an ArrayList of all classes implementing RunTest
     */
    // R
    ArrayList<RunTest> getAllTests() {
        // Uses ClassGraph to find all classes implementing RunTest
        var rv = new ArrayList<RunTest>();
        try ( ScanResult scanResult
                = new ClassGraph().enableAllInfo().scan()) { // add .verbose() after new ClassGraph() to get console logging
            ClassInfoList classInfoList = scanResult.getClassesImplementing(RunTest.class.getName());
            for (ClassInfo classInfo : classInfoList) {
                System.out.println("About to instantiate " + classInfo.getName());
                // now use ClassInfo.loadClass to add the class to rv
                var theClass = classInfo.loadClass();
                try {
                    var theConstructor = theClass.getConstructor();
                    Object theInstance = theConstructor.newInstance();
                    if (theInstance instanceof RunTest) { // Swift has better syntax for this
                        rv.add((RunTest) theInstance);
                    }
                } catch (Exception e) {
                    System.err.println("Exception while creating test object " + e);
                }
            }
        }
        return rv;
    }

    public static void main(String[] args) {
        launch();
    }
}

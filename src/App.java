import java.io.File;

/**
 * Main application for the Data Analysis Mini‑Project.
 *
 * TODO:
 *  - Update the path to your dataset file
 *  - Read the CSV file using Scanner
 *  - Parse each row and extract the correct columns
 *  - Construct Data objects from each row
 *  - Store them in an array
 *  - Write methods to analyze the dataset (min, max, average, filters, etc.)
 *  - Print insights and answer your guiding question
 *  - Add Javadoc comments for any methods you create
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application for the Data Analysis Mini‑Project.
 * <p>
 * Loads the CDC state dataset and performs simple numeric analysis.  The
 * guiding question for this run is: how does drug overdose relate to firearm
 * deaths?
 */
public class App {

    public static void main(String[] args) {
        File file = new File("StateData2020-CDC-Census.csv");
        if (!file.exists()) {
            File alt = new File("src/StateData2020-CDC-Census.csv");
            if (alt.exists()) {
                file = alt;
            }
        }

        List<Data> dataList = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextLine()) { 
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] cols = line.split(",");
                Data d = Data.fromCsvColumns(cols);
                dataList.add(d);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Dataset file not found: tried " + file.getAbsolutePath());
            return;
        }

        System.out.printf("Loaded %d rows\n", dataList.size());

        if (dataList.isEmpty()) {
            System.out.println("No data to analyze");
            return;
        }


        double totalDrugRate = 0;
        double totalFirearmRate = 0;
        int count = 0;
        Data maxDrug = null;
        Data maxFirearm = null;

        for (Data d : dataList) {
            if (d.getDrugOverdoseRate() >= 0) {
                totalDrugRate += d.getDrugOverdoseRate();
            }
            if (d.getFirearmDeathRate() >= 0) {
                totalFirearmRate += d.getFirearmDeathRate();
            }
            if (maxDrug == null || d.getDrugOverdoseRate() > maxDrug.getDrugOverdoseRate()) {
                maxDrug = d;
            }
            if (maxFirearm == null || d.getFirearmDeathRate() > maxFirearm.getFirearmDeathRate()) {
                maxFirearm = d;
            }
            count++;
        }

        double avgDrug = totalDrugRate / count;
        double avgFirearm = totalFirearmRate / count;

        System.out.printf("Average drug overdose rate: %.2f per 100k\n", avgDrug);
        System.out.printf("Average firearm death rate: %.2f per 100k\n", avgFirearm);
        System.out.println("State with highest drug rate: " + (maxDrug != null ? maxDrug.getState() + " (" + maxDrug.getDrugOverdoseRate() + ")" : "n/a"));
        System.out.println("State with highest firearm rate: " + (maxFirearm != null ? maxFirearm.getState() + " (" + maxFirearm.getFirearmDeathRate() + ")" : "n/a"));


        System.out.println("\nDrug vs. Firearm rates by state:");
        for (Data d : dataList) {
            System.out.printf("%s: drug=%.1f, firearm=%.1f\n", d.getState(), d.getDrugOverdoseRate(), d.getFirearmDeathRate());
        }

        System.out.println("\nGuiding answer: there is not a straightforward linear relationship in this 2016 data; some states with high drug overdose rates also have high firearm death rates (e.g., "
                + (maxDrug != null ? maxDrug.getState() : "?") + "), but others diverge.");
    }
}

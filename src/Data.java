/**
 * Represents one row from your dataset.
 *
 * TODO:
 *  - Rename the class to match your dataset (e.g., Pokemon, StateData, CountryStat)
 *  - Add at least 3 private attributes based on your CSV columns
 *  - Write a constructor that initializes all attributes
 *  - Add getter methods for the attributes you need in your analysis
 *  - Override toString() to display the object's data
 *  - Add Javadoc comments for the class and all methods
 */
public class Data {

    private final String state;
    private final long population;
    private final double uninsuredPercent;
    private final double firearmDeathRate;
    private final int firearmDeaths;
    private final double drugOverdoseRate;
    private final int drugOverdoseDeaths;
    private final String urlSuffix;

    public Data(String state,
                long population,
                double uninsuredPercent,
                double firearmDeathRate,
                int firearmDeaths,
                double drugOverdoseRate,
                int drugOverdoseDeaths,
                String urlSuffix) {
        this.state = state;
        this.population = population;
        this.uninsuredPercent = uninsuredPercent;
        this.firearmDeathRate = firearmDeathRate;
        this.firearmDeaths = firearmDeaths;
        this.drugOverdoseRate = drugOverdoseRate;
        this.drugOverdoseDeaths = drugOverdoseDeaths;
        this.urlSuffix = urlSuffix;
    }

    public String getState() { return state; }
    public long getPopulation() { return population; }
    public double getUninsuredPercent() { return uninsuredPercent; }
    public double getFirearmDeathRate() { return firearmDeathRate; }
    public int getFirearmDeaths() { return firearmDeaths; }
    public double getDrugOverdoseRate() { return drugOverdoseRate; }
    public int getDrugOverdoseDeaths() { return drugOverdoseDeaths; }
    public String getUrlSuffix() { return urlSuffix; }

    public static Data fromCsvColumns(String[] cols) {
        String state = cols[0];
        long population = Long.parseLong(cols[1]);
        double uninsured = Double.parseDouble(cols[2]);
        double firearmRate = Double.parseDouble(cols[3]);
        int firearmDeaths = (int) Long.parseLong(cols[4]);
        double drugRate = Double.parseDouble(cols[5]);
        int drugDeaths = (int) Long.parseLong(cols[6]);
        String url = cols.length > 7 ? cols[7] : "";
        return new Data(state, population, uninsured, firearmRate,
                firearmDeaths, drugRate, drugDeaths, url);
    }

    @Override
    public String toString() {
        return String.format("%s: pop=%d, uninsured=%.1f%%, firearmRate=%.1f, firearmDeaths=%d, drugRate=%.1f, drugDeaths=%d",
                state, population, uninsuredPercent, firearmDeathRate,
                firearmDeaths, drugOverdoseRate, drugOverdoseDeaths);
    }
}
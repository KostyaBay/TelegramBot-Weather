
public class Model {

    private String name;
    private Double temp;
    private Double humidity;
    private Double pressure;
    private Double wind;
    private String icon;
    private String main;

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public Double getPressure() {
        pressure = pressure / 1.333;
        return Double.valueOf(Math.round(pressure));
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String getName() {
        return name;
    }

    // set - for pooling the value
    public void setName(String name) {
        this.name = name;
    }

    // get - for getting the value
    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}

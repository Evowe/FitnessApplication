package Application.Utility.Objects;

public class CartItem {
    private int rocketBucks;
    private double price;
    private String description;

    public CartItem(int rocketBucks, double price) {
        this.rocketBucks = rocketBucks;
        this.price = price;
        this.description = rocketBucks + " Rocket Bucks $" + price;
    }

    public int getRocketBucks() {
        return rocketBucks;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}

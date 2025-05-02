package Application.Utility.Objects;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }

    public int getTotalRocketBucks() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getRocketBucks();
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice();
        }
        return total;
    }
}

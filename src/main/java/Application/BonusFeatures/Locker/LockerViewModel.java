package Application.BonusFeatures.Locker;

import java.util.List;

public class LockerViewModel {
    private final LockerModel model;

    public LockerViewModel(String username) {
        this.model = new LockerModel(username);
    }

    public void refreshData() {
        model.refresh();
    }

    public List<LockerModel.Item> getCatalogItems() {
        return model.getCatalogItems();
    }

    public List<LockerModel.Item> getOwnedItems() {
        return model.getOwnedItems();
    }

    public List<LockerModel.Item> getOwnedItemsByType(String type) {
        return model.getOwnedItemsByType(type);
    }

    public List<LockerModel.Item> getEquippedItems() {
        return model.getEquippedItems();
    }

    public List<LockerModel.Item> getEquippedItemsByType(String type) {
        return model.getEquippedItemsByType(type);
    }

    public boolean purchaseItem(int itemId) {
        boolean success = model.purchaseItem(itemId);
        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean equipItem(int itemId) {
        boolean success = model.equipItem(itemId);
        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean unequipItem(int itemId) {
        boolean success = model.unequipItem(itemId);
        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean hasItem(int itemId) {
        return model.hasItem(itemId);
    }

    public boolean hasEquipped(int itemId) {
        return model.hasEquipped(itemId);
    }
}
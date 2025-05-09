package Application.BonusFeatures.BattlePass;

import Application.Databases.AccountsDB;
import Application.Databases.BattlePassDB;
import Application.Databases.DatabaseManager;
import Application.Databases.ItemsDB;
import Application.Main;

import java.sql.*;
import java.util.*;


public class BattlePassModel {

    private final List<TierData> tiers = new ArrayList<>();
    private final Map<Integer, String> rewardMapping = new HashMap<>();
    private String username;

    public BattlePassModel() {
        loadTiersFromDatabase();
        assignXpToTiers();
        setupRewardMapping();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TierData> getTiers() {
        return tiers;
    }

    public void unlockTiersBasedOnXP(int playerXP) {
        for (TierData tier : tiers) {
            if (playerXP >= tier.requiredXP && tier.isUnlocked == 0) {
                tier.isUnlocked = 1;
                updateTierUnlockInDatabase(tier.tierNumber);
                unlockItemForTier(tier.tierNumber);
            }
        }
    }

    private void assignXpToTiers() {
        int xp = 100;
        int tierCounter = 1;
        for (TierData tier : tiers) {
            tier.requiredXP = xp * tierCounter;
            tierCounter += 4;
        }
    }

    private void loadTiersFromDatabase() {
        try (Connection conn = BattlePassDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM BattlePass ORDER BY TierNumber ASC")) {

            while (rs.next()) {
                TierData data = new TierData(
                        rs.getInt("TierNumber"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("IsUnlocked"),
                        rs.getString("ImagePath")
                );
                tiers.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTierUnlockInDatabase(int tierNumber) {
        try (Connection conn = BattlePassDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE BattlePass SET IsUnlocked = 1 WHERE TierNumber = ?")) {
            stmt.setInt(1, tierNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupRewardMapping() {
        rewardMapping.put(1, "Blue Rocket");
        rewardMapping.put(2, "Pleb");
        rewardMapping.put(3, "11");
        rewardMapping.put(4, "Wimp");
        rewardMapping.put(5, "28");
        rewardMapping.put(6, "Weakling");
        rewardMapping.put(7, "Blue Rocket");
        rewardMapping.put(8, "-4");
        rewardMapping.put(9, "Fledgling");
        rewardMapping.put(10, "Cow Rocket");
        rewardMapping.put(12, "Chubby");
        rewardMapping.put(13, "-7");
        rewardMapping.put(14, "Soggy Noodle");
        rewardMapping.put(15, "17");
        rewardMapping.put(16, "Cheat Day Pro");
        rewardMapping.put(17, "5");
        rewardMapping.put(18, "Error 404");
        rewardMapping.put(20, "Disco Rocket");
        rewardMapping.put(21, "Skips Leg Day");
        rewardMapping.put(22, "15");
        rewardMapping.put(24, "Outrageously Mediocre");
        rewardMapping.put(25, "-32");
        rewardMapping.put(26, "Not Embarrassing");
        rewardMapping.put(27, "31");
        rewardMapping.put(28, "Trying Your Best");
        rewardMapping.put(29, "18");
        rewardMapping.put(30, "Shrek Rocket");
        rewardMapping.put(31, "9");
        rewardMapping.put(32, "Looks Maxxer");
        rewardMapping.put(34, "1");
        rewardMapping.put(35, "Protein Addict");
        rewardMapping.put(36, "28");
        rewardMapping.put(37, "SpeedRunner");
        rewardMapping.put(38, "14");
        rewardMapping.put(40, "Kirket");
        rewardMapping.put(42, "12");
        rewardMapping.put(43, "Big (In a good way)");
        rewardMapping.put(44, "100");
        rewardMapping.put(45, "Maximus");
        rewardMapping.put(47, "Ascended Lifter");
        rewardMapping.put(48, "104");
        rewardMapping.put(49, "SWOLE");
        rewardMapping.put(50, "SWOLEKET");
    }

    public boolean unlockItemForTier(int tier) {
        String itemName = rewardMapping.get(tier);
        if (itemName == null) {
            System.out.println("No item mapped for tier " + tier);
            return false;
        }
        if (username == null) {
            System.out.println("Username is null; cannot unlock item for tier " + tier);
            return false;
        }

        try {
            ItemsDB itemsDB = DatabaseManager.getItemsDB();
            int itemId = itemsDB.getItemIdByName(itemName);
            if (itemId == -1) {
                if ( itemName.matches("-?\\d+" ) ){
                    Integer rb = Integer.parseInt(itemName);
                    AccountsDB accountsDB = DatabaseManager.getAccountsDB();
                    String username = Main.getCurrentUser().getUsername();
                    int currentBalance = accountsDB.getWallet(username);

                    int newBalance = currentBalance + rb;
                    accountsDB.updateWallet(username, newBalance);
                }
                return false;
            }

            if (!itemsDB.userHasItem(username, itemId)) {
                System.out.println("User does not have item. Granting item " + itemId);
                itemsDB.giveItemToUser(username, itemId);
                return true;
            } else {
                System.out.println("User already has item " + itemId);
            }
        } catch (SQLException e) {
            System.err.println("Error unlocking item for tier " + tier + ": " + e.getMessage());
        }

        return false;
    }


    public static class TierData {
        public int tierNumber;
        public String title;
        public String description;
        public int isUnlocked;
        public String imagePath;
        public int requiredXP;

        public TierData(int tierNumber, String title, String description, int isUnlocked, String imagePath) {
            this.tierNumber = tierNumber;
            this.title = title;
            this.description = description;
            this.isUnlocked = isUnlocked;
            this.imagePath = imagePath;
        }
    }
}

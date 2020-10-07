package com.codecool;

import com.codecool.model.Coin;
import com.codecool.model.Product;
import com.codecool.model.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Machine {
    private final ArrayList<Coin> customerCoins;
    private final ArrayList<Coin> returnedCoins;
    private final HashMap<Value, Integer> machineCoins;
    private final HashMap<Product, Integer> machineStock;
    private String currentDisplay;

    public Machine(HashMap<Value, Integer> machineCoins, HashMap<Product, Integer> machineStock) {
        this.customerCoins = new ArrayList<>();
        this.returnedCoins = new ArrayList<>();
        this.machineCoins = machineCoins;
        this.machineStock = machineStock;
        if (machineCoins.get(Value.NICKEL) >= 1){ //with one nickel inside you can buy cola and chips for sure and probably candy
            currentDisplay = "INSERT COIN";
        }else{
            currentDisplay = "EXACT CHANGE ONLY";
        }
    }

    public void insertCoin(Coin coin) {
        if (coin.getValue() != Value.NOT_WORTHY) {
            customerCoins.add(coin);
            currentDisplay = "Current amount: ";
            currentDisplay += getCoinsSum(customerCoins);
            currentDisplay += "$";
        } else {
            returnedCoins.add(coin);
            currentDisplay = "INSERT COIN";
        }
    }

    public Product selectProduct(Product product) {
        if (machineStock.get(product) >= 1){
            if (getCoinsSum(customerCoins) == product.getPrice()) {
                addCoinsToMachine();
                currentDisplay = "THANK YOU";
                return product;
            } else if (getCoinsSum(customerCoins) > product.getPrice()) {
                if (checkIfChangePossibleAndDispenseIt(product)) {
                    addCoinsToMachine();
                    currentDisplay = "THANK YOU";
                    customerCoins.clear();
                    return product;
                }else{
                    currentDisplay = "CANNOT DISPENSE CHANGE";
                    returnButton();
                    return Product.NOTHING;
                }
            }
            currentDisplay = "INSERT COIN";
        }else {
            currentDisplay = "SOLD OUT";
        }
        return Product.NOTHING;
    }

    public void returnButton() {
        returnedCoins.addAll(customerCoins);
        customerCoins.clear();
    }

    public String showDisplay() {
        return currentDisplay;
    }

    public String showReturn() {
        StringBuilder coins = new StringBuilder();
        for (Coin coin : returnedCoins) {
            coins.append(coin.getValue().getValue()).append(",");
        }
        return coins.toString();
    }

    private boolean checkIfChangePossibleAndDispenseIt(Product product) {
        float change = getCoinsSum(customerCoins) - product.getPrice();
        int quarters = (int) (change / 0.25);
        float quartersRest = change % 0.25F;
        int dimes = (int) (quartersRest / 0.1);
        float dimesRest = dimes % 0.1F;
        int nickels = (int) (dimesRest / 0.05);
        if (machineCoins.get(Value.QUARTER) >= quarters &&
                machineCoins.get(Value.DIME) >= dimes && machineCoins.get(Value.NICKEL) >= nickels) {
            addCoinToReturn(quarters, Value.QUARTER);
            addCoinToReturn(dimes, Value.DIME);
            addCoinToReturn(nickels, Value.NICKEL);
            return true;
        }
        return false;
    }

    private void addCoinToReturn(int amount, Value value) {
        for (int i = 0; i < amount; i++) {
            returnedCoins.add(new Coin(value));
        }
    }

    private void addCoinsToMachine() {
        for (Coin coin : customerCoins) {
            int amount = machineCoins.get(coin.getValue());
            machineCoins.put(coin.getValue(), ++amount);
        }
    }

    private float getCoinsSum(ArrayList<Coin> coins) {
        float sum = 0;
        for (Coin coin : coins) {
            sum += coin.getValue().getValue();
        }
//        return new BigDecimal(sum).round(new MathContext(2)).doubleValue();
        return sum;
    }
}

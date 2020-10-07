package com.codecool;

import static org.junit.Assert.assertEquals;

import com.codecool.model.Coin;
import com.codecool.model.Product;
import com.codecool.model.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

public class AcceptCoin
{
    private final HashMap<Value, Integer> machineCoins = new HashMap<>();
    private final HashMap<Product, Integer> machineStock = new HashMap<>();
    private Machine machine;

    @BeforeEach
    public void prepareMachine() {
        fillMaps();
        machine = new Machine(machineCoins, machineStock);
    }

    @Test
    public void shouldAcceptCoins()
    {
        machine.insertCoin(new Coin(Value.NICKEL));
        machine.insertCoin(new Coin(Value.DIME));
        assertEquals("Current amount: 0.15$", machine.showDisplay());
    }

    @Test
    public void shouldNotAcceptCoins() {
        machine.insertCoin(new Coin(Value.NOT_WORTHY));
        System.out.println();
        assertEquals("0.0,", machine.showReturn());
    }

    private void fillMaps(){
        machineCoins.put(Value.NICKEL, 5);
        machineCoins.put(Value.DIME, 10);
        machineCoins.put(Value.QUARTER, 10);
        machineStock.put(Product.COLA, 4);
        machineStock.put(Product.CHIPS, 8);
        machineStock.put(Product.CANDY, 10);
    }
}

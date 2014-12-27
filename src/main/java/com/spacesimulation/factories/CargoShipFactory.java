package com.spacesimulation.factories;

import com.spacesimulation.domain.CargoShip;
import com.spacesimulation.exceptions.ColorNotFoundException;
import com.spacesimulation.exceptions.InvalidDoubleException;
import com.spacesimulation.exceptions.InvalidIntegerException;
import com.spacesimulation.exceptions.NullObjectException;
/**
 * Single point of creation for CargoShip objects.
 * @author Steven Muschler
 */
public class CargoShipFactory {
    /**
     * Calls the CargoShip constructor.
     * @param c The side/color the CargoShip is to be on.
     * @param ang The angle of the CargoShip.
     * @param sp The speed of the CargoShip.
     * @param mStrength The maximum strength of the CargoShip.
     * @param clouds The number of DebrisClouds the CargoShip is initiated with.
     * @return The new CargoShip object.
     * @throws InvalidDoubleException thrown if sp or mStrength < 0.
     * @throws InvalidIntegerException thrown if clouds < 0.
     * @throws ColorNotFoundException thrown if c does not exist.
     * @throws NullObjectException thrown if c is null.
     */
    public static CargoShip build(String c, double ang, double sp, double mStrength, int clouds) throws InvalidDoubleException, InvalidIntegerException, ColorNotFoundException, NullObjectException
    {
        return new CargoShip(c, ang, sp, mStrength, clouds);
    }
}

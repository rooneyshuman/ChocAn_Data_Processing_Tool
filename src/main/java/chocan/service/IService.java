package chocan.service;

import chocan.ui.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * A read-only interface for a data object that stores service information.
 */
public interface IService extends Cloneable {

    /**
     * The minimum (6-digit) service code allowed.
     */
    int MIN_CODE = 100000;

    /**
     * The maximum (6-digit) service code allowed.
     */
    int MAX_CODE = 999999;

    /**
     * Gets the code of the service.
     */
    int getCode();

    /**
     * Gets the name of the service.
     */
    String getName();

    /**
     * Gets the fee of the service.
     */
    BigDecimal getFee();

    /**
     * Displays the service information to the standard output.
     */
    default void display() {
        System.out.println("Code: " + this.getCode());
        System.out.println("Name: " + this.getName());
        System.out.println("Fee: " + NumberFormat.getCurrencyInstance().format(this.getFee()));
    }

    /**
     * Gets the header string for a table of services.
     */
    default String displayHeader() {
        return StringUtils.padRight("Code", 6) +
            " | " + StringUtils.padRight("Name", 25) +
            " | " + StringUtils.padRight("Fee", 9);
    }

    /**
     * Gets a row string for this service in a table of services.
     */
    default String displayRow() {
        return StringUtils.padRight(String.valueOf(this.getCode()), 6) +
            " | " + StringUtils.padRight(this.getName(), 25) +
            " | " + StringUtils.padRight(NumberFormat.getCurrencyInstance().format(this.getFee()), 9);
    }

    /**
     * Creates a copy of the service object.
     */
    IService clone();

}

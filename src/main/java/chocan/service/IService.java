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
     * The exact number of characters of the code field.
     */
    int CODE_LENGTH = 6;

    /**
     * Gets the 6-digit code of the service.
     */
    int getCode();
    
    /**
     * The maximum number of characters of the name field.
     */
    int MAX_NAME_LENGTH = 25;
    
    /**
     * Gets the name of the service. (25 character max)
     */
    String getName();
    
    /**
     * The maximum value of the fee field.
     */
    BigDecimal MAX_FEE = new BigDecimal("999.99");
    
    /**
     * Gets the fee of the service. (9 character max)
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
        return StringUtils.padRight("Code", CODE_LENGTH) +
            " | " + StringUtils.padRight("Name", MAX_NAME_LENGTH) +
            " | " + StringUtils.padRight("Fee", 9);
    }

    /**
     * Gets a row string for this service in a table of services.
     */
    default String displayRow() {
        return StringUtils.padRight(String.valueOf(this.getCode()), CODE_LENGTH) +
            " | " + StringUtils.padRight(this.getName(), MAX_NAME_LENGTH) +
            " | " + StringUtils.padRight(NumberFormat.getCurrencyInstance().format(this.getFee()), 9);
    }

    /**
     * Creates a copy of the service object.
     */
    IService clone();

}

package EasyIO; /**
 * <p>An iterator class to input specific data types from an input stream that is reading lines of text,
 * without throwing any exceptions that require try/catch blocks. This is just a simple wrapper around
 * <code>Scanner</code> that implements a subset of the <code>Scanner</code> public interface.
 * If invalid input is found during use  * of the class then a message is output to the standard error
 * and methods will return a default value, leaving the state of the program using this class
 * potentially undefined.
 * </p>
 * <p> If the input stream is closed and an attempt is made to read from it then by default an error message
 * is output to standard error. As an option there is a flag that can be set so that an unchecked exception
 * is thrown instead, causing the program using the class to terminate unless the exception is caught. The
 * use of an uncaught exception does not require the program using the class to include code to
 * catch the exception.
 * </p>
 * <p> EasyIO.Input is buffered meaning that an entire line of text is read into a buffer and then an attempt is
 * made to read a value using the characters in the buffer. Reading a value only consumes as many characters
 * from the buffer as needed, leaving any characters left over still in the buffer. This means that the next attempt
 * to read input starts with the remaining content in the buffer. If all the characters are read from the buffer it
 * is refilled with the next line of text available at the next attempt to read.
 * </p>
 * <p>This class is useful for those new to Java since it allows them to write programs using
 * input without having to fully understand the try/catch exceptions handling model of the
 * standard Java classes and in particular <code>Scanner</code>. Once exceptions and object chaining
 * are covered this class ought not to be used, it is definitely just an "early stepping stone"
 * utility class for initial learning.</p>
 */

import java.io.Closeable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input implements Closeable, Iterator<String> {
    /**
     * A reference to the wrapped <code>Scanner</code> that supplies all the actual input
     * functionality.
     * <p/>
     * <p>This is protected and not final rather than private and final (as we might have
     * expected it to be) so that <code>EasyIO.FileInput</code> can access the variable.  This is
     * necessary because <code>EasyIO.FileInput</code> needs to manage what happens when opening
     * a file.</p>
     */
    protected Scanner scanner;

    /**
     * The state of the input stream.
     */
    protected boolean closed = false;

    // Flag to determine whether to throw an exception if the input is closed when an attempt
    // to read is made.
    private boolean throwIllegalStateException = false;

    /**
     * The default constructor of an <code>EasyIO.Input</code> that assumes <code>System.in</code> is to
     * be the <code>InputStream</code> used.
     */
    public Input() {
        this(System.in);
    }

    /**
     * Constructor of an <code>EasyIO.Input</code> object given an <code>InputStream</code> object.
     */
    public Input(final InputStream in) {
        this.scanner = new Scanner(in);
    }

    /**
     * Constructor of an <code>EasyIO.Input</code> object given a <code>Readable</code> object.
     */
    public Input(final Readable in) {
        this.scanner = new Scanner(in);
    }

    /**
     * Constructor of an <code>EasyIO.Input</code> object given a <code>Scanner</code> object.
     * This allows a different <code>Scanner</code> to be used for the input, primarily to enable testing
     * of the class.
     */
    public Input(final Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Close the scanner when finished with it. Use with caution.
     * Calling close also closes the underlying <code>InputStream</code> or <code>Readable</code>
     * that is being read from. If reading from System.in (usually connected to the keyboard
     * unless I/O redirection is being used) then System.in will be closed. This will prevent any further
     * input from System.in, hence close should not normally be called if System.in is being used.
     * If the input is already closed then an error is reported and no attempt is made to close the
     * input a second time.
     */
    @Override
    public synchronized void close() {
        if (!closed) {
            scanner.close();
            closed = true;
        } else {
            System.err.println("Warning: Attempted to close an already closed input.");
        }
    }

    /**
     * @return <code>true</code> if there is more input in the buffer, <code>false</code> otherwise.
     */
    @Override
    public synchronized boolean hasNext() {
        return handleInput(scanner::hasNext, false, "Any type");
    }

    /**
     * @return the next token (sequence of characters terminated by a whitespace) in the input
     * stream. Or return an empty string by default if there is no valid input.
     */
    @Override
    public synchronized String next() {
        return handleInput(scanner::next, "", "String");
    }

    /**
     * This operation is required in order to conform to <code>Iterator&lt;String></code> but is
     * not supported.  Normally an <code>UnsupportedOperationException</code> would be thrown to
     * indicate this situation, but as this class aims to provide a simple way of reading input this is
     * a "do nothing" method.
     */
    @Override
    public synchronized void remove() {
        // Method not supported
    }

    /**
     * @return <code>true</code> if there is a <code>char</code> to input, <code>false</code>
     * otherwise.
     * NB This method will return false when there is a single end-of-line left in the file.
     */
    public synchronized boolean hasNextChar() {
        return handleInput(scanner::hasNext, false, "char");
    }

    /**
     * @return the next <code>char</code> in the input stream. Or return the null character by default
     * if there is no valid input.
     */
    public synchronized char nextChar() {
        return handleInput(() ->
                {
                    String result = scanner.findWithinHorizon("(?s).", 1);
                    return result != null ? result.charAt(0) : '\0';
                },
                '\0', "char");
    }

    /**
     * @return <code>true</code> if there is an <code>int</code> to input, <code>false</code>
     * otherwise.
     */
    public synchronized boolean hasNextInt() {
        return handleInput(scanner::hasNextInt, false, "int");
    }

    /**
     * @return the next <code>int</code> in the input stream assumed to be in the default radix
     * which is 10. Or return zero by default if there is no valid input.
     */
    public synchronized int nextInt() {
        return handleInput(scanner::nextInt, 0, "int");
    }

    /**
     * @param radix the radix (number base) of the input.
     * @return the next <code>int</code> in the input stream using the radix
     * <code>radix</code>. Or return zero by default if there is no valid input.
     */
    public synchronized int nextInt(final int radix) {
        return handleInput(() -> scanner.nextInt(radix), 0, "int");
    }

    /**
     * @return <code>true</code> if there is a <code>long</code> to input, <code>false</code>
     * otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextLong() {
        return handleInput(scanner::hasNextLong, false, "long");
    }

    /**
     * @return the next <code>long</code> in the input stream assumed to be in the default radix
     * which is 10. Or return zero by default if there is no valid input.
     */
    public synchronized long nextLong() {
        return handleInput(scanner::nextLong, 0L, "long");
    }

    /**
     * @param radix the radix (number base) of the input sequence.
     * @return the next <code>long</code> in the input stream using the radix
     * <code>radix</code>. Or return zero by default if there is no valid input.
     */
    public synchronized long nextLong(final int radix) {
        return handleInput(() -> scanner.nextLong(radix), 0L, "long");
    }

    /**
     * @return <code>true</code> if there is a <code>BigInteger</code> to input, <code>false</code>
     * otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextBigInteger() {
        return handleInput(scanner::hasNextBigInteger, false, "BigInteger");
    }

    /**
     * @return the next <code>BigInteger</code> in the input stream assumed to be in the default
     * radix which is 10. Or return zero by default if there is no valid input.
     */
    public synchronized BigInteger nextBigInteger() {
        return handleInput(scanner::nextBigInteger, BigInteger.ZERO, "BigInteger");
    }

    /**
     * @param radix the radix of the input sequence.
     * @return the next <code>BigInteger</code> in the input stream using the radix
     * <code>radix</code. Or return zero by default if there is no valid input.
     */
    public synchronized BigInteger nextBigInteger(final int radix) {
        return handleInput(() -> scanner.nextBigInteger(radix), BigInteger.ZERO, "BigInteger");
    }

    /**
     * @return <code>true</code> if there is a <code>float</code> to input, <code>false</code>
     * otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextFloat() {
        return handleInput(scanner::hasNextFloat, false, "float");
    }

    /**
     * @return the next <code>float</code> in the input stream. Or return zero by
     * default if there is no valid input.
     */
    public synchronized float nextFloat() {
        return handleInput(scanner::nextFloat, 0.0f, "float");
    }

    /**
     * @return <code>true</code> if there is a <code>double</code> to input, <code>false</code>
     * otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextDouble() {
        return handleInput(scanner::hasNextDouble, false, "float");
    }

    /**
     * @return the next <code>double</code> in the input stream. Or return zero by
     * default if there is no valid input.
     */
    public synchronized double nextDouble() {
        return handleInput(scanner::nextDouble, 0.0, "double");
    }

    /**
     * @return <code>true</code> if there is a <code>BigDecimal</code> to input,
     * <code>false</code> otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextBigDecimal() {
        return handleInput(scanner::hasNextBigDecimal, false, "BigDecimal");
    }

    /**
     * @return the next <code>BigDecimal</code> in the input stream. Or return zero by
     * default if there is no valid input.
     */
    public synchronized BigDecimal nextBigDecimal() {
        return handleInput(scanner::nextBigDecimal, BigDecimal.ZERO, "BigDecimal");
    }

    /**
     * @return <code>true</code> if there is more input including an end of line marker,
     * <code>false</code> otherwise. Or return false by default if there is no valid input.
     */
    public synchronized boolean hasNextLine() {
        return handleInput(scanner::hasNextLine, false, "String");
    }

    /**
     * @return all the characters in the input stream up to and including the next end of line
     * marker in the input stream. Or return an empty string by default if there is no valid input.
     */
    public synchronized String nextLine() {
        return handleInput(scanner::nextLine, "", "String");
    }

    /**
     * Set the flag to throw an IllegalStateException if an attempt is made to read input from a
     * closed stream. This enables the program using this class to be notified that the error has
     * occurred and attempt to recover from it. Otherwise, an error message is displayed on standard
     * error but there is no straightforward way for the using program to recognise it has
     * occurred.
     * If an exception is thrown, the using program will terminate if the exception is not caught.
     *
     * @param throwIllegalStateException true if an exception is to be thrown, false otherwise.
     */
    public synchronized void setThrowIllegalStateException(boolean throwIllegalStateException) {
        this.throwIllegalStateException = throwIllegalStateException;
    }

    // The following are all private utility methods, which are not commented with documentation
    // comments as they are not part of the public view of the class.

    // Use the supplier function to get the next input and handle any exceptions that get thrown.
    // EasyIO.Input can be of any of the types supported by the class.
    // This method avoids the need to duplicate the exception handling code in all the
    // next methods.
    private <T> T handleInput(SupplierWithException<T> supplier, T defaultValue, String type) {
        if (!closed) {
            try {
                return supplier.get();
            } catch (InputMismatchException e) {
                handleInputMismatchException(type);
            } catch (NoSuchElementException e) {
                handleNoSuchElementException();
            } catch (IllegalStateException e) {
                handleIllegalStateException();
            }
        }
        return defaultValue;
    }

    // Output a message to standard error that the input is closed.
    // Use the flag to determine whether an exception is thrown or not.
    private void handleIllegalStateException() {
        System.err.println("IllegalStateException: The input is closed.");
        if (throwIllegalStateException) {
            throw new IllegalStateException("Scanner is closed.");
        }
    }

    // Output m message to standard error that the requested input type does not match the contents
    // in the input buffer. A hasNext method should always be used before a next method to confirm that
    // the input is of the expected type.
    private void handleInputMismatchException(String type) {
        System.err.println("InputMismatchException: EasyIO.Input does not match expected " + type + " type.");
    }

    // Output m message to standard error that the requested input is not available.
    private void handleNoSuchElementException() {
        System.err.println("NoSuchElementException: No input available.");
    }

    @FunctionalInterface
    private interface SupplierWithException<T> {
        T get() throws InputMismatchException, NoSuchElementException, IllegalStateException;
    }
}
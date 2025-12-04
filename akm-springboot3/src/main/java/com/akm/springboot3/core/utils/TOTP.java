package com.akm.springboot3.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This is an example implementation of the OATH TOTP algorithm. Visit
 * www.openauthentication.org for more information.
 *
 * @author Johan Rydell, PortWise, Inc.
 */

public class TOTP {

    private static final int[] DIGITS_POWER
        // 0 1 2 3 4 5 6 7 8
        = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    private TOTP() {
    }

    /**
     * This method uses the JCE to provide the crypto algorithm. HMAC computes a
     * Hashed Message Authentication Code with the crypto hash algorithm as a
     * parameter.
     *
     * @param crypto   : the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @param keyBytes : the bytes to use for the HMAC key
     * @param text     : the message or text to be authenticated
     */
    private static byte[] hmacSha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * This method converts a HEX string to Byte[]
     *
     * @param hex : the HEX string
     * @return: a byte array
     */

    private static byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        System.arraycopy(bArray, 1, ret, 0, ret.length);
        return ret;
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key          : the shared secret, HEX encoded
     * @param time         : a value that reflects a time
     * @param returnDigits : number of digits to return
     * @return: a numeric String in base 10 that includes
     */

    public static String generateTotp(String key, String time,
                                      String returnDigits) {
        return generateTotp(key, time, returnDigits, "HmacSHA1");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key          : the shared secret, HEX encoded
     * @param time         : a value that reflects a time
     * @param returnDigits : number of digits to return
     * @return: a numeric String in base 10 that includes
     */

    public static String generateTotp256(String key, String time,
                                         String returnDigits) {
        return generateTotp(key, time, returnDigits, "HmacSHA256");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key          : the shared secret, HEX encoded
     * @param time         : a value that reflects a time
     * @param returnDigits : number of digits to return
     * @return: a numeric String in base 10 that includes
     */

    public static String generateTotp512(String key, String time,
                                         String returnDigits) {
        return generateTotp(key, time, returnDigits, "HmacSHA512");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     *
     * @param key          : the shared secret, HEX encoded
     * @param time         : a value that reflects a time
     * @param returnDigits : number of digits to return
     * @param crypto       : the crypto function to use
     * @return: a numeric String in base 10 that includes
     */

    public static String generateTotp(String key, String time,
                                      String returnDigits, String crypto) {
        int codeDigits = Integer.decode(returnDigits);
        StringBuilder result;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        StringBuilder timeBuilder = new StringBuilder(time);
        while (timeBuilder.length() < 16) {
            timeBuilder.insert(0, "0");
        }
        time = timeBuilder.toString();

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);
        byte[] hash = hmacSha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = ((hash[offset] & 0x7f) << 24)
            | ((hash[offset + 1] & 0xff) << 16)
            | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = new StringBuilder(Integer.toString(otp));
        while (result.length() < codeDigits) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Seed for HMAC-SHA1 - 20 bytes
        String seed = "3132333435363738393031323334353637383930";
        // Seed for HMAC-SHA256 - 32 bytes
        String seed32 = "3132333435363738393031323334353637383930"
            + "313233343536373839303132";
        // Seed for HMAC-SHA512 - 64 bytes
        String seed64 = "3132333435363738393031323334353637383930"
            + "3132333435363738393031323334353637383930"
            + "3132333435363738393031323334353637383930" + "31323334";
        long t0 = 0;
        long x = 30;
        long[] testTime = {59L, 1111111109L, 1111111111L, 1234567890L,
            2000000000L, 20000000000L};

        StringBuilder steps;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println("+---------------+-----------------------+"
                + "------------------+--------+--------+");
            System.out.println("|  Time(sec)    |   Time (UTC format)   "
                + "| Value of T(Hex)  |  TOTP  | Mode   |");
            System.out.println("+---------------+-----------------------+"
                + "------------------+--------+--------+");

            for (long l : testTime) {
                long t = (l - t0) / x;
                steps = new StringBuilder(Long.toHexString(t).toUpperCase());
                while (steps.length() < 16) {
                    steps.insert(0, "0");
                }
                String fmtTime = String.format("%1$-11s", l);
                String utcTime = df.format(new Date(l * 1000));
                System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | "
                    + steps + " |");
                System.out.println(generateTotp(seed, steps.toString(), "8", "HmacSHA1")
                    + "| SHA1   |");
                System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | "
                    + steps + " |");
                System.out.println(generateTotp(seed32, steps.toString(), "8",
                    "HmacSHA256") + "| SHA256 |");
                System.out.print("|  " + fmtTime + "  |  " + utcTime + "  | "
                    + steps + " |");
                System.out.println(generateTotp(seed64, steps.toString(), "8",
                    "HmacSHA512") + "| SHA512 |");

                System.out.println("+---------------+-----------------------+"
                    + "------------------+--------+--------+");
            }
        } catch (final Exception e) {
            System.out.println("Error : " + e);
        }
    }
}

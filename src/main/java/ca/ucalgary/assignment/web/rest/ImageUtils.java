package ca.ucalgary.assignment.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import org.apache.commons.io.FileUtils;

public class ImageUtils {

    public static String toBase64(String filePath) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    public static void fromBase64(String encodedString, File outputFile) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
    }

    public static String ocr(String inputFilename) throws IOException, InterruptedException {
        String outputFilename = inputFilename + "_o";
        Process p = Runtime.getRuntime().exec("tesseract " + inputFilename + " " + outputFilename);
        p.waitFor();
        String output = FileUtils.readFileToString(new File(outputFilename + ".txt"));
        return output;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(toBase64("sample/receipt2.jpg"));
        String o = "sample/o";
        fromBase64("", new File(o));
        String s = ocr(o);
        System.out.println(s);
    }
}

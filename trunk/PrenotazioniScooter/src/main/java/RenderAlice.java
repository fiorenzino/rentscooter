/*
 * RenderAlice.java
 * 
 * Created on Jun 19, 2007, 8:06:48 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.*;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author joshy
 */
public class RenderAlice {

    public static void main(String[] args) 
            throws IOException, DocumentException {
        String inputFile = "/home/fiorenzo/workspace/PrenotazioniScooter/WebContent/print/alice.xhtml";
        String url = new File(inputFile).toURI().toURL().toString();
        String outputFile = "alice.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        
        ITextRenderer renderer = new ITextRenderer();
        
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(os);
        
        os.close();
    }
}
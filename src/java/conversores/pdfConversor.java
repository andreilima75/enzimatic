package conversores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class pdfConversor {

    public static void main(String[] args) throws IOException {

        readParaFromPDF("D:\\enzimatic\\tabelas\\SUGESTÕES 2019.pdf", "D:\\enzimatic\\tabelas\\SUGESTÕES 2019.txt");

    }

    public static void readParaFromPDF(String pdfPath, String txtPath) {

        try {
            PDDocument document = PDDocument.load(new File(pdfPath));
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                String returnString = tStripper.getText(document);

                try (FileWriter x = new FileWriter(txtPath, false)) {
                    x.write(returnString);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

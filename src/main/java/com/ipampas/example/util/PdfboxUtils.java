package com.ipampas.example.util;


import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/4/1 5:49 PM
 */
public class PdfboxUtils {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/caizj/Downloads/专业投资者评定表.pdf");
        PDDocument document = PDDocument.load(file);
        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

        acroForm.getFields().stream().forEach(field -> {


            System.out.println(field.toString() + "|" + field.getFullyQualifiedName());
            try {
                if (field instanceof PDCheckBox) {

                } else if (field instanceof PDTextField) {

                    PDTextField pdTextField = (PDTextField) field;

                    String string = pdTextField.getCOSObject().getString(COSName.TU);

                    String string1 = field.getCOSObject().getString(COSName.TU);

                    COSBase cosObject1 = field.getCOSObject().getCOSName(COSName.TU).getCOSObject().getCOSObject();


                    COSObject cosObject = field.getCOSObject().getCOSObject(COSName.TU);

                    cosObject.getObject();


                } else if (field instanceof PDPushButton) {

                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        acroForm.flatten();

        document.save("/Users/caizj/Downloads/test1.pdf");
        document.close();
        System.out.println("Done");
    }
}

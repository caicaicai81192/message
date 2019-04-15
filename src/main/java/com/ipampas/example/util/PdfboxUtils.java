package com.ipampas.example.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/4/1 5:49 PM
 */
@Slf4j
public class PdfboxUtils {

    public static void main(String[] args) throws IOException {

        File file = ResourceUtils.getFile("classpath:files/test.pdf");
        File resultFile = ResourceUtils.getFile("classpath:files/result.pdf");
        //
        PDDocument document = PDDocument.load(file);
        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        //
        acroForm.getFields().stream().forEach(field -> {
            log.info(field.toString() + "|" + field.getFullyQualifiedName());
            //
            try {
                if (field instanceof PDCheckBox) {

                } else if (field instanceof PDTextField) {
                    //备注
                    String tu = field.getCOSObject().getString(COSName.TU);
                    log.debug(tu);


                } else if (field instanceof PDPushButton) {

                } else {

                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        //
        acroForm.flatten();
        document.save(resultFile);
        document.close();
        log.info("Done");
    }
}

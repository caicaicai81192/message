package com.ipampas.example.util;

import com.alibaba.fastjson.JSON;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfChoiceFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/4/1 5:49 PM
 */
public class ItextUtils {


    public static void main(String[] args) throws IOException {

        InputStream inputStream = new FileInputStream("/Users/caizj/Downloads/专业投资者评定表.pdf");

        PdfDocument pdf = new PdfDocument(new PdfReader(inputStream), new PdfWriter("/Users/caizj/Downloads/test.pdf"));



        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getFormFields();

        for (Map.Entry<String, PdfFormField> entry : fields.entrySet()) {
            PdfFormField pdfFormField = entry.getValue();

            System.out.println(pdfFormField.toString() + "|" + pdfFormField.getFieldName());

            if (pdfFormField instanceof PdfTextFormField) {

                PdfTextFormField pdfTextFormField = (PdfTextFormField) pdfFormField;


                pdfTextFormField.setValue("你好");

                PdfObject defaultValue = pdfTextFormField.getDefaultValue();

                System.out.println(defaultValue);

            } else if (pdfFormField instanceof PdfButtonFormField) {

                PdfButtonFormField pdfButtonFormField = (PdfButtonFormField) pdfFormField;

                String[] appearanceStates = pdfButtonFormField.getAppearanceStates();
                System.out.println(JSON.toJSONString(appearanceStates));

                if (pdfButtonFormField.isPushButton()) {

                } else if (pdfButtonFormField.isToggleOff()) {

                    pdfButtonFormField.setCheckType(PdfFormField.TYPE_CHECK);

                    pdfButtonFormField.setValue("1");

                } else if (pdfButtonFormField.isRadio()) {

                }

            } else if (pdfFormField instanceof PdfChoiceFormField) {

            }
        }
        //不可编辑
        form.flattenFields();
        pdf.close();


    }
}

package Clases;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import sv.edu.catolica.vlsp_inventarios.ViewPDFActivity;


public class TemplatePDFinv {

    private  Context context;
    public File pdfFile;
    private com.itextpdf.text.Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;

    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHightText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public TemplatePDFinv(Context context){
        this.context = context;
    }

    public void openDocument(){
        createFile();
        try {
            document = new com.itextpdf.text.Document(PageSize.LETTER);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        } catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    private void createFile(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "REPORTES_VLSP");
        if(!folder.exists()) folder.mkdirs();
        pdfFile = new File(folder, "Reporte VLSP "+ new Date().toString()+ ".pdf");

    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject){
        document.addTitle(title);
        document.addSubject(subject);
    }

    public void addTitles(String title, String subTitle, String date){
        try {
            paragraph = new Paragraph();
            addChild(new Paragraph(title,fTitle));
            addChild(new Paragraph(subTitle,fSubTitle));
            addChild(new Paragraph("Generado: "+date,fHightText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addTitles", e.toString());
        }
    }

    private void addChild(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fHightText);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            paragraph.setSpacingAfter(10);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> pros){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while(indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR = 0; indexR<pros.size(); indexR++){
                String[] row = pros.get(indexR);
                for(indexC = 0; indexC<header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }
    }

    public void viewPDF(){
        Intent intent = new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /*
    public void viewPDF(Reportes activity){
        if(pdfFile.exists()){
            Uri uri;
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                uri = Uri.parse(String.valueOf(pdfFile));
            } else{
                uri = Uri.fromFile(pdfFile);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getContext(), "No cuentas con una aplicacion para visualizar pdf", Toast.LENGTH_LONG);
            }
        } else{
            Toast.makeText(activity.getContext(), "No se encontró el archivo", Toast.LENGTH_LONG);
        }
    }*/

}

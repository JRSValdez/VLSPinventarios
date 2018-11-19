package sv.edu.catolica.vlsp_inventarios;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import Clases.TemplatePDFinv;
import Clases.Venta;


public class Reportes extends Fragment {

    Button btnReporteInv, btnReportVentas, btnReporteCompras;
    private String empresa_name, reporte;
    private  String[] headerInv = {"ID", "Categoría", "Producto", "Existencia"};
    private  String[] headerVentas = {"ID", "Fecha", "Total"};
    private TemplatePDFinv templateInv;
    File pdf;
    int idEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        empresa_name = getArguments().getString("empresa");
        idEmpresa = getArguments().getInt("idEmpresa");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        btnReporteInv = getView().findViewById(R.id.btnReporteInv);
        btnReporteInv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                templateInv = new TemplatePDFinv(getContext());
                templateInv.openDocument();
                templateInv.addMetaData(empresa_name, "Reporte de Inventario VLSP");
                templateInv.addTitles("VLSP inventarios", "REPORTE DE INVENTARIO " + empresa_name, new Date().toString());

                templateInv.createTable(headerInv,getInventario());
                pdf = templateInv.pdfFile;
                templateInv.closeDocument();

                templateInv.viewPDF();
            }
        });

        btnReportVentas = getView().findViewById(R.id.btnReporteVentas);
        btnReportVentas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                templateInv = new TemplatePDFinv(getContext());
                templateInv.openDocument();
                templateInv.addMetaData(empresa_name, "Reporte de Ventas VLSP");
                templateInv.addTitles("VLSP inventarios", "REPORTE DE VENTAS " + empresa_name, new Date().toString());

                templateInv.createTable(headerVentas,getVentas());
                pdf = templateInv.pdfFile;
                templateInv.closeDocument();

                templateInv.viewPDF();
            }
        });

        btnReporteCompras = getView().findViewById(R.id.btnReporteCompras);
        btnReporteCompras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                templateInv = new TemplatePDFinv(getContext());
                templateInv.openDocument();
                templateInv.addMetaData(empresa_name, "Reporte de Compras VLSP");
                templateInv.addTitles("VLSP inventarios", "REPORTE DE COMPRAS " + empresa_name, new Date().toString());

                templateInv.createTable(headerVentas,getVentas());
                pdf = templateInv.pdfFile;
                templateInv.closeDocument();

                templateInv.viewPDF();
            }
        });
    }

    private ArrayList<String[]> getInventario(){
        ArrayList<String[]> rows = new ArrayList<>();
        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            String query = "select p.idProducto, c.cat_name,p.producto_name, p.producto_stock from producto p " +
            " inner join categoria c on c.idCat = p.idCat where p.idEmpresa = ? and eliminado = 0";

            PreparedStatement preparedStatement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                rows.add(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)});
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return rows;
    }

    private ArrayList<String[]> getVentas(){
        ArrayList<String[]> rows = new ArrayList<>();
        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            String query = "select idVenta, venta_date, venta_total from ventas where idEmpresa = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                rows.add(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)});
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return rows;
    }

    private ArrayList<String[]> getCompras(){
        ArrayList<String[]> rows = new ArrayList<>();
        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();

            Statement st = conn.createStatement();

            String query = "select idCompra, compra_date, compra_total from compras where idEmpresa = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, idEmpresa);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                rows.add(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)});
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return rows;
    }

}

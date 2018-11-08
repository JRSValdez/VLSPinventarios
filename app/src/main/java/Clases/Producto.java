package Clases;

import java.io.Serializable;

public class Producto implements Serializable{
    public int idEmpresa;
    public int idProducto;
    public int idCategoria;
    public String producto;
    public float cantidad;
    public double precio;
    public double costo;
    public String descripcion;
    public String Vencimiento;

    public Producto(){

    }


    public Producto(int _idEmp, int _idCat, String _nomb, float _cant, double _precio, double _costo, String _desc, String _exp){
        this.idEmpresa = _idEmp;
        this.idCategoria = _idCat;
        this.producto = _nomb;
        this.cantidad = _cant;
        this.precio = _precio;
        this.costo = _costo;
        this.descripcion = _desc;
        this.Vencimiento = _exp;
    }
}

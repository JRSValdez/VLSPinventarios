package Clases;

import java.io.Serializable;

public class Categoria implements Serializable{
    public int idEmpresa;
    public int idCategoria;
    public String cat_name;
    public Categoria(Integer _idEmp, Integer _idCat, String _cat){
        this.idEmpresa = _idEmp;
        this.idCategoria = _idCat;
        this.cat_name = _cat;    }
    public Categoria(){
    }
}

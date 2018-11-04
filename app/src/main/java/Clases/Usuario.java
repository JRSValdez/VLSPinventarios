package Clases;


import java.io.Serializable;

// se implementa Serializable para poder mandar los objetos entre activitis
public class Usuario implements Serializable {
    public String usario;
    public String pass;
    public String nombre;
    public int idEmpresa;
    public String empresa;

    public Usuario(String _user, String _pass){
        this.usario = _user;
        this.pass = _pass;
    }

}

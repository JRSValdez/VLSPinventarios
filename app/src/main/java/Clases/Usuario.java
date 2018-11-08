package Clases;


import java.io.Serializable;

// se implementa Serializable para poder mandar los objetos entre activitis
public class Usuario implements Serializable {

    public int idEmpresa;
    public String empresa;
    public String nombre;
    public String apellido;
    public String usario;
    public String email;
    public String pass;
    public String pass2;

    public Usuario(){

    }

    public Usuario(String _user, String _pass){
        this.usario = _user;
        this.pass = _pass;
    }

}

//package Marketplace;

import java.util.List;

public class Orden{
    int Id;
    int IdUsuario;
    List <Producto> Productos;
    boolean Reserva;

    public Orden( int Id, int IdUsuario, List <Producto> Productos, boolean Reserva){
        this.Id = Id;
        this.IdUsuario = IdUsuario;
        this.Productos = Productos;
        this.Reserva = Reserva;
    }

    //setters y getters necesarios
    public void setId(int Id){
        this.Id = Id;
    }

    public int getId(){
        return this.Id;
    }

    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }

    public int getIdUsuario(){
        return this.IdUsuario;
    }

    public boolean isReserva(){
        return this.Reserva;
    }

}

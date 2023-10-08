import java.util.List;

public class Orden{
    int Id;
    int IdUsuario;
    List <Producto> Productos;
    boolean Reserva;

    public ProcesarOrden(){
        //Esto solo para que permita
        return 2;
    }

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

}
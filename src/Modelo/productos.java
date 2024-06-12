
package Modelo;

/**
 *
 * @author ebrey
 */
public class productos {
    private int id, stock;
    private String codigo, nombre, nombreProveedor;
    private double precio;

    public productos() {
    }

    public productos(int id, int stock, String codigo, String nombre, String nombreProveedor, double precio) {
        this.id = id;
        this.stock = stock;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nombreProveedor = nombreProveedor;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    

}    
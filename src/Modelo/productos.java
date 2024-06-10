
package Modelo;

/**
 *
 * @author ebrey
 */
public class productos {
    private int id, stock;
    private String codigo, pro_nombre, marca;
    private double precio;

    public productos() {
    }

    public productos(int id, int stock, String codigo, String pro_nombre, String marca, double precio) {
        this.id = id;
        this.stock = stock;
        this.codigo = codigo;
        this.pro_nombre = pro_nombre;
        this.marca = marca;
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

    public String getPro_nombre() {
        return pro_nombre;
    }

    public void setPro_nombre(String pro_nombre) {
        this.pro_nombre = pro_nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;
/**
 *
 * @author daian
 */
import java.util.List;

public interface GenericService<T> {

    void insertar(T t) throws Exception;

    T getById(long id) throws Exception;

    List<T> getAll() throws Exception;

    void actualizar(T t) throws Exception;

    void eliminar(long id) throws Exception;
}
    


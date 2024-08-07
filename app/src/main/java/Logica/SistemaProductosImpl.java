package Logica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.*;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DataBase.DbLocal;
import Dominio.Producto;
import Utils.NetworkUtils;

public class SistemaProductosImpl implements SistemaProductos, Serializable {
    private List<Producto> listaProducto;
    private static SistemaProductos instance;
    private Context context;
    private DbLocal dbLocal;

    private SistemaProductosImpl(Context context) {
        this.context = context;
        this.dbLocal = new DbLocal(context);
        listaProducto = new ArrayList<>();

    }

    public static synchronized SistemaProductosImpl getInstance(Context context) {
        if (instance == null) {
            instance = new SistemaProductosImpl(context);
        }
        return (SistemaProductosImpl) instance;
    }

    @Override
    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    @Override
    public boolean editarProducto(Producto producto) {
        int posProducto = busquedaLinealProductos(producto.getId());
        if (posProducto != -1) {
            listaProducto.set(posProducto, producto);
            actualizarProductoEnBD(producto);
            actualizarProductoEnFirestore(producto); // Agregar actualización en Firestore
            return true;
        }
        return false;
    }

    @Override
    public void ingresarProducto(Producto producto) {
        listaProducto.add(producto);
        try {
            guardarProductoEnBD(producto);
            guardarProductoEnFirestore(producto); // Agregar guardado en Firestore
        } catch (Exception e) {
            System.out.println("Ya agregado");
        }
    }

    public int busquedaLinealProductos(String nombre) {
        for (int i = 0; i < listaProducto.size(); i++) {
            if (nombre.equalsIgnoreCase(listaProducto.get(i).getNombre())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int busquedaLinealProductos(int id) {
        for (int i = 0; i < listaProducto.size(); i++) {
            if (id ==listaProducto.get(i).getId() ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        int pos = busquedaLinealProductos(producto.getNombre());
        if (pos != -1) {
            listaProducto.remove(pos);
            eliminarProductoDeBD(producto.getId());
            eliminarProductoDeFirestore(producto.getId());
            return true;
        }
        return false;
    }

    public void obtenerProductos() {
        if (NetworkUtils.isInternetAvailable(context)) {
            System.out.println("Internet disponible");
            obtenerProductosDesdeFirestore();
        } else {
            System.out.println("Internet no disponible");
            obtenerProductosDesdeBD();
        }
    }

    private void obtenerProductosDesdeFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("productos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaProducto.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Producto producto = new Producto(
                                document.getLong("id").intValue(),
                                document.getString("categoria"),
                                document.getString("nombre"),
                                document.getLong("cantidad").intValue()
                        );
                        listaProducto.add(producto);
                    }
                    if (listaProducto.isEmpty()) {
                        obtenerProductosDesdeBD();
                    } else {
                        try{
                            guardarProductosEnBD();
                        }catch(Exception e){
                            System.out.println("Ya agregado");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al obtener productos de Firestore: " + e.getMessage());
                    obtenerProductosDesdeBD();
                });
    }

    private void obtenerProductosDesdeBD() {
        listaProducto.clear();
        SQLiteDatabase db = dbLocal.getReadableDatabase();
        Cursor cursor = db.query(DbLocal.TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("categoria")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
                );
                listaProducto.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void guardarProductoEnBD(Producto producto) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", producto.getId());
        values.put("categoria", producto.getCategoria());
        values.put("nombre", producto.getNombre());
        values.put("cantidad", producto.getCantidad());

        db.insert(DbLocal.TABLE_PRODUCTOS, null, values);
    }



    private void actualizarProductoEnBD(Producto producto) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("categoria", producto.getCategoria());
        values.put("nombre", producto.getNombre());
        values.put("cantidad", producto.getCantidad());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(producto.getId())};

        db.update(DbLocal.TABLE_PRODUCTOS, values, selection, selectionArgs);
    }

    private void eliminarProductoDeBD(int id) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(DbLocal.TABLE_PRODUCTOS, selection, selectionArgs);
    }

    private void guardarProductosEnBD() {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DbLocal.TABLE_PRODUCTOS, null, null); // Limpiar tabla antes de insertar nuevos datos
            for (Producto producto : listaProducto) {
                ContentValues values = new ContentValues();
                values.put("id", producto.getId());
                values.put("categoria", producto.getCategoria());
                values.put("nombre", producto.getNombre());
                values.put("cantidad", producto.getCantidad());
                db.insert(DbLocal.TABLE_PRODUCTOS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }



    public void guardarProductosEnFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productosRef = db.collection("productos");
        for (Producto producto : listaProducto) {
            productosRef.document(String.valueOf(producto.getId()))
                    .set(producto)
                    .addOnSuccessListener(aVoid -> System.out.println("Producto guardado con éxito: " + producto.getNombre()))
                    .addOnFailureListener(e -> {
                        System.err.println("Error al guardar producto: " + producto.getNombre());
                        e.printStackTrace();
                    });
        }
    }
    public void eliminarProductoDeFirestore(int idProducto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("productos").document(String.valueOf(idProducto))
                .delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Producto eliminado con éxito de Firestore: " + idProducto);
                    // También eliminar el producto de la lista local
                    int posProducto = busquedaLinealProductos(idProducto);
                    if (posProducto != -1) {
                        listaProducto.remove(posProducto);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al eliminar el producto de Firestore: " + idProducto);
                    e.printStackTrace();
                });
    }
    private void actualizarProductoEnFirestore(Producto producto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("productos").document(String.valueOf(producto.getId()))
                .set(producto)
                .addOnSuccessListener(aVoid -> System.out.println("Producto actualizado con éxito: " + producto.getNombre()))
                .addOnFailureListener(e -> {
                    System.err.println("Error al actualizar producto: " + producto.getNombre());
                    e.printStackTrace();
                });
    }
    private void guardarProductoEnFirestore(Producto producto) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productosRef = db.collection("productos");
        productosRef.document(String.valueOf(producto.getId()))
                .set(producto)
                .addOnSuccessListener(aVoid -> System.out.println("Producto guardado con éxito: " + producto.getNombre()))
                .addOnFailureListener(e -> {
                    System.err.println("Error al guardar producto: " + producto.getNombre());
                    e.printStackTrace();
                });
    }
}

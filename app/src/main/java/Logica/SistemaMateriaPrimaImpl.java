package Logica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataBase.DbLocal;
import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaMateriaPrimaImpl implements SistemaMateriaPrima {
    private static SistemaMateriaPrima instance;
    private final List<MateriaPrima> listaMateriaPrima;
    private final Context context;
    private final DbLocal dbLocal;

    private SistemaMateriaPrimaImpl(Context context) {
        this.context = context;
        this.dbLocal = new DbLocal(context);
        listaMateriaPrima = new ArrayList<>();
        obtenerMateriasPrimas();


    }

    public static synchronized SistemaMateriaPrima getInstance(Context context) {
        if (instance == null) {
            instance = new SistemaMateriaPrimaImpl(context);
        }
        return instance;
    }

    @Override
    public void ingresarMateriaPrima(MateriaPrima materiaPrima) {
        listaMateriaPrima.add(materiaPrima);
        guardarMateriaPrimaEnBD(materiaPrima);
        guardarMateriaPrimaEnFirestore(materiaPrima); // Agregar guardado en Firestore
    }

    @Override
    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }


    @Override
    public int busquedaLineal(int id) {
        for (int i = 0; i < listaMateriaPrima.size(); i++) {
            if (listaMateriaPrima.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean editarMateriaPrima(MateriaPrima materiaPrima) {
        listaMateriaPrima.set(busquedaLineal(materiaPrima.getId()), materiaPrima);
        actualizarMateriaPrimaEnBD(materiaPrima);
        actualizarMateriaPrimaEnFirestore(materiaPrima); // Agregar actualización en Firestore
        return true;
    }

    @Override
    public boolean eliminarMateriaPrima(MateriaPrima materiaPrima) {
        listaMateriaPrima.remove(busquedaLineal(materiaPrima.getId()));
        eliminarMateriaPrimaDeBD(materiaPrima.getId());
        eliminarMateriaPrimaDeFirestore(materiaPrima.getId());
        return true;
    }


    private void obtenerMateriasPrimas() {
        listaMateriaPrima.clear();
        SQLiteDatabase db = dbLocal.getReadableDatabase();
        Cursor cursor = db.query(DbLocal.TABLE_MATERIAS_PRIMAS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                MateriaPrima materiaPrima = new MateriaPrima(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("cantidad")),
                        cursor.getString(cursor.getColumnIndexOrThrow("unidad"))
                );
                listaMateriaPrima.add(materiaPrima);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void guardarMateriaPrimaEnBD(MateriaPrima materiaPrima) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", materiaPrima.getId());
        values.put("nombre", materiaPrima.getNombre());
        values.put("cantidad", materiaPrima.getCantidad());
        values.put("unidad", materiaPrima.getUnidad());

        db.insert(DbLocal.TABLE_MATERIAS_PRIMAS, null, values);
    }

    private void actualizarMateriaPrimaEnBD(MateriaPrima materiaPrima) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", materiaPrima.getNombre());
        values.put("cantidad", materiaPrima.getCantidad());
        values.put("unidad", materiaPrima.getUnidad());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(materiaPrima.getId())};

        db.update(DbLocal.TABLE_MATERIAS_PRIMAS, values, selection, selectionArgs);
    }

    private void eliminarMateriaPrimaDeBD(int id) {
        SQLiteDatabase db = dbLocal.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(DbLocal.TABLE_MATERIAS_PRIMAS, selection, selectionArgs);
    }
    public void guardarProductosEnFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productosRef = db.collection("materias_primas");
        for (MateriaPrima materiaPrima : listaMateriaPrima) {
            productosRef.document(String.valueOf(materiaPrima.getId()))
                    .set(materiaPrima)
                    .addOnSuccessListener(aVoid -> System.out.println("Materia Prima guardado con éxito: " + materiaPrima.getNombre()))
                    .addOnFailureListener(e -> {
                        System.err.println("Error al guardar producto: " + materiaPrima.getNombre());
                        e.printStackTrace();
                    });
        }
    }
    private void guardarMateriaPrimaEnFirestore(MateriaPrima materiaPrima) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference materiasPrimasRef = db.collection("materias_primas");
        materiasPrimasRef.document(String.valueOf(materiaPrima.getId()))
                .set(materiaPrima)
                .addOnSuccessListener(aVoid -> System.out.println("Materia Prima guardada con éxito: " + materiaPrima.getNombre()))
                .addOnFailureListener(e -> {
                    System.err.println("Error al guardar materia prima: " + materiaPrima.getNombre());
                    e.printStackTrace();
                });
    }
    public void eliminarMateriaPrimaDeFirestore(int idMateriaPrima) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("materias_primas").document(String.valueOf(idMateriaPrima))
                .delete()
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Materia Prima eliminada con éxito de Firestore: " + idMateriaPrima);
                    // También eliminar la materia prima de la lista local
                    int posMateriaPrima = busquedaLineal(idMateriaPrima);
                    if (posMateriaPrima != -1) {
                        listaMateriaPrima.remove(posMateriaPrima);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al eliminar la Materia Prima de Firestore: " + idMateriaPrima);
                    e.printStackTrace();
                });
    }
    private void actualizarMateriaPrimaEnFirestore(MateriaPrima materiaPrima) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("materias_primas").document(String.valueOf(materiaPrima.getId()))
                .set(materiaPrima)
                .addOnSuccessListener(aVoid -> System.out.println("Materia Prima actualizada con éxito: " + materiaPrima.getNombre()))
                .addOnFailureListener(e -> {
                    System.err.println("Error al actualizar materia prima: " + materiaPrima.getNombre());
                    e.printStackTrace();
                });
    }
}

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
        materiaPrima.setId(listaMateriaPrima.size());
        listaMateriaPrima.add(materiaPrima);
        guardarMateriaPrimaEnBD(materiaPrima);
    }

    @Override
    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

    @Override
    public String busquedaLinealMateriaPrima(String materiaPrima) {
        return null;
    }

    @Override
    public int busquedaBinaria(int id) {
        int posIzq = 0;
        int posDer = listaMateriaPrima.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            if (id == listaMateriaPrima.get(posMid).getId()) {
                return posMid;
            } else if (id < listaMateriaPrima.get(posMid).getId()) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return -1;
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
        listaMateriaPrima.set(busquedaBinaria(materiaPrima.getId()), materiaPrima);
        actualizarMateriaPrimaEnBD(materiaPrima);
        return true;
    }

    @Override
    public boolean eliminarMateriaPrima(MateriaPrima materiaPrima) {
        listaMateriaPrima.remove(busquedaLineal(materiaPrima.getId()));
        eliminarMateriaPrimaDeBD(materiaPrima.getId());
        return true;
    }

    @Override
    public void actualizarMateriasPrimas(Collection<MateriaPrima> materiasPrimasActualizadas) {
        for (MateriaPrima materiaPrimaActualizada : materiasPrimasActualizadas) {
            for (MateriaPrima materiaPrima : listaMateriaPrima) {
                if (materiaPrima.getId() == materiaPrimaActualizada.getId()) {
                    materiaPrima.setCantidad(materiaPrimaActualizada.getCantidad());
                    actualizarMateriaPrimaEnBD(materiaPrima);
                    break;
                }
            }
        }
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
}

package Logica;

public class SistemaMateriaPrimaimpl implements SistemaMateriaPrima {
    private static SistemaMateriaPrima  instance;

    private SistemaMateriaPrimaimpl(){

    }
    public static SistemaMateriaPrima getInstance(){
        if(instance == null){
            instance = new SistemaMateriaPrimaimpl();
        }
        return instance;
    }
}


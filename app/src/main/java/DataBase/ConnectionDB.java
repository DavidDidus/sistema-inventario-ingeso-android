package DataBase;


public class ConnectionDB {
    //private static Firestore db;
    private static ConnectionDB instance;

    private ConnectionDB() {

    }

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
/*
    private void conectFirebase() {
        try{
            if(isInternetAvailable()){
                FileInputStream sa =
                        new FileInputStream("src/main/resources/firebase_connection.json");
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(sa))
                        .build();
                FirebaseApp.initializeApp(options);
                db = FirestoreClient.getFirestore();
            } else {
                System.out.println("Error de conexión");
                db = null;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al cargar el archivo de configuración de Firebase");
            db = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Firestore getDb() {
        return db;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            if (address.isReachable(3500)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    */

}
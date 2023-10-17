import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Convertirdocs {
    public static void main(String[] args) {
        try {
            String respuesta = JOptionPane.showInputDialog("¿Quieres convertir un archivo XML a JSON? (Sí/No)");

            if (respuesta != null && respuesta.equalsIgnoreCase("Sí")) {
                // Pedir al usuario el nombre del archivo XML a convertir
                String nombreArchivoXML = JOptionPane.showInputDialog("Ingrese el nombre del archivo XML a convertir (incluyendo la extensión, por ejemplo, 'archivo.xml'):");

                // Leer el archivo XML y convertirlo a JSON
                JsonArray autos = leerArchivoXML(nombreArchivoXML);

                // Calcular el precio promedio por marca
                Map<String, Double> preciosPromedioPorMarca = calcularPrecioPromedio(autos);
                String nombreArchivoJSON = JOptionPane.showInputDialog("Ingrese el nombre del archivo JSON de salida (incluyendo la extensión, por ejemplo, 'resultado.json'):");
                guardarArchivoJSON(nombreArchivoJSON, preciosPromedioPorMarca);
                JOptionPane.showMessageDialog(null, "La conversión se ha realizado con éxito. El archivo JSON resultante se ha guardado como '" + nombreArchivoJSON + "'.");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha realizado la conversión.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public static JsonArray leerArchivoXML(String nombreArchivo) {
        try (FileReader fileReader = new FileReader(nombreArchivo)) {
            Gson gson = new Gson();
            AutoList autoList = gson.fromJson(fileReader, AutoList.class);
            return gson.toJsonTree(autoList.getAutos()).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Double> calcularPrecioPromedio(JsonArray autos) {
        Map<String, Double> preciosPromedioPorMarca = new HashMap<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Auto>>() {}.getType();
        List<Auto> listaAutos = gson.fromJson(autos, listType);

        for (Auto auto : listaAutos) {
            String marca = auto.getMarca();
            double precio = auto.getPrecio();

            if (preciosPromedioPorMarca.containsKey(marca)) {
                double precioPromedioActual = preciosPromedioPorMarca.get(marca);
                preciosPromedioPorMarca.put(marca, (precioPromedioActual + precio) / 2);
            } else {
                preciosPromedioPorMarca.put(marca, precio);
            }
        }
        return preciosPromedioPorMarca;
    }

    public static void guardarArchivoJSON(String nombreArchivo, Map<String, Double> preciosPromedioPorMarca) {
        Gson gson = new Gson();
        String json = gson.toJson(preciosPromedioPorMarca);

        try (FileWriter archivoJSON = new FileWriter(nombreArchivo)) {
            archivoJSON.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AutoList {
        private List<Auto> autos;

        public List<Auto> getAutos() {
            return autos;
        }
    }

    public class Auto {
        private String marca;
        private double precio;

        public String getMarca() {
            return marca;
        }

        public double getPrecio() {
            return precio;
        }
    }

}

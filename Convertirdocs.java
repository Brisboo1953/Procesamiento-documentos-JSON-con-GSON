import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Convertirdocs {
    public static void main(String[] args) {
        try {
            // Preguntar al usuario si desea realizar la conversión
            String respuesta = JOptionPane.showInputDialog("¿Desea convertir un archivo XML a JSON? (Sí/No)");

            if (respuesta != null && respuesta.equalsIgnoreCase("Sí")) {
                // Pedir al usuario el nombre del archivo XML a convertir
                String nombreArchivoXML = JOptionPane.showInputDialog("Por favor, ingrese el nombre del archivo XML a convertir (incluyendo la extensión, por ejemplo, 'archivo.xml'):");

                // Leer el archivo XML
                JSONParser parser = new JSONParser();
                JSONArray autos = (JSONArray) parser.parse(new FileReader(nombreArchivoXML));

                // Crear un mapa para calcular el precio promedio por marca
                Map<String, Double> preciosPromedioPorMarca = new HashMap<>();

                // Calcular el precio promedio por marca
                for (Object autoObjeto : autos) {
                    JSONObject auto = (JSONObject) autoObjeto;
                    String marca = (String) auto.get("marca");
                    double precio = (double) auto.get("precio");

                    if (preciosPromedioPorMarca.containsKey(marca)) {
                        double precioPromedioActual = preciosPromedioPorMarca.get(marca);
                        preciosPromedioPorMarca.put(marca, (precioPromedioActual + precio) / 2);
                    } else {
                        preciosPromedioPorMarca.put(marca, precio);
                    }
                }

                // Crear un archivo JSON con el resultado
                String nombreArchivoJSON = JOptionPane.showInputDialog("Por favor, ingrese el nombre del archivo JSON de salida (incluyendo la extensión, por ejemplo, 'resultado.json'):");
                FileWriter archivoJSON = new FileWriter(nombreArchivoJSON);
                archivoJSON.write(convertirInformeJSON(preciosPromedioPorMarca));
                archivoJSON.close();

                // Mostrar un mensaje de confirmación
                JOptionPane.showMessageDialog(null, "La conversión se ha realizado con éxito. El archivo JSON resultante se ha guardado como '" + nombreArchivoJSON + "'.");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha realizado la conversión.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en la conversión.");
        }
    }

    public static String convertirInformeJSON(Map<String, Double> informe) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Double> entry : informe.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject.toJSONString();
    }
}


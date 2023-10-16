import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JOptionPane;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MarcaProm {
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();

            // Leer el archivo JSON
            JSONArray autos = (JSONArray) parser.parse(new FileReader("C:/Users/WIN10/Downloads/car_sales.json"));
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

            // Imprimir el informe de precios promedio
            StringBuilder informe = new StringBuilder("Precios promedio por marca:\n");
            for (Map.Entry<String, Double> entry : preciosPromedioPorMarca.entrySet()) {
                informe.append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
            }
            JOptionPane.showMessageDialog(null, informe.toString());
            String marcaElegida = JOptionPane.showInputDialog("¿Desea calcular el promedio de una marca específica? (Escriba el nombre de la marca o 'Salir' para finalizar)");
            if (!marcaElegida.equalsIgnoreCase("Salir")) {
                if (preciosPromedioPorMarca.containsKey(marcaElegida)) {
                    double precioPromedio = preciosPromedioPorMarca.get(marcaElegida);
                    JOptionPane.showMessageDialog(null, "El precio promedio de la marca " + marcaElegida + " es: $" + precioPromedio);
                } else {
                    JOptionPane.showMessageDialog(null, "Marca no encontrada en el informe.");
                }
            }
                 } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

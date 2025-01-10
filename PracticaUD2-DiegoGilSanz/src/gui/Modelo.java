/**
 * La clase modelo se encarga de albergar el codigo mas tecnico de la aplicacion.
 *
 */
package gui;

import util.Util;

import java.io.*;
import java.sql.*;

import java.time.LocalDate;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;
    private Connection conexion;
    //Constructor modelo que se encarga de oobtener los datos de conexion de la base de datos
    public Modelo() {
           getPropValues();
    }

    /**
     * Metodo que obtiene los parametros
     * @return ip
     * @return user
     * @return password
     * @return adminPassword
     *
     */
    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
    //Metodo que obtiene los parametros de la base de datos
    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo que establece los parametros de la base de datos
    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }
    //Metodo que se encarga de la conexion con la base de datos
    void conectar() {

        try {
            conexion= DriverManager.getConnection("jdbc:mysql://"+ip+"/Gimnasio",user,password);
        } catch (SQLException e) {
        e.printStackTrace();
        }   try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/",user, password);

            PreparedStatement statement = null;

            String code = leerFichero();
            String[] query = code.split("--");
            for (String aQuery : query) {
                statement = conexion.prepareStatement(aQuery);
                statement.executeUpdate();
            }
            assert statement != null;
            statement.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    //Metodo que se encarga de leer el fichero de configuracion
    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("ScriptBBDD.sql")) ;
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
    //Metodo que se encarga de desconectar la base de datos
    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    //Metodo que añade un entrenador a la base de datos
    void insertarEntrenador(String nombre, String apellidos, LocalDate fechaInicio, String nacionalidad){
      String consultaSql = "INSERT INTO entrenador (nombre, apellidos, fechaInicio, pais) VALUES (?,?,?,?)";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,apellidos);
            prepa.setDate(3,java.sql.Date.valueOf(fechaInicio));
            prepa.setString(4,nacionalidad);
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que añade una liga a la base de datos
    void insertarLiga(String nombre, String descripcion, int participantes, String tipo, String web){

        String consultaSql = "INSERT INTO liga (nombre, descripcion, participantes, tipoliga, web) VALUES (?,?,?,?,?)";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,descripcion);
            prepa.setInt(3,participantes);
            prepa.setString(4,tipo);
            prepa.setString(5,web);
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que añade un peleador a la base de datos
    void insertarPeleador(String nombre, String estilo, String liga, String entrenador, String genero, float peso, LocalDate nacimiento){
        String consultaSql = "INSERT INTO peleador (nombre, estilo, idLiga, idEntrenador, genero, peso, fechanacimiento) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement prepa=null;
        int idLiga=Integer.valueOf(liga.split(" ")[0]);
        int idEntrenador=Integer.valueOf(entrenador.split(" ")[0]);
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,estilo);
            prepa.setInt(3,idLiga);
            prepa.setInt(4,idEntrenador);
            prepa.setString(5,genero);
            prepa.setFloat(6,peso);
            prepa.setDate(7,java.sql.Date.valueOf(nacimiento));
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que modifica un entrenador en la base de datos
    void modificarEntrenador(String nombre, String apellidos, LocalDate fechaInicio, String nacionalidad, int id){
        String consultaSql = "UPDATE entrenador SET nombre=?, apellidos=?, fechaInicio=?, pais=? WHERE idEntrenador=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,apellidos);
            prepa.setDate(3,java.sql.Date.valueOf(fechaInicio));
            prepa.setString(4,nacionalidad);
            prepa.setInt(5,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            Util.showErrorAlert("Error al modificar Entrenador, posiblemente no exista");
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que modifica una liga en la base de datos
    void modificarLiga(String nombre, String descripcion, int participantes, String tipo, String web, int id){
        String consultaSql = "UPDATE liga SET nombre=?, descripcion=?, participantes=?, tipoliga=?, web=? WHERE idLiga=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,descripcion);
            prepa.setInt(3,participantes);
            prepa.setString(4,tipo);
            prepa.setString(5,web);
            prepa.setInt(6,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Util.showErrorAlert("Error al modificar Liga, posiblemente no exista");
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que modifica un peleador en la base de datos
    void modificarPeleador(String nombre, String estilo, String liga, String entrenador, String genero, double peso, LocalDate nacimiento, int id){
        String consultaSql = "UPDATE peleador SET nombre=?, estilo=?, idLiga=?, idEntrenador=?, genero=?, peso=?, fechanacimiento=? WHERE idPeleador=?";
        PreparedStatement prepa=null;
        int idLiga=Integer.valueOf(liga.split(" ")[0]);
        int idEntrenador=Integer.valueOf(entrenador.split(" ")[0]);
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,estilo);
            prepa.setInt(3,idLiga);
            prepa.setInt(4,idEntrenador);
            prepa.setString(5,genero);
            prepa.setDouble(6,peso);
            prepa.setDate(7,java.sql.Date.valueOf(nacimiento));
            prepa.setInt(8,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            Util.showErrorAlert("Error al modificar peleador, posiblemente no exista");
        }finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que elimina una liga de la base de datos
    void eliminarLiga(int id){
        String consultaSql = "DELETE FROM liga WHERE idLiga=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setInt(1,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }



    }
    //Metodo que elimina un entrenador de la base de datos
    void eliminarEntrenador(int id){
        String consultaSql = "DELETE FROM entrenador WHERE idEntrenador=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setInt(1,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que elimina un peleador de la base de datos
    void eliminarPeleador(int id){
        String consultaSql = "DELETE FROM peleador WHERE idPeleador=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setInt(1,id);
            prepa.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (prepa != null)
                try {
                    prepa.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    //Metodo que consulta las ligas de la base de datos
    ResultSet consultarLiga(){
     String consultaSql = "SELECT idLiga, nombre, descripcion, participantes, tipoliga, web FROM liga";
        PreparedStatement prepa=null;
        ResultSet rs=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            rs=prepa.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //Metodo que consulta los entrenadores de la base de datos
    ResultSet consultarEntrenador(){
        String consultaSql = "SELECT idEntrenador, nombre, apellidos, fechaInicio, pais FROM entrenador";
        PreparedStatement prepa=null;
        ResultSet rs=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            rs=prepa.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //Metodo que consulta los peleadores de la base de datos
    ResultSet consultarPeleador(){
        String consultaSql = "SELECT idPeleador, nombre, estilo, idLiga, idEntrenador, genero, peso, fechanacimiento FROM peleador";
        PreparedStatement prepa=null;
        ResultSet rs=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            rs=prepa.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //metodo que llama a la funcion de existe nombre de entrenador
    public boolean nombreEntrenadorExiste(String nombre){
        String consulta="SELECT existeNombreEntrenador(?)";
        boolean existe=false;
        PreparedStatement funcion;
        try {
            funcion=conexion.prepareStatement(consulta);
            funcion.setString(1,nombre);
            ResultSet rs=funcion.executeQuery();
            rs.next();
            existe=rs.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return existe;
    }
    //metodo que llama a la funcion de existe nombre de liga
    public boolean nombreLigaExiste(String nombre){
        String consulta="SELECT existeNombreLiga(?)";
        boolean existe=false;
        PreparedStatement funcion;
        try {
            funcion=conexion.prepareStatement(consulta);
            funcion.setString(1,nombre);
            ResultSet rs=funcion.executeQuery();
            rs.next();
            existe=rs.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return existe;
    }
    //metodo que llama a la funcion de existe nombre de peleador
    public boolean nombrePeleadorExiste(String nombre){
        String consulta="SELECT existeNombrePeleador(?)";
        boolean existe=false;
        PreparedStatement funcion;
        try {
            funcion=conexion.prepareStatement(consulta);
            funcion.setString(1,nombre);
            ResultSet rs=funcion.executeQuery();
            rs.next();
            existe=rs.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return existe;
    }

}


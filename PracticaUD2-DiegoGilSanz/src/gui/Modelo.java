package gui;

import java.io.*;
import java.sql.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;
    private Connection conexion;

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

    void conectar() {

        try {
            conexion= DriverManager.getConnection("jdbc:mysql://"+ip+"/Gimnasio",user,password);
        } catch (SQLException e) {
        e.printStackTrace();
        }   try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/",user, password);

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

    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    void insertarEntrenador(String nombre, String apellidos, LocalDate fechaInicio, String nacionalidad){
      String consultaSql = "INSERT INTO entrenador (nombre, apellidos, fechaInicio, nacionalidad) VALUES (?,?,?,?)";
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
    void insertarLiga(String nombre, String descripcion, int participantes, String tipo, String web){

        String consultaSql = "INSERT INTO liga (nombre, descripcion, participantes, tipo, web) VALUES (?,?,?,?,?)";
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
    void insertarPeleador(String nombre, String estilo, String liga, String entrenador, String genero, double peso, LocalDate nacimiento){
        String consultaSql = "INSERT INTO peleador (nombre, estilo, liga, entrenador, genero, peso, nacimiento) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,estilo);
            prepa.setString(3,liga);
            prepa.setString(4,entrenador);
            prepa.setString(5,genero);
            prepa.setDouble(6,peso);
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
    void modificarEntrenador(String nombre, String apellidos, LocalDate fechaInicio, String nacionalidad, int id){
        String consultaSql = "UPDATE entrenador SET nombre=?, apellidos=?, fechaInicio=?, nacionalidad=? WHERE id=?";
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
    void modificarLiga(String nombre, String descripcion, int participantes, String tipo, String web, int id){
        String consultaSql = "UPDATE liga SET nombre=?, descripcion=?, participantes=?, tipo=?, web=? WHERE id=?";
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
    void modificarPeleador(String nombre, String estilo, String liga, String entrenador, String genero, double peso, LocalDate nacimiento, int id){
        String consultaSql = "UPDATE peleador SET nombre=?, estilo=?, liga=?, entrenador=?, genero=?, peso=?, nacimiento=? WHERE id=?";
        PreparedStatement prepa=null;
        try {
            prepa=conexion.prepareStatement(consultaSql);
            prepa.setString(1,nombre);
            prepa.setString(2,estilo);
            prepa.setString(3,liga);
            prepa.setString(4,entrenador);
            prepa.setString(5,genero);
            prepa.setDouble(6,peso);
            prepa.setDate(7,java.sql.Date.valueOf(nacimiento));
            prepa.setInt(8,id);
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
    void eliminarLiga(int id){
        String consultaSql = "DELETE FROM liga WHERE id=?";
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
    void eliminarEntrenador(int id){
        String consultaSql = "DELETE FROM entrenador WHERE id=?";
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
    void eliminarPeleador(int id){
        String consultaSql = "DELETE FROM peleador WHERE id=?";
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
    ResultSet consultarLiga(){
     String consultaSql = "SELECT id, nombre, descripcion, participantes, tipo, web FROM liga";
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
    ResultSet consultarEntrenador(){
        String consultaSql = "SELECT id, nombre, apellidos, fechaInicio, nacionalidad FROM entrenador";
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
    ResultSet consultarPeleador(){
        String consultaSql = "SELECT id, nombre, estilo, liga, entrenador, genero, peso, nacimiento FROM peleador";
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


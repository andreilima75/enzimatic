package conversores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeiaCVS {

    private final String url = "jdbc:postgresql://localhost/postgres";
    private final String user = "postgres";
    private final String password = "postgres";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void createItem(Connection conn, String nome, String receita, Double valorcobrado, Double valorreal, Integer idlaboratorio, Integer idtipo, String opcao, String apresentacao) {
        try {
            Statement stmnt = null;
            stmnt = conn.createStatement();
            String sql = "INSERT INTO public.produtos (nome,receita,valorcobrado,valorreal,idlaboratorio,idtipo,opcao, apresentacao) "
                    + "VALUES " + "('" + nome + "', '" + receita + "', '" + valorcobrado + "','" + valorreal + "','" + idlaboratorio + "','" + idtipo + "','" + opcao + "','" + apresentacao + "')";
            System.out.println(sql);

            stmnt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void createItem(Connection conn, String nome, String apresentacao, Double valorcobrado, Double valorreal, Integer idlaboratorio, Integer idtipo) {
        try {
            Statement stmnt = null;
            stmnt = conn.createStatement();
            String sql = "INSERT INTO public.produtos (nome,apresentacao,valorcobrado,valorreal,idlaboratorio,idtipo,opcao, receita) "
                    + "VALUES " + "('" + nome + "', '" + apresentacao + "', '" + valorcobrado + "','" + valorreal + "','" + idlaboratorio + "','" + idtipo + "','','')";
            System.out.println(sql);

            stmnt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        LeiaCVS obj = new LeiaCVS();
        Connection conn = obj.connect();
        String arquivoCSV = "D:\\enzimatic\\tabelas\\Pasta1.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {

                String[] produtos = linha.split(csvDivisor);

               // obj.createItem(conn, produtos[0].trim(), produtos[1].trim(), Double.parseDouble(produtos[3].replaceAll(",", ".").trim()), Double.parseDouble(produtos[2].replaceAll(",", ".").trim()), Integer.parseInt(produtos[4].trim()), Integer.parseInt(produtos[5].trim()),produtos[6].trim(),produtos[7].trim());

                obj.createItem(conn, produtos[0].trim(), produtos[1].trim(), Double.parseDouble(produtos[3].replaceAll(",", ".").trim()), Double.parseDouble(produtos[2].replaceAll(",", ".").trim()), Integer.parseInt(produtos[4].trim()), Integer.parseInt(produtos[5].trim()));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeiaCVS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

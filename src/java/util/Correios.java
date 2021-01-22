/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Andrei
 */
public class Correios {

    public String execute(String cepOrigem, String cepDestino) {
        String address = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?"
                + "nCdEmpresa="
                + "&sDsSenha="
                + "&sCepOrigem=" + cepOrigem
                + "&sCepDestino=" + cepDestino
                + "&nVlPeso=2"
                + "&nCdFormato=1"
                + "&nVlComprimento=16"
                + "&nVlAltura=9"
                + "&nVlLargura=18"
                + "&sCdMaoPropria=N"
                + "&nVlValorDeclarado=0"
                + "&sCdAvisoRecebimento=N"
                + "&nCdServico=04014"
                + "&nVlDiametro=0"
                + "&StrRetorno=xml";
        HttpURLConnection con = null;
        PrintStream out = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new java.net.URL(address); //aqui se define o endereço que devolverá o arquivo
            con = (java.net.HttpURLConnection) url.openConnection();//prepara conexão com o cliente
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "text/xml");//define o tipo de retorno, pode ser outro tipo também
            out = new PrintStream(con.getOutputStream());//obtém o que o cliente devolveu
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String xml;

            while ((xml = buffReader.readLine()) != null) {
                sb.append(xml);//recebe o arquivo xml do cliente
            }
            System.out.println(sb.toString()); //imprime o xml retornado :)

            out.close();
            con.disconnect(); // sempre feche as conexões!

        } catch (Exception e) {
            e.printStackTrace();
            out.close();
            con.disconnect(); // sempre feche as conexões!
        }
        return readXML(sb.toString());
    }

    private String readXML(String s) {
        String retorno = null;
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(s)));

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("cServico");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    retorno = eElement.getElementsByTagName("Valor").item(0).getTextContent();
                    System.out.println("Valor : " + eElement.getElementsByTagName("Valor").item(0).getTextContent());
                    System.out.println("PrazoEntrega : " + eElement.getElementsByTagName("PrazoEntrega").item(0).getTextContent());
                    System.out.println("EntregaDomiciliar : " + eElement.getElementsByTagName("EntregaDomiciliar").item(0).getTextContent());
                    System.out.println("EntregaSabado : " + eElement.getElementsByTagName("EntregaSabado").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
    
    public double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
}

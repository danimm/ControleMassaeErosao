/*
 * Este programa é um software livre; você pode redistribuí-lo e/ou 

    modificá-lo dentro dos termos da Licença Pública Geral GNU como 

    publicada pela Fundação do Software Livre (FSF); na versão 2 da 

    Licença.

 */
package br.groupfive.massaerosao;

import Arduino.Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author Daniela Marques de Morais
 */
public class Graficos extends javax.swing.JFrame {
    private String com = "COM5";
    private int time = 5000;
    private int baud = 9600; 
    
       
    public Graficos() {
        initComponents();
        try {
            myarduino.ArduinoRXTX(com, time, baud, evento);
        } catch (Exception ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
   
        collection.addSeries(serieumidade);
        collection.addSeries(seriex);
        collection.addSeries(seriey);
        collection.addSeries(seriez);
        grafico = ChartFactory.createXYLineChart("Controle Movimento de Massa",
                "Tempo", "Sensores", collection,
                PlotOrientation.VERTICAL, true, true, false); 
        grafico.setBackgroundPaint(Color.decode("#EEEEEE"));

        ChartPanel painel = new ChartPanel(grafico);

        janela.getContentPane().add(painel);

        painel.setBorder(BorderFactory.createTitledBorder("Leitura de dados"));
        janela.pack();
        janela.setLocationRelativeTo(null);
       
        janela.setVisible(true);
    }
    
    final XYSeries serieumidade = new XYSeries("Umidade");
    final XYSeries seriex = new XYSeries("X");
    final XYSeries seriey = new XYSeries("Y");
    final XYSeries seriez = new XYSeries("Z");
    final XYSeriesCollection collection = new XYSeriesCollection(); 
    JFreeChart grafico; 
    Arduino myarduino = new Arduino();
    int i = 0;
    JFrame janela = new JFrame("Gráfico");
    SerialPortEventListener evento = new SerialPortEventListener() { 
    
        @Override
        public void serialEvent(SerialPortEvent spe) {
            if (myarduino.MessageAvailable()) {
                i++;
                String resposta = myarduino.PrintMessage();
                if (resposta.charAt(0) == 'S'){
                    jLabel2.setText(resposta);
                }
                if (resposta.charAt(0) == 'u') {
                    String umidade = resposta.substring(1, resposta.length());
                    jLabelValorUmidade.setText(umidade);
                    serieumidade.add(i, Integer.parseInt(umidade));   
                }
                if (resposta.charAt(0) == 'x') {
                    String valorx = resposta.substring(1, resposta.length());
                    jLabelValorX.setText(valorx);
                    seriex.add(i, Integer.parseInt(valorx));
                }
                if (resposta.charAt(0) == 'y') {
                    String valory = resposta.substring(1, resposta.length());
                    jLabelValorY.setText(valory);
                    seriey.add(i, Integer.parseInt(valory));
                }
                if (resposta.charAt(0) == 'z') {
                    String valorz = resposta.substring(1, resposta.length());
                    jLabelValorZ.setText(valorz);
                    seriez.add(i, Integer.parseInt(valorz));
                }
                chamamysql("localhost", "daniela", "root", "");
            }
        }
    };

    private void chamamysql(String local, String banco, String usuario, String senha) {
        try {
           
            Class.forName("com.mysql.jdbc.Driver");

            
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + local + "/" + banco,
                    usuario, senha);
            
            java.sql.Statement st = conn.createStatement();

            int valorumidade = Integer.parseInt(jLabelValorUmidade.getText());
            int valorx = Integer.parseInt(jLabelValorX.getText());
            int valory = Integer.parseInt(jLabelValorY.getText());
            int valorz = Integer.parseInt(jLabelValorZ.getText());
            st.executeUpdate("INSERT INTO dados (umidade, x, y, z, horario) VALUES ('"
                    + valorumidade + "','"
                    + valorx + "','"
                    + valory + "','"
                    + valorz 
                    + "',NOW())");

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabelValorUmidade = new javax.swing.JLabel();
        jLabelUmidade = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabelX = new javax.swing.JLabel();
        jLabelValorX = new javax.swing.JLabel();
        jLabelY = new javax.swing.JLabel();
        jLabelValorY = new javax.swing.JLabel();
        jLabelZ = new javax.swing.JLabel();
        jLabelValorZ = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle de Movimento de Massa e Erosão - Versão 1.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabelValorUmidade.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabelUmidade.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelUmidade.setText("Umidade:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Situação:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelUmidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelValorUmidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelUmidade, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValorUmidade, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabelX.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelX.setText("X:");

        jLabelValorX.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabelY.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelY.setText("Y:");

        jLabelValorY.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabelZ.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelZ.setText("Z:");

        jLabelValorZ.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabelX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelValorX, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelValorY, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelZ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelValorZ, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelValorY, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelValorX, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelZ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelValorZ, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        janela.dispose();
        myarduino.KillArduinoConnection();

    }//GEN-LAST:event_formWindowClosed

    public static void main() {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Graficos().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelUmidade;
    private javax.swing.JLabel jLabelValorUmidade;
    private javax.swing.JLabel jLabelValorX;
    private javax.swing.JLabel jLabelValorY;
    private javax.swing.JLabel jLabelValorZ;
    private javax.swing.JLabel jLabelX;
    private javax.swing.JLabel jLabelY;
    private javax.swing.JLabel jLabelZ;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables

   
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import kontrol_url.url_pembelian;
import handler.RequestHandler;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author AS_Pro
 */
public class Penjualan extends javax.swing.JInternalFrame {

    public ArrayList<HashMap<String, String>> postList;
    public ArrayList<HashMap<String, String>> post;
    public ArrayList<HashMap<String, String>> postListkecamatan;
    public ArrayList<HashMap<String, String>> postListdesa;
    public ArrayList<HashMap<String, String>> postListpos;
    public String item[];
    public String itemkota[];
    public String itemkecamtan[];
    public String itemdesa[];
    public String itempos[];
    public String Provinsitmp;
    public String idpenjualan;
    public HashMap<String, String> id_provinsi = new HashMap<>();
    public HashMap<String, String> id_kota = new HashMap<>();
    public HashMap<String, String> id_kecamatan = new HashMap<>();
    url_pembelian alamat = new url_pembelian();
    Vector tampungData = new Vector();
    Vector tampungKata = new Vector();
    public String namagambar;

    /**
     * Creates new form Penjualan
     */
    public Penjualan() {
        initComponents();
        view_namabarang process = new view_namabarang();

        try {
            process.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        view_provinsi process1 = new view_provinsi();

        try {
            process1.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void reset() {
        jTjumlah.setText("");
    }

    class view_namabarang extends SwingWorker {

        private String s;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            String alamatServer = alamat.ipAddress
                    + alamat.lokasiFile
                    + alamat.url_view;
            s = rh.sendGetRequest(alamatServer);
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    jCnama.addItem(jo.getString("nama"));
                }

            } catch (JSONException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }

    class view_id extends SwingWorker {

        private String s;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            String alamatServer = alamat.ipAddress
                    + alamat.lokasiFile
                    + alamat.url_viewid;
            s = rh.sendGetRequest(alamatServer);
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    jLid.setText(jo.getString("id"));
                }

            } catch (JSONException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }

    class view_provinsi extends SwingWorker {

        private String s;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            postList = new ArrayList<>();
            String json = "https://kodepos-2d475.firebaseio.com/list_propinsi.json";
            s = rh.sendGetRequest(json);
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                Iterator<String> iter = jsonObject.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        Object value = jsonObject.get(key);
                        String nama = value.toString();
                        HashMap<String, String> pos = new HashMap<>();
                        pos.put("name", nama);
                        pos.put("id", key);
                        postList.add(pos);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                item = new String[postList.size()];
                for (int i = 0; i < postList.size(); i++) {
                    item[i] = postList.get(i).get("name");
                    id_provinsi.put(postList.get(i).get("name"), postList.get(i).get("id"));
                    jCprovinsi.addItem(item[i]);
                }

            } catch (JSONException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }

    class view_kota extends SwingWorker {

        private String s;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            post = new ArrayList<>();
            String jsonKota = "https://kodepos-2d475.firebaseio.com/list_kotakab/" + id_provinsi.get(jCprovinsi.getSelectedItem()) + ".json";
            s = rh.sendGetRequest(jsonKota);
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                Iterator<String> iter = jsonObject.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        Object value = jsonObject.get(key);
                        String nama = value.toString();
                        HashMap<String, String> pos = new HashMap<>();
                        pos.put("name", nama);
                        pos.put("id", key);
                        post.add(pos);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                itemkota = new String[post.size()];
                for (int i = 0; i < post.size(); i++) {
                    itemkota[i] = post.get(i).get("name");
                    id_kota.put(post.get(i).get("name"), post.get(i).get("id"));
                    jCkota.addItem(itemkota[i]);
                }
            } catch (JSONException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
    }

    class view_kecamatan extends SwingWorker {

        private String s;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            postListkecamatan = new ArrayList<>();
            postListdesa = new ArrayList<>();
            postListpos = new ArrayList<>();
            String jsonKota = "https://kodepos-2d475.firebaseio.com/kota_kab/" + id_kota.get(jCkota.getSelectedItem()) + ".json";
            s = rh.sendGetRequest(jsonKota);
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                HashMap<String, String> kec = new HashMap<>();
                HashMap<String, String> des = new HashMap<>();
                HashMap<String, String> kode = new HashMap<>();
                JSONArray data = new JSONArray(s);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);

                    String kecamatan = c.getString("kecamatan");
                    String kelurahan = c.getString("kelurahan");
                    String kodepos = c.getString("kodepos");

                    HashMap<String, String> pos = new HashMap<>();

                    pos.put("kecamatan", kecamatan);
                    pos.put("kelurahan", kelurahan);
                    pos.put("kodepos", kodepos);
                    
                    if(kec.get(kecamatan)==null){
                        jCkecamatan.addItem(kecamatan);
                        kec.put(kecamatan, "");
                    }
                    if(des.get(kelurahan)==null){
                        jCkelurahan.addItem(kelurahan);
                        des.put(kelurahan, "");
                    }
                  //  System.out.println(kode.get(kodepos));
                    if(kode.get(kodepos)==null){
                        jCkode_pos.addItem(kodepos);
                        kode.put(kodepos, "");
                      //  System.out.println(kodepos);
                    }
                }
            } catch (final JSONException e) {
            }
        }
    }

    class viewAll extends SwingWorker {

        private String s;
        private String p;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            String alamatServer = alamat.ipAddress
                    + alamat.lokasiFile
                    + alamat.url_viewgambar;
            s = rh.sendGetRequestParam(alamatServer, jCnama.getSelectedItem().toString());
            return s;
        }

        @Override
        protected void done() {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    namagambar = jo.getString("gambar");
                }

            } catch (JSONException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }

    }

    private void cud(String kategori) {
        class proses extends SwingWorker {

            private String res;
            private String alamatServer;

            @Override
            protected Object doInBackground() {
                HashMap<String, String> params = new HashMap<>();
                if (kategori.matches("insert")) {
                    alamatServer = alamat.ipAddress
                            + alamat.lokasiFile
                            + alamat.url_penjualan;
                    params.put("nama_barang", jCnama.getSelectedItem().toString());
                    params.put("jumlah", jTjumlah.getText());
                    params.put("provinsi", jCprovinsi.getSelectedItem().toString());
                    params.put("kota", jCkota.getSelectedItem().toString());
                    params.put("kecamatan", jCkecamatan.getSelectedItem().toString());
                    params.put("desa", jCkelurahan.getSelectedItem().toString());
                    params.put("kode_pos", jCkode_pos.getSelectedItem().toString());
                }
                RequestHandler rh = new RequestHandler();
                res = rh.sendPostRequest(alamatServer, params);
                return res;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(rootPane, res);
                reset();
            }
        }
        proses process = new proses();
        try {
            process.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void cetaksuratjalan(String id) {
        try {
            try {
                String url = "http://182.253.199.116:107/layout_patron/suratjalan.php?id=" + id;
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error !! " + e);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCnama = new javax.swing.JComboBox();
        jTjumlah = new javax.swing.JTextField();
        jCprovinsi = new javax.swing.JComboBox();
        jCkota = new javax.swing.JComboBox();
        jCkecamatan = new javax.swing.JComboBox();
        jCkode_pos = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jCkelurahan = new javax.swing.JComboBox();
        jLid = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLimage = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Penjualan"));
        setClosable(true);
        setMaximizable(true);

        jLabel1.setText("Nama Barang");

        jLabel2.setText("Jumlah");

        jLabel3.setText("Provinsi");

        jLabel4.setText("Kota");

        jLabel5.setText("kecamatan");

        jLabel6.setText("Kode pos");

        jCnama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCnamaMouseClicked(evt);
            }
        });
        jCnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCnamaActionPerformed(evt);
            }
        });
        jCnama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCnamaKeyPressed(evt);
            }
        });

        jCprovinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCprovinsiActionPerformed(evt);
            }
        });

        jCkota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkotaActionPerformed(evt);
            }
        });

        jCkecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCkecamatanActionPerformed(evt);
            }
        });

        jButton2.setText("Proses");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Kelurahan");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        jLimage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLimage, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLimage, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("View Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jCkecamatan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCkota, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCprovinsi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCnama, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTjumlah, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCkode_pos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCkelurahan, 0, 123, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLid)))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jCnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLid)
                            .addComponent(jButton1))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jCprovinsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jCkota, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jCkecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jCkelurahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jCkode_pos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cud("insert");

        cetaksuratjalan((Integer.parseInt(jLid.getText()) + 1) + "."
                + jCnama.getSelectedItem().toString() + "."
                + jTjumlah.getText()
        );
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCprovinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCprovinsiActionPerformed
        // TODO add your handling code here:/
        jCkota.removeAllItems();
        view_kota process = new view_kota();
        try {
            process.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        view_id process1 = new view_id();
        try {
            process1.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jCprovinsiActionPerformed

    private void jCkotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkotaActionPerformed
//        // TODO add your handling code here:/
        jCkecamatan.removeAllItems();
        jCkelurahan.removeAllItems();
        jCkode_pos.removeAllItems();
        view_kecamatan process = new view_kecamatan();
        try {
            process.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jCkotaActionPerformed

    private void jCkecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCkecamatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCkecamatanActionPerformed

    private void jCnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCnamaActionPerformed
        // TODO add your handling code here:
        viewAll process1 = new viewAll();
        try {
            process1.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }

    }//GEN-LAST:event_jCnamaActionPerformed

    private void jCnamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCnamaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jCnamaMouseClicked

    private void jCnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCnamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCnamaKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String row = namagambar;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Penjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = toolkit.getImage(path + "/image/" + row);
        Image imagedResized = image.getScaledInstance(200, 250, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(imagedResized);
        jLimage.setIcon(icon);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jCkecamatan;
    private javax.swing.JComboBox jCkelurahan;
    private javax.swing.JComboBox jCkode_pos;
    private javax.swing.JComboBox jCkota;
    private javax.swing.JComboBox jCnama;
    private javax.swing.JComboBox jCprovinsi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLid;
    private javax.swing.JLabel jLimage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTjumlah;
    // End of variables declaration//GEN-END:variables
}

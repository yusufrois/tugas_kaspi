/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import handler.RequestHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import kontrol_url.url_pembelian;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AS_Pro
 */
public class log extends javax.swing.JInternalFrame {

    url_pembelian report = new url_pembelian();
    private HashMap<String, String> tmp;
    /**
     * Creates new form log
     */
    public log() {
        initComponents();
        data();
    }

    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
   public void data() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(model);
        model.addColumn("Nama Stok");
        model.addColumn("Jumlah");
        model.addColumn("Sisa Stok");
        model.addColumn("Keterangan");
        model.addColumn("Tanggal");
        viewAll process = new viewAll();
        try {
            process.execute();
            sortTabel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }

    }
   
   private void sortTabel() {
        TableRowSorter<TableModel> sorter
                = new TableRowSorter<TableModel>(jTable1.getModel());
        jTable1.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
    }
   
    class viewAll extends SwingWorker {

        private String s;
        private String p;

        @Override
        protected Object doInBackground() {
            RequestHandler rh = new RequestHandler();
            String alamatServer = report.ipAddress
                    + report.lokasiFile
                    + report.url_viewlog;
            s = rh.sendGetRequestParam(alamatServer, "ALL");
            return s;
        }

        @Override
        protected void done() {
            tmp = new HashMap();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            JSONObject jsonObject = null;
            try {

                jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray("result");
                Object[] obj = new Object[5];
                // int x = 1;
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    
                    obj[0] = jo.getString("nama");
                    obj[1] = jo.getString("jumlah");
                    obj[2] = jo.getString("sisa_stok");
                    obj[3] = jo.getString("keterangan");
                    obj[4] = jo.getString("tanggal");
                    model.addRow(obj);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Log Barang"));
        setClosable(true);
        setMaximizable(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

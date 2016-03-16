package gracze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


class Node{
    
    String info = "";
    Node left = null;
    Node right = null;
    int poziom = 1;
    
    Node(String in,int a)
    {
        info =in +a;
        poziom=1;
    }
    
    public void nodeLeft(Node n)
    {
        this.left = n;
        n.poziom=this.poziom+1;
    }
    
    public void nodeRight(Node n)
    {
        this.right = n;
        n.poziom=this.poziom+1;
    }
}

class Tree{
    
    Node root = null;
    long ilew = 0;
    
    Tree(Node a)
    {
        root = a;
        ilew++;
        a.poziom=1;
    }       
}

public class NewJFrame extends javax.swing.JFrame {
    
    public Scanner input = new Scanner(System.in);
    public int howManySeq = 0;
    public Tree tree; 
    public int winValue;
    public int loseValue; 
    public int treeLevel=0;
    
    public long silnia(long a)
    {
        long tmp=1;
        for(long i=1;i<=a;i++)
        {
            tmp*=i;
        }
        return tmp;
    }
    
    public long iloczynOdDo(long a, long b)
    {
        long tmp=1;
        for(long i=a;i<=b;i++)
        {
            tmp*=i;
        }
        return tmp;
    }
    
    public long dwumianNewtona(long n, long k)
    {
        long tmp=0;
      
        tmp=iloczynOdDo(n-k+1,n)/silnia(k);
        
        return tmp;
    }
    
    public int suma(int tab[], int n)
    {
        int s=0;
        
        for(int i=0;i<=n;i++)
            s+=tab[i];
        
        return s;
    }
    
    public int max(int a, int b)
    {
        if(a>b)
            return a;
        else
            return b;
    }
    
    public void KLP(Node r)
    {
        //System.out.print(r.info+ "  ");
        if(r.left!=null)
            KLP(r.left);
        if(r.right!=null)
            KLP(r.right);
        if(r.left == null && r.right == null)
            jTextArea1.setText(jTextArea1.getText()+r.info+"\n");
    }
    
    public String printTab(int tab[])
    {
        String tmp ="";
        for(int i=0;i<tab.length;i++)
            tmp+=tab[i]+"";  
        return tmp;
    }
       
    public void funkcja(int tab[], int a, int b,int x, int y, Node node)
    {
        int bat[] = new int [a+b];
        System.arraycopy(tab, 0, bat, 0, a+b);
        int tmp=0;
        while(bat[tmp]!=0)
        {
            tmp++;
            if(tmp>=a+b)
            {
                howManySeq++;
                treeLevel=max(treeLevel,node.poziom);
                return;
            }
        }
 
        if(suma(bat,tmp)==0)
        {
            if(x==0 && y==0)
            {
                Node root = new Node("",1);
                tree= new Tree(root);
                bat[tmp]=1;
                funkcja(bat,a,b,x+1,y,root);
            }
            else
            {
                Node next = new Node(node.info,1);
                node.nodeLeft(next);
                bat[tmp]=1;
                funkcja(bat,a,b,x+1,y,next);
            }
        }
        else if(suma(bat,tmp)>0)
        {
            if(y<b)
            {
                Node next = new Node(node.info,0);
                node.nodeRight(next);
                bat[tmp]=-1;
                funkcja(bat,a,b,x,y+1,next);
            }
            if(x<a)
            {   
                Node next = new Node(node.info,1);
                node.nodeLeft(next);
                bat[tmp]=1;
                funkcja(bat,a,b,x+1,y,next);
            }
        }
    }
    
    public int potega(int a, int b)
    {
        int tmp=1;
        for(int i=0;i<b;i++)
            tmp*=a;
        return tmp;
    }
    
    public NewJFrame() {
        initComponents();
        setPreferredSize(new Dimension(2000,1500));
    }
    
    public double min(double a, double b)
    {
        if(a<b)
            return a;
        else
            return b;
    }

    public double max(double a, double b)
    {
        if(a>b)
            return a;
        else
            return b;
    }
    
    public void rysTree()
    {
       JPanel treePanel = new JPanel()
       {    
            public void drawOval(Graphics x,double horizontalPosition,double verticalPosition, double width, double height, Color color)
            {
                x.fillOval((int)horizontalPosition,(int) verticalPosition,(int) width,(int) height);
                x.setColor(color);
            }
        
            public void drwTree(Node r,Graphics x,double horizontalPosition,double verticalPosition, double width, double height, double ParentHorizontalPosition)
            {
                if(r.left!=null)
                {   
                    double nextHorizontalPosition;
                    if(ParentHorizontalPosition==horizontalPosition)
                        nextHorizontalPosition = horizontalPosition/2;
                    else
                        nextHorizontalPosition =horizontalPosition-(max(ParentHorizontalPosition,horizontalPosition) - min(horizontalPosition,ParentHorizontalPosition))/2;
                    double nextVerticalPosition = verticalPosition+50;
                    x.drawLine((int)(horizontalPosition+width/2), (int) (verticalPosition+width/2), (int) (nextHorizontalPosition+width/2),(int) (nextVerticalPosition+width/2));
                    drwTree(r.left,x,nextHorizontalPosition,nextVerticalPosition,width,height,horizontalPosition);
                }
                if(r.right!=null)
                {
                    double nextHorizontalPosition;
                    if(ParentHorizontalPosition==horizontalPosition)
                        nextHorizontalPosition = 3*horizontalPosition/2;
                    else
                        nextHorizontalPosition =horizontalPosition-(min(horizontalPosition,ParentHorizontalPosition) - max(ParentHorizontalPosition,horizontalPosition))/2;
                    double q = verticalPosition+50;
                    x.drawLine((int)(horizontalPosition+width/2), (int) (verticalPosition+width/2), (int) (nextHorizontalPosition+width/2),(int) (q+width/2));
                    drwTree(r.right,x,nextHorizontalPosition,q,width,height,horizontalPosition);
                }
                if(r==tree.root)
                    x.setColor(Color.RED);
                else
                    x.setColor(Color.BLACK);
                x.drawString(r.info, (int) (horizontalPosition-10),(int) verticalPosition);
                drawOval(x,horizontalPosition,verticalPosition,width,height,Color.BLACK); 
            }
        
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                int r=30;
                int horizontalPosition=potega(2,(winValue+loseValue))*(15+3*(loseValue+winValue))/2;
                int verticalDistB2Nodes=50;
                drwTree(tree.root, g, horizontalPosition, verticalDistB2Nodes, r, r,horizontalPosition); 
            }
        };
       
        treePanel.setLayout(new BorderLayout());
        JFrame frame = new JFrame();
        double horizontalVisible=0.75;
        double verticalVisible=1.0;
        int horizontalMargin=100;
        int verticalMargin=100;
        int verticalDistB2Nodes=50;
        int minHorizontalDistB2Nodes=15+3*(loseValue+winValue);
        treePanel.setPreferredSize(new Dimension((int)(horizontalVisible*potega(2,(winValue+loseValue))*minHorizontalDistB2Nodes+horizontalMargin),(int) verticalVisible*((winValue+loseValue)*verticalDistB2Nodes)+verticalMargin));
        JScrollPane jScrollPane = new JScrollPane(treePanel);
        frame.add(jScrollPane, BorderLayout.CENTER);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Drzewo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setText("Ok");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jTextArea1.setColumns(5);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextArea1.setRows(2);
        jTextArea1.setTabSize(4);
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.getAccessibleContext().setAccessibleName("");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setNextFocusableComponent(jLabel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(4, 4, 4))
                            .addComponent(jButton1))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(106, 116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(100, 100, 100))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        rysTree();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int win=Integer.parseInt(this.jTextField1.getText());
        int lose=Integer.parseInt(this.jTextField2.getText());
        winValue=win;
        loseValue=lose;
        if(win>=lose)
        {
            int tab[] = new int[win+lose];
        
       
            for(int i=1;i<win+lose;i++)
                tab[i]=0;
       
            jTextArea1.setText("");
            howManySeq=0;
            treeLevel=0;
            funkcja(tab,win,lose,0,0,null);
            KLP(tree.root);
            this.jLabel3.setText("Szukanych: "+howManySeq);
            this.jLabel1.setText("Wszystkich: "+dwumianNewtona(win+lose, lose));
            this.jLabel2.setText("P-stwo: "+((double) howManySeq/dwumianNewtona(win+lose, lose)));
        }
        else
            jTextArea1.setText("Przegral wiecej razy niz wygral");
    }//GEN-LAST:event_jButton2ActionPerformed
  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
   
                NewJFrame a = new NewJFrame();
                a.setLayout(new GridLayout(1,3));
                a.jPanel1.setLayout(new GridLayout(4,1));
                a.jPanel1.add(a.jTextField1);
                a.jPanel1.add(a.jTextField2);
                a.jPanel1.add(a.jButton2);
                a.jPanel1.add(a.jButton1);
                a.jPanel2.setLayout(new GridLayout(3,1));
                a.jPanel2.add(a.jLabel1);
                a.jPanel2.add(a.jLabel3);
                a.jPanel2.add(a.jLabel2);
                a.setSize(400,150);
                a.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

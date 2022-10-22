import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;

public class GUI extends JFrame {
    JTextField path = new JTextField(18);
    // 定义组件
    JPanel jp1, jp2;
    JLabel jlb1, jlb2;
    JComboBox jcb1, jcb2;

    GUI d3;

    public static void main(String[] args) throws IOException {
        // 调用GUI
        GUI d3 = new GUI();
    }

    public GUI() throws IOException {
        // Main(this) 主体
        this.setTitle("我的世界服务端下载器 - 由alazeprt编写");
        this.setSize(500,300);
        this.setLayout(new GridLayout(6,-1));
        int windowWidth = this.getWidth(); //获得窗口宽
        int windowHeight = this.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示

        // 字体定义
        Font titlefont = new Font("Microsoft YaHei UI", Font.BOLD, 20);
        Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
        Font smallfont = new Font("Microsoft YaHei UI", Font.PLAIN, 12);
        // title 标题
        JPanel title2 = new JPanel();
        JLabel title = new JLabel();
        title.setText("我的世界服务端下载器");
        title.setFont(titlefont);
        title2.add(title);
        title.setVisible(true);
        this.add(title2);
        // server 选择框1
        jp1 = new JPanel();
        jlb1 = new JLabel("请选择服务端: ");
        jlb1.setFont(defaultfont);
        String[] server = {"Vanilla/原版"};
        jcb1 = new JComboBox(server);
        jcb1.setFont(smallfont);
        jp1.setFont(defaultfont);
        jp1.add(jlb1);
        jp1.add(jcb1);
        this.add(jp1);
        // version_list 选择框2
        jp2 = new JPanel();
        jlb2 = new JLabel("请选择版本: ");
        jlb2.setFont(defaultfont);
        ArrayList vl = new InternetGet().GetVersionList();
        int size = vl.size();
        String[] vlist = (String[])vl.toArray(new String[size]);
        jcb2 = new JComboBox(vlist);
        jcb2.setFont(smallfont);
        jp2.setFont(defaultfont);
        jp2.add(jlb2);
        jp2.add(jcb2);
        this.add(jp2);
        // path 文本框1
        JPanel path_panel = new JPanel();
        JLabel l1 = new JLabel("下载路径: ");
        l1.setFont(defaultfont);
        // file_choose 文件选择按钮
        JButton path_choose = new JButton();
        path_choose.setText("浏览...");
        path_choose.setFont(smallfont);
        path.setFont(smallfont);
        path_panel.add(l1);
        path_panel.add(path);
        path_panel.add(path_choose);
        this.add(path_panel);
        path_choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderchoose = new JFileChooser();
                folderchoose.setCurrentDirectory(new File("."));
                folderchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = folderchoose.showSaveDialog(d3);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = folderchoose.getSelectedFile();
                    path.setText(String.valueOf(file));
                }
            }
        });
        // download 按钮
        JPanel button = new JPanel();
        JButton download_button = new JButton("下载服务端");
        download_button.setFont(defaultfont);
        button.add(download_button);
        this.add(button);
        download_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String download_version = (String) jcb2.getSelectedItem();
                DownloadFile download = new DownloadFile();
                String url = null;
                url = download.GetVersionDownloadURL(download_version);
                if(!url.equals(null)){
                    UIManager.put("OptionPane.buttonFont", defaultfont);
                    UIManager.put("OptionPane.messageFont", defaultfont);
                    boolean a = download.DownloadNetFile(url, path.getText());
                    if(a == true){
                        JOptionPane.showMessageDialog(null, "服务端下载成功!","提示",JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "服务端下载失败!请重试!","错误",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
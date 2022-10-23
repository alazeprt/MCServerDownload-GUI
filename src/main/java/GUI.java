import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
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
        // 初始化语言
        LanguageReader a = new LanguageReader();
        if(a.GetLanguageList().size() == 0){
            a.WriteLanguage();
        }
        if(a.GetLanguage().equals("null")){
            a.SetLanguage("en_us");
        }
        Map map = a.GetLanguageMessage(a.GetLanguage());
        GUI b = new GUI(map);
    }

    public GUI(Map map) throws IOException {
        // Main(this) 主体
        this.setTitle(map.get("gui_title").toString());
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
        title.setText(map.get("title").toString());
        title.setFont(titlefont);
        title2.add(title);
        title.setVisible(true);
        this.add(title2);
        // server 选择框1
        jp1 = new JPanel();
        jlb1 = new JLabel(map.get("choose_server").toString());
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
        jlb2 = new JLabel(map.get("choose_version").toString());
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
        JLabel l1 = new JLabel(map.get("path").toString());
        l1.setFont(defaultfont);
        // file_choose 文件选择按钮
        JButton path_choose = new JButton();
        path_choose.setText(map.get("choose_folder").toString());
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
        JButton download_button = new JButton(map.get("download").toString());
        download_button.setFont(defaultfont);
        button.add(download_button);
        JButton languageset = new JButton(map.get("setlang").toString());
        languageset.setFont(defaultfont);
        button.add(languageset);
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
                        JOptionPane.showMessageDialog(null, map.get("complete").toString(),"提示",JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, map.get("error").toString(),"错误",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        languageset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetLanguageFrame();
            }
        });
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void SetLanguageFrame(){
        JFrame langset = new JFrame();
        langset.setTitle("Setting Language...");
        langset.setSize(400,200);
        langset.setLayout(new GridLayout(4,-1));
        int windowWidth = langset.getWidth(); //获得窗口宽
        int windowHeight = langset.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        langset.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        langset.setVisible(true);
        // 字体定义
        Font titlefont = new Font("Microsoft YaHei UI", Font.BOLD, 20);
        Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 16);
        // title2 标题
        JPanel a = new JPanel();
        JLabel title2 = new JLabel("Please Select Language");
        title2.setFont(titlefont);
        title2.setVisible(true);
        a.add(title2);
        langset.add(a);
        // Combobox 语言选择
        JPanel jp3 = new JPanel();
        JLabel jlb3 = new JLabel("Please Select Language: ");
        jlb3.setFont(defaultfont);
        ArrayList vl = new LanguageReader().GetLanguageList();
        ArrayList<String> vls = new ArrayList<>();
        vls.add("");
        for(Object v : vl){
            File tempfile = new File(v.toString().trim());
            String fileName = tempfile.getName().replace(".yml","");
            vls.add(fileName);
        }
        int size = vl.size();
        String[] vlist = (String[])vls.toArray(new String[size]);;
        JComboBox jcb3 = new JComboBox(vlist);
        jcb3.setFont(defaultfont);
        jp3.setFont(defaultfont);
        jp3.add(jlb3);
        jp3.add(jcb3);
        langset.add(jp3);
        jcb3.addItemListener(new ItemListener() {
            int j=0;
            @Override
            public void itemStateChanged(ItemEvent e){
                if(j==0){
                    j++;
                    JPanel changetemp2 = new JPanel();
                    LanguageReader a = new LanguageReader();
                    try{
                        String choose = (String) jcb3.getSelectedItem();
                        a.SetLanguage(choose);
                    } catch (IOException e2){
                        e2.printStackTrace();
                    }
                    System.exit(0);
                }

            }
        });
        // 2个文本框
        JPanel psp1 = new JPanel();
        JPanel psp2 = new JPanel();
        JLabel ps = new JLabel("Note: The program will be automatically closed");
        JLabel ps2 = new JLabel("after you switch languages. Please restart manually");
        ps.setFont(defaultfont);
        ps2.setFont(defaultfont);
        ps.setVisible(true);
        ps2.setVisible(true);
        psp1.add(ps);
        psp2.add(ps2);
        langset.add(psp1);
        langset.add(psp2);
    }


}
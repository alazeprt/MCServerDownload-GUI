import javax.swing.*;
import java.awt.*;

public class DownloadGUI extends JFrame{
    private DownloadGUI(){
        // Main(this) 主体
        this.setTitle("下载管理器");
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
        Font defaultfont = new Font("Microsoft YaHei UI", Font.PLAIN, 24);

        JPanel downloading2 = new JPanel();
        JPanel pb = new JPanel();
        JLabel downloading = new JLabel("正在下载中,请稍后......");
        downloading.setFont(defaultfont);
        downloading.setVisible(true);
        JLabel download_progress_string = new JLabel("下载进度: ");
        JProgressBar download_progress = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        download_progress.setStringPainted(true);
        download_progress.setBorderPainted(true);
        download_progress.setVisible(true);
        downloading2.add(downloading);
        pb.add(download_progress);

        this.add(downloading2);
        this.add(pb);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        DownloadGUI a = new DownloadGUI();
    }

}

package screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;
@SuppressWarnings("all")
public class MainThread extends JFrame{

    /**屏幕的分辨率*/
    public static int screen_w = 1024;//宽
    public static int screen_h = 682;//高
    public static int half_screen_w = screen_w/2;//半宽
    public static int half_screen_h = screen_h/2;//半高

    /**用Jpanel作为画板*/
    public static JPanel panel;//A generic lightweight container.

    /**使用 一个int数组 存储 屏幕上像素的数值*/
    public static int[] screen;

    /**屏幕图像缓冲区。它提供了在内存中操作屏幕中图像的方法*/
    public static BufferedImage screenBuffer;

    /**记载目前已渲染的 帧数*/
    public static int frameIndex;

    /**希望达到的每帧之间的间隔时间 (毫秒)*/
    public static int frameInterval = 33;

    /**cpu睡眠时间，数字越小 运算效率越高*/
    public static int sleepTime;

    /**刷新率FPS，及计算刷新率所用到一些辅助参数*/
    public static int framePerSecond;
    public static long lastDraw;
    public static double thisTime, lastTime;

    /**程序的入口点*/
    public static void main(String[] args) {
        new MainThread();
    }

    public MainThread(){

/*\/-----------------------------------------设置窗口和面板属性---------------------------------------------------\/*/

        /*弹出一个宽 为screen_w高为screen_h的Jpanel窗口，并把它放置屏幕中间。*/

        setTitle("Java软光栅教程 1");//标题

        panel= (JPanel) this.getContentPane();//JPanel是Container的子类
        //Returns the contentPane object for this frame.

        panel.setPreferredSize(new Dimension(screen_w, screen_h));
        //setPreferredSize仅仅是设置最好的大小，这个不一定与实际显示出来的控件大小一致
        // （根据界面整体的变化而变化）

        panel.setMinimumSize(new Dimension(screen_w,screen_h));
        //Sets the minimum size of this component to a constant value.

        panel.setLayout(null);//居中

        setResizable(false);//是否可调大小

        pack();//to fit the preferred size and layouts of its subcomponents.
        // The resulting width and height of the window
        // are automatically enlarged if either of dimensions is less than
        // the minimum size as specified by the previous call to the
        // setMinimumSize method.

        setVisible(true);//可见

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//Gets the size of the screen.
        //Some methods defined by Toolkit query the native operating system directly.
        //The Dimension class encapsulates the width and height of a component
        // (in integer precision) in a single object.

        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        //x –
        // the x-coordinate of the new location's
        // top-left corner
        // in the parent's coordinate space
        // y –
        // the y-coordinate of the new location's
        // top-left corner
        // in the parent's coordinate space

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Exit the application using the System exit method. Use this only in applications.

/*/\-----------------------------------------设置窗口和面板属性----------------------------------------------------/\*/


/*\/-----------------------------------------初始化像素数组--------------------------------------------------------\/*/

        //用TYPE_INT_RGB来创建BufferedImage，然后把屏幕的像素数组指向BufferedImage中的DataBuffer。
        //这样通过改变屏幕的像素数组(screen[])中的数据就可以在屏幕中渲染出图像

        //width – width of the created image
        // height – height of the created image
        // imageType – type of the created image
        screenBuffer =  new BufferedImage(screen_w, screen_h, BufferedImage.TYPE_INT_RGB);
        //Constructs a BufferedImage of one of the predefined image types.
        // The ColorSpace for the image is the default sRGB space.
        //BufferedImage.TYPE_INT_RGB -
        // Represents an image with 8-bit RGB color components packed into integer pixels.

        //Raster-光栅
        //A Raster defines values for
        // pixels occupying a particular rectangular area of the plane,
        // not necessarily including (0, 0).
        DataBuffer dest = screenBuffer.getRaster().getDataBuffer();
        ///Returns the DataBuffer associated with this Raster.

        //public final class DataBufferInt
        //extends java.awt.image.DataBuffer
        //This class extends DataBuffer and stores data internally as integers.
        screen = ((DataBufferInt)dest).getData();//Returns the default (first) int data array in DataBuffer.

/*/\------------------------------------------初始化像素数组---------------------------------------------------------/\*/



/*\/----------------------------------------把屏幕渲染成从天蓝色过渡到橙色----------------------------------------------\/*/

        while(true)
        {
            //天蓝色rgb
            int     r_skyblue = 163,
                    g_skyblue = 216,
                    b_skyblue = 239;
            //橙色rgb
            int     r_orange = 255,
                    g_orange = 128,
                    b_orange = 0;

            //把屏幕渲染成从天蓝色过渡到橙色的 动态画面
            for(int i = 0; i < screen_w; i++)
            {
                int p = (i + frameIndex*8)%screen_w;

                for(int j = 0; j < screen_h; j++)
                {
                    float t1 = Math.abs((float)(half_screen_w - p)/half_screen_w);
                    float t2 = 1f - t1;
                    int r = (int)(r_skyblue*t1 + r_orange*t2);
                    int g = (int)(g_skyblue*t1 + g_orange*t2);
                    int b = (int)(b_skyblue*t1 + b_orange*t2);
                    screen[i + j*screen_w] = (r << 16) | (g << 8) | b;
                }
            }


            //loop每运行一边，帧数就+1
            frameIndex++;



            /*------------------------------计算当前的刷新率，并尽量让刷新率保持恒定。--------------------------*/

            if(frameIndex%30==0)//是否为30帧
            {
                double thisTime = System.currentTimeMillis();
                framePerSecond = (int)(1000/((thisTime - lastTime)/30));
                lastTime = thisTime;
            }

            sleepTime = 0;

            while(System.currentTimeMillis()-lastDraw < frameInterval)
            {
                try
                {
                    Thread.sleep(1);
                    sleepTime++;             //让刷新率保持恒定。
                } catch (InterruptedException e1){e1.printStackTrace();}
            }
            lastDraw=System.currentTimeMillis();

            /*------------------------------计算当前的刷新率，并尽量让刷新率保持恒定。--------------------------*/




            /*--------------------------------显示当前刷新率-----------------------------------------------*/

            Graphics2D g2 = (Graphics2D)screenBuffer.getGraphics();
            //This method returns a Graphics2D, but is here for backwards compatibility.

            g2.setColor(Color.BLACK);
            //Sets this graphics context's current color to the specified color.
            // All subsequent graphics operations using this graphics context use this specified color.

            g2.drawString(
                    "FPS: " + framePerSecond + "   "  +
                        "Thread Sleep: " + sleepTime +
                        "ms    ",
                    5, 15);
            //str – the string to be rendered (已渲染的)
            // x – the x coordinate of the location where the String should be rendered
            // y – the y coordinate of the location where the String should be rendered

            /*--------------------------------显示当前刷新率-----------------------------------------------*/




            /*-----------------------把图像发画到显存里，这是唯一要用到显卡的地方---------------------------------*/

            panel.getGraphics().drawImage(screenBuffer, 0, 0, this);
            //getGraphics()-
            //Returns this component's graphics context, which lets you draw on a component.
            // Use this method to get a Graphics object and then
            // invoke operations on that object to draw on the component.
            //Params:
            //img – the specified image to be drawn. This method does nothing if img is null.
            // x – the x coordinate.
            // y – the y coordinate.
            // observer – object to be notified as more of the image is converted.
            //Returns:
            //false if the image pixels are still changing; true otherwise.

            /*-----------------------把图像发画到显存里，这是唯一要用到显卡的地方---------------------------------*/




        }
    }
}

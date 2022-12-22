package IFAbil;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.util.Arrays;

public class So_lat1_070322 {
public static void main(String[] args) throws IOException {
	BufferedImage img=null;
	File f =null;
	try {
		f = new File("D:/sms5/PC/file/COVER.jpg");
		img = ImageIO.read(f);
		System.out.println("berhasil membaca");
		}
	catch (IOException e) {
		System.out.println("gagal membaca");
	}
       
 int   T[] = {76};
int T1[] = {144,50};
          Writeimaged("Tres3",f,otsuMethod(clone(img)) );
         
     

    // write image
  
	
}
public static final BufferedImage clone(BufferedImage image) {
    BufferedImage clone = new BufferedImage(image.getWidth(),
            image.getHeight(), image.getType());
    Graphics2D g2d = clone.createGraphics();
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();
    return clone;
}

public static void Writeimaged  (String namaFile, File f, BufferedImage img) {
    try {
        f = new File(
            "D:/sms5/PC/file/"+namaFile+".png");
        ImageIO.write(img, "png", f);
        System.out.println("berhasil menulis");
    }
    catch (IOException e) {
        System.out.println(e);
        System.out.println("gagal membaca");
    }
}

public static BufferedImage otsuMethod(BufferedImage img){
	ColorSpace cs = img.getColorModel().getColorSpace();

	   if( cs.getType() != ColorSpace.TYPE_GRAY)
	   {
		   img=grayScale(img);
	   }
	int hist []= histogram(img);
	int sum= sum(hist,256);
	int sumb=0, sumf ;
	int Wtotal =w(hist,256);
	int wb=0,wf;
	double  var, minvar=0, minvar2=0;
	int check=0,check2;
	for(int i=0; i<256; i++) {
		wb+=hist[i];
		if (wb==0)continue;
		wf=Wtotal-wb;
		if (wf==0)break;
		sumb+=i*hist[i];
		sumf=sum-sumb;
		var= var(sumb,sumf,wb,wf,Wtotal,hist,i);
		if (minvar==0)// (minvar==0&&minvar2==0)
			{minvar=var;
			check=i;
			//minvar2=var;
			//check2=i;
			};
		if (minvar>var ) {
			minvar=var;
			check =i;
			
		/**
		 * if (minvar2>minvar)
		 *minvar2=var;
		 *check2;
		 */
		}
		
		
		
		
		
	}
		
	System.out.println("Treshhold = " + check);
	int t[]= {check};
	//{check,check2};
	img= Treshhold(img,t);
	return img;
}

public static double var (int sumb,int sumf,int wb, int wf, int wtotal,int hist[], int t) {
	
	double 	mf=(double)sumf/wf,
			mb=(double)sumb/wb,
			Wf= (double) wf/wtotal,
			Wb=(double)wb/wtotal,varb=0,varf=0;
			for (int i=0;i<256; i++) {
				if(hist[i]==0)continue;
				if(i<t) {
					varb+=Math.pow(i-mb, 2);
				}
				else {
					varf+=Math.pow(i-mf, 2);
				}
			}
			
	double var = Wb*varb+Wf*varf;
	
	return var;
}


public static int w (int [] hist, int t) {
	int sum=0;
	for(int i=0;i<t;i++) {
		sum+=hist[i];
	}
	
	
	return sum;
}

public static int sum (int [] hist, int t) {
	int sum=0;
	for(int i=0;i<t;i++) {
		sum+=i*hist[i];
	}
	
	
	return sum;
}

public static int [] histogram(BufferedImage img) {
	
	int hist[]= new int [256];
	
	int width = img.getWidth();
	int height = img.getHeight();
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
	    	 int p = img.getRGB(x, y);

	         int a = (p >> 24) & 0xff;
	         int r = (p >> 16) & 0xff;
	         int g = (p >> 8) & 0xff;
	         int b = p & 0xff;
	    	hist[r]++;
	    }
	    
	    }
	
	
	
	
	return hist;
}





public static BufferedImage Treshhold (BufferedImage t, int T[]) {
	ColorSpace cs = t.getColorModel().getColorSpace();

   if( cs.getType() != ColorSpace.TYPE_GRAY)
   {
	   t=grayScale(t);
   }
	int width = t.getWidth();
	int height = t.getHeight();
	
		
	// convert to 
	if (T.length ==1) {
		for (int y = 0; y < height; y++) {
		    for (int x = 0; x < width; x++) {
		        
		        // Here (x,y)denotes the coordinate of image
		        // for modifying the pixel value.
		    	 int p = t.getRGB(x, y);

		         int a = (p >> 24) & 0xff;
		         int r = (p >> 16) & 0xff;
		         int g = (p >> 8) & 0xff;
		         int b = p & 0xff;

		        // Here (x,y)denotes the coordinate of image
		        // for modifying the pixel value.
		        if (r >= T[0])
		        	 r=g=b=0;
		        else
		        	r=g=b=255;
		        p = (a << 24) | (r<< 16) | (g << 8)
		                | b;

		        t.setRGB(x, y, p);
		    }}
		
		
		
		
	}
	else if (T.length==2) {
		Arrays.sort(T);
		 
		for (int y = 0; y < height; y++) {
		    for (int x = 0; x < width; x++) {
		        
		    	 int p = t.getRGB(x, y);

		         int a = (p >> 24) & 0xff;
		         int r = (p >> 16) & 0xff;
		         int g = (p >> 8) & 0xff;
		         int b = p & 0xff;

		        // Here (x,y)denotes the coordinate of image
		        // for modifying the pixel value.
		        if (r>=T[1]) {
		        	r=g=b=0;
		        }
		        else if(r >= T[0] && r <T[1])
		        	 {r=g=b=128;}
		        else
		        	{r=g=b=255;}
		        p = (a << 24) | (r<< 16) | (g << 8)
		                | b;

		        t.setRGB(x, y, p);
		        }}
		    
		    }
	
	else {
		System.out.println ("gagal");
	}
	return t;

}
public static BufferedImage grayScale(BufferedImage gs) { 
	int width = gs.getWidth();
int height = gs.getHeight();

// convert to grayscale
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        
        // Here (x,y)denotes the coordinate of image
        // for modifying the pixel value.
        int p = gs.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        // calculate average
        int avg = (r + g + b) / 3;
        // replace RGB value with avg to  make graysSCALE
        p = (a << 24) | (avg<< 16) | (avg << 8)
            | avg;

        gs.setRGB(x, y, p);}}
	
return gs;
}

public static  BufferedImage negative(BufferedImage s) { 
	int width = s.getWidth();
int height = s.getHeight();

// convert to 
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        
        // Here (x,y)denotes the coordinate of image
        // for modifying the pixel value.
        int p = s.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        // turn negatif
        r = 255-r;
         g=  255-g;
         b= 255-b;
        // replace RGB value with avg to  make graysSCALE
        p = (a << 24) | (r<< 16) | (g << 8)
            | b;

        s.setRGB(x, y, p);}}
	
return s;
	
}
public static  BufferedImage sephia(BufferedImage n) { 
	int width = n.getWidth();
int height = n.getHeight();

// convert to grayscale
for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
        
        // Here (x,y)denotes the coordinate of image
        // for modifying the pixel value.
        int p = n.getRGB(x, y);

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

     // calculate newRed, newGreen, newBlue
        int newRed = (int)(0.393 * r + 0.769 * g
                           + 0.189 * b);
        int newGreen = (int)(0.349 * r + 0.686 * g
                             + 0.168 * b);
        int newBlue = (int)(0.272 * r + 0.534 * g
                            + 0.131 * b);
       int d,e,f;
        // check condition
        if (newRed > 255)
            d = 255;
        else
            d = newRed;

        if (newGreen > 255)
            e = 255;
        else
            e = newGreen;

        if (newBlue > 255)
            f = 255;
        else
            f = newBlue;
        // replace RGB value with avg to  make graysSCALE
        p = (a << 24) | (d<< 16) | (e << 8)
            | f;

        n.setRGB(x, y, p);}}
	
return n;
	
}







}








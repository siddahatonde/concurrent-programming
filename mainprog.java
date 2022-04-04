//Scanner class
import java.util.Scanner;

//Main class
public class mainprog
{
   //Main method
	public static void main(String[] args)
	{
		int number = 20;
	
		int i = 0;
                int j = 0; 
                int k = 0;
		for(k=0;k<21;k++)
	{
		number = number*2;
		
	
	   //Creating scanner object	
		Scanner read = new Scanner(System.in);
		
		//Taking input from user
		System.out.println("Please Enter the Number");
		number = read.nextInt();
	
		int minimum = 1;
		int maximum = 50;
		
		float [][]a = new float[number][number];
		float [][]b = new float[number][number];
		float [][]c = new float[number][number];
		for(i=0;i<number;i++)
		{
			for(j=0;j<number;j++)
			{
			   //Using math functions to calculate minimum and maximum number
				a[i][j] = (float)(minimum + (int)(Math.random() * maximum));
				b[i][j] = (float)(minimum + (int)(Math.random() * maximum));
			}
		}
		
		//Find the seconds for different nano functions
		double start = System.nanoTime();
		c = strassenMethod(a,b);
		
		double end = System.nanoTime();
        System.out.println((end-start)/1000000000 + " Seconds");
		
           	}			
	}

public static float[][] strassenMethod(float[][]a, float[][]b)
	{	
		int number = a.length;
		float[][] r = new float[number][number];
		if(number==1)
			r[0][0] = a[0][0]*b[0][0];
		

		
		//generally parallel better for lower sizes, should put if for general use
		 else
		{
			float[][] a11 = new float[number/2][number/2];
			float[][] a12 = new float[number/2][number/2];
			float[][] a21 = new float[number/2][number/2];
			float[][] a22 = new float[number/2][number/2];
			float[][] b11 = new float[number/2][number/2];
			float[][] b12 = new float[number/2][number/2];
			float[][] b21 = new float[number/2][number/2];
			float[][] b22 = new float[number/2][number/2];	

			class Multiple implements Runnable
			{
				private Thread t;
				private String threadName;
				Multiple(String name)
				{
					threadName=name;
				}
				public void run()
				{
					if(threadName.equals("thread1"))
					{
						spliterMethod(a,a11,0,0);
					}
					else if(threadName.equals("thread2"))
					{
						 spliterMethod(a,a12,0,number/2);			
					}
		
					else if(threadName.equals("thread3"))
					{
						  spliterMethod(a,a21,number/2,0);
					}
				
					else if(threadName.equals("thread4"))
					{
						spliterMethod(a,a22,number/2,number/2);
					}

					else if(threadName.equals("thread5"))
					{
						spliterMethod(b,b11,0,0);
					}
			
					else if(threadName.equals("thread6"))
					{
						 spliterMethod(b,b12,0,number/2);
					}
		
					else if(threadName.equals("thread7"))
					{
						spliterMethod(b,b21,number/2,0);
					}

					else if(threadName.equals("thread8"))
					{
						spliterMethod(b,b22,number/2,number/2);
					}
				}
			
				public void start()
				{
					if(t==null)
					{
						t=new Thread(this,"Thread1");
						t.start();
					}
				}
			}
			Multiple m1=new Multiple("thread1");
			m1.start();
			Multiple m2=new Multiple("thread2");
			m2.start();
			Multiple m3=new Multiple("thread3");
			m3.start();
			Multiple m4=new Multiple("thread4");
			m4.start();
			Multiple m5=new Multiple("thread5");
			m5.start();
			Multiple m6=new Multiple("thread6");
			m6.start();
			Multiple m7=new Multiple("thread7");
			m7.start();
			Multiple m8=new Multiple("thread8");
			m8.start();
			for (Thread t : new Thread[] { m1.t,m2.t,m3.t,m4.t,m5.t,m6.t,m7.t,m8.t })
			{
				try
				{
					t.join();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}

			
			float [][] M1 = strassenMethod(adderMethod(a11, a22), adderMethod(b11, b22));
           		float [][] M2 = strassenMethod(adderMethod(a21, a22), b11);
            		float [][] M3 = strassenMethod(a11, substituterMethod(b12, b22));
            		float [][] M4 = strassenMethod(a22, substituterMethod(b21, b11));
            		float [][] M5 = strassenMethod(adderMethod(a11, a12), b22);
            		float [][] M6 = strassenMethod(substituterMethod(a21, a11), adderMethod(b11, b12));
            		float [][] M7 = strassenMethod(substituterMethod(a12, a22), adderMethod(b21, b22));

			float [][] C11 = adderMethod(substituterMethod(adderMethod(M1, M4), M5), M7);
            		float [][] C12 = adderMethod(M3, M5);
            		float [][] C21 = adderMethod(M2, M4);
            		float [][] C22 = adderMethod(substituterMethod(adderMethod(M1, M3), M2), M6);

			class Multiple2 implements Runnable
			{
				private Thread t;
				private String threadName;
				Multiple2(String name)
				{
					threadName=name;
				}

				public void run()
				{
					if(threadName.equals("Threader 1"))
					{
						jointerMethod(C11, r, 0 , 0);
					}
					else if(threadName.equals("Thread 2"))
					{
						 jointerMethod(C12, r, 0 , number/2);			
					}
		
					else if(threadName.equals("Threader 3"))
					{
						 jointerMethod(C21, r, number/2, 0);
            		
					}
				
					else if(threadName.equals("Threader4"))
					{
						 jointerMethod(C22, r, number/2, number/2);
					}

				}

				public void starterMethod()
				{
					if(t==null)
					{
						t=new Thread(this,"Threader 1");
						t.start();
					}
				}
			}
			Multiple2 m11=new Multiple2("Threader 1");
			m11.starterMethod();
			Multiple2 m22=new Multiple2("Threader 2");
			m22.starterMethod();
			Multiple2 m33=new Multiple2("Threader 3");
			m33.starterMethod();
			Multiple2 m44=new Multiple2("Threader 4");
			m44.starterMethod();
			for (Thread t : new Thread[] { m11.t,m22.t,m33.t,m44.t })
			{
				try
				{
					t.join();
				}
				catch(Exception exception)
				{
					System.out.println(exception);
				}
			}
		}
		return r;
	}
	
public static void spliterMethod(float[][]pyl,float[][]cyl,int B,int B1)
	{
		int i2 = B;
		for(int i1=0;i1< cyl.length;i1++)
		{
			int j2 = B1;
			for(int j1=0; j1<cyl.length;j1++)
			{
				cyl[i1][j1] = pyl[i2][j2];
				j2++;
			}
			i2++;
		}
	}

public static float[][] adderMethod(float[][] a,float[][] b)
	{
		int n = a.length;
		float[][] c = new float[n][n];
		for(int i = 0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				c[i][j] = a[i][j] + b[i][j];
		}
	return c;
	}

public static float[][] substituterMethod(float[][] a,float[][] b)
	{
		int n = a.length;
		float[][] c = new float[n][n];
		for(int i = 0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				c[i][j] = a[i][j] - b[i][j];
		}
	return c;
	}
public static void jointerMethod(float[][]pyl,float[][]cyl,int B,int B1)
	{
		int iyl = B;
		for(int i1=0;i1< pyl.length;i1++)
		{
			int jyl = B1;
			for(int j1=0; j1< pyl.length;j1++)
			{
				cyl[iyl][jyl] = pyl[i1][j1];
				jyl++;
			}
			iyl++;
		}
	}
}
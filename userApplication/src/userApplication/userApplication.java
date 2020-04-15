/*!
  \file   userApplication.java
  \brief  Source code of project for Computer Networks I on serial communication with server Ithaki 
  \author Dimitra Karatza
  \AEM    8828
  \date   2020-4-15
*/

package userApplication;

import java.io.FileOutputStream;

import ithakimodem.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

//REQUEST CODES
class requestCodes {
    public static String echo="E9890\r";
    public static String image="M4308\r";
    public static String imageWithErrors="G5092\r";
    public static String gps="P0083"; //does not contain "\r" because gps code will be fixed inside its function
    public static String ack="Q5391\r";
    public static String nack="R5992\r";
}

public class userApplication {
	
	public static void main(String[] param) { 
		(new userApplication()).demo();
	}
	
	public void getEchoPacket(Modem modem){
		int k; 
		//ECHO REQUEST CODE
		byte[] ebytes = requestCodes.echo.getBytes();
		
		//to count the time
		String times = "";
		String packets = "";
		long t1, t2;
		long beginTime = System.currentTimeMillis();
		int num=0;
		
		//to write the time and the number of packages
		FileOutputStream fop = null;
		File file1;
		FileOutputStream fop2 = null;
		File file2;
		
		try {
			
			file1 = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ECHOtimes.txt");
			fop = new FileOutputStream(file1);
			file2 = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ECHOpackets.txt");
			fop2 = new FileOutputStream(file2);
		
			while ((System.currentTimeMillis()-beginTime)<240000) {
				
				String message="";
				t1 = System.currentTimeMillis();
				num++; //number of packets
				modem.write(ebytes);
				
				for (;;) {
					try {
						k = modem.read();
						System.out.print((char)k);
						message += (char)k;
						if (k==-1) break;		
						if (message.endsWith("PSTOP")){
							t2 = System.currentTimeMillis();
							times += String.valueOf(t2-t1) + "\r";
							packets += String.valueOf(num) + "\r";
							break;
						}
					}	
					catch (Exception x) {
						break;
					}				
				}
				System.out.println("");
			}
			System.out.println("Number of packets is: ");
			System.out.println(num);
			fop.write(times.getBytes());
			fop.close();
			fop2.write(packets.getBytes());
			fop2.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
				if (fop2 != null) {
					fop2.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getImage(Modem modem) { 
		
		//IMAGE REQUEST CODE
		byte[] ibytes = requestCodes.image.getBytes();
		modem.write(ibytes);
		
		FileOutputStream fop = null;
		File file;
		
		int i,j;
		
		try {
			
			file = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\Image.jpg");
			fop = new FileOutputStream(file);
			boolean flag = false;
			
			i = modem.read();
			
			for (;;){
				j=i;
				i = modem.read();
				if (i==-1) break;
				if (i==0xD8 && j==0xFF) { 
					flag = true; 
				}
				if(flag==true) {
					fop.write(j);
				}
				if (i==0xD9 && j==0xFF){
					break;
				}
			}
			fop.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void getImageWithErrors(Modem modem) {
			
		//IMAGE REQUEST CODE
		byte[] ibytes = requestCodes.imageWithErrors.getBytes();
		modem.write(ibytes);
		
		FileOutputStream fop = null;
		File file;
		
		int i,j;
		
		try {
			
			file = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ImageWithErrors.jpg");
			fop = new FileOutputStream(file);
			boolean flag = false;
			
			i = modem.read();
			
			for (;;){
				j=i;
				i = modem.read();
				if (i==-1) break;
				if (i==0xD8 && j==0xFF) { 
					flag = true; 
				}
				if(flag==true) {
					fop.write(j);
				}
				if (i==0xD9 && j==0xFF){
					break;
				}
			}
			fop.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void getGPS(Modem modem) {
		//GPS REQUEST CODE
		String gpsCode1 = requestCodes.gps+"R=1010050\r"; //path = 1010050 (1rst session) or 1020550 (2nd session) or 1011150 or 1049950 or 1000050
		byte[] gbytes = gpsCode1.getBytes();
		modem.write(gbytes);
		
		int k;
		int numOfCodes = 50; //number of GPS codes to get
		int differ = 10; //seconds that each point has to differ from the others
		
		String[] GPS = new String[numOfCodes]; //save the returned codes in this array
		for (int w=0; w<numOfCodes; w++) { //create an empty string (if not, then the string is null)
			GPS[w] = "";
		}
		
		int n=-1; //number of codes returned, it is not 0 because we start writing from position 0
		
		for (;;) {
			try {
				k = modem.read();
				if (k==-1) break;
						
				String l = String.valueOf((char)k);
				
				if((char)k=='$' ) { 
					n++; //start writing at the next line of the array
				}
				
				if(n>=0 && n<numOfCodes-1) { //n<numOfCodes-1 because we don't want the last line 
					GPS[n] = GPS[n] + l; //to avoid writing the lines which say "gps start" and "gps stop"
				}
				
			}	
			catch (Exception x) {
				break;
			}
		}
		
		//check array
		for (int w=0; w<numOfCodes; w++) { //array includes STOP ITHAKI GPS TRACKING 
			System.out.print(GPS[w]);
		}
		
		//split the returned codes
		
		String latitude; //we need at least 4 coordinates for the image co1=225735403737 co2=225734403736 co3=225734403737 co4=225733403738
		String longitude;
		int a=0,b=0; //number of spots
		String coordinates[]= new String[4];	
		boolean f = false;
		
		do {
	
			String[] parts = GPS[a].split(",");
			
			float partsFloat[]= new float[2]; //convert string to float
		
			//latitude
			partsFloat[0] = Float.valueOf(parts[2]);
	
			latitude =  Integer.toString((int)partsFloat[0]) +  Integer.toString((int)((partsFloat[0]-(int)partsFloat[0])*60));
				
			//longitude
			partsFloat[1] = Float.valueOf(parts[4]);
		
			longitude =  Integer.toString((int)partsFloat[1]) +  Integer.toString((int)((partsFloat[1]-(int)partsFloat[1])*60));
				
			//concatenate the longitude and the latitude
			coordinates[b] = "" + longitude + latitude;
			coordinates[b] = coordinates[b].replaceFirst("^0+(?!$)", ""); //get rid of the first 0
			
			//System.out.print(coordinates[b]+"\n");
			
			if(b!=0 && Objects.equals(coordinates[b], coordinates[b-1] )){ //coordinates[b]==coordinates[b-1])
				f = true;
				a++; //when we have 2 same coordinates we check the next coordinate
			}else {
				f = false;
				a=a+differ; //we want the coordinates to differ by "differ" seconds when we don't have same coordinates
				b++;
			}
		
		}while(f=true && a<numOfCodes && b<4); 
		
		String gpsCode2=requestCodes.gps+"T="+coordinates[0]+"T="+coordinates[1]+"T="+coordinates[2]+"T="+coordinates[3]+"\r";
		byte[] gbytesNew = gpsCode2.getBytes();
		modem.write(gbytesNew);
		
		FileOutputStream fop = null;
		File file;
		
		int i,j;
		
		try {
			
			file = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\GPSimage.jpg");
			fop = new FileOutputStream(file);
			boolean flag = false;
			
			i = modem.read();
			
			for (;;){
				j=i;
				i = modem.read();
				if (i==-1) break;
				if (i==0xD8 && j==0xFF) { 
					flag = true; 
				}
				if(flag==true) {
					fop.write(j);
				}
				if (i==0xD9 && j==0xFF){
					break;
				}
			}
			fop.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void getARQ(Modem modem) {
		int k;
		//ARQ REQUEST CODE
		String msg=requestCodes.ack;
		
		//to count the time
		String times = "";
		String packets = "";
		String resentString = "";
		long t1, t2;
		long beginTime = System.currentTimeMillis();
		int num=0;
		int numWrong=0;
		int numCorrect=0;
		int resent=0; //how many times a package was resent
		
		//to write the time, the number of packages and how many times a package was resent
		FileOutputStream fop = null;
		File file1;
		FileOutputStream fop2 = null;
		File file2;
		FileOutputStream fop3 = null;
		File file3;
		
		try {
			
			file1 = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ARQtimes.txt");
			fop = new FileOutputStream(file1);
			file2 = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ARQpackets.txt");
			fop2 = new FileOutputStream(file2);
			file3 = new File("C:\\Users\\dimit\\Desktop\\ComputerNetworksI\\CodeRepository\\ARQresent.txt");
			fop3 = new FileOutputStream(file3);
		
			while ((System.currentTimeMillis()-beginTime)<240000) { //repeat until 4 minutes pass
				t1 = System.currentTimeMillis();
				String encrypted = "";
				byte[] bytes = msg.getBytes();
				modem.write(bytes);
			
				for (;;) {
					try {
						k = modem.read();
						System.out.print((char)k);
						encrypted += (char)k; //save here the encrypted message
						if (k==-1) break;
						if (encrypted.endsWith("PSTOP")){
							System.out.println("");
							break;
						}
					}	
					catch (Exception x) {
						break;
					}
				}
				System.out.println("");
				if (encrypted.startsWith("PSTART")) {
					int start = encrypted.indexOf("<");
					int end = encrypted.indexOf(">");
					int FCS = Integer.parseInt(encrypted.substring(end+2,end+5));
					int temp = (int) encrypted.charAt(start + 1);
				
					for (int i = start+2; i<end; i++){
						temp = temp^(int)encrypted.charAt(i);
					}
	
					
					if (temp==FCS){
						//next
						msg=requestCodes.ack;
						num++; 
						numCorrect++;//number of successful packets
						t2 = System.currentTimeMillis();
						times += String.valueOf(t2-t1) + "\r";
						packets += String.valueOf(numCorrect) + "\r";
						resentString += String.valueOf(resent) + "\r";
						resent=0;
						System.out.println(resentString);
					}else {
						//repeat
						msg=requestCodes.nack;
						num++;
						numWrong++;
						resent++;
					}
				}
			}
			System.out.println("number of all packets is:");
			System.out.println(num);
			System.out.println("number of correct packets is:");
			System.out.println(numCorrect);
			System.out.println("number of wrong packets is:");
			System.out.println(numWrong);
			fop.write(times.getBytes());
			fop.close();
			fop2.write(packets.getBytes());
			fop2.close();
			fop3.write(resentString.getBytes());
			fop3.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
				if (fop2 != null) {
					fop2.close();
				}
				if (fop3 != null) {
					fop3.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void demo(){ 
		
		Modem modem;
		modem = new Modem();
		modem.setSpeed(80000);
		modem.setTimeout(2000);
		
		modem.open("ithaki");
		
		//echo request
		getEchoPacket(modem);	
		
		//image request
		getImage(modem);
		
		//image with errors request 
		getImageWithErrors(modem);
		
		//gps request
		getGPS(modem);
		
		//arq result
		getARQ(modem);
		
		modem.close();			
	}
}



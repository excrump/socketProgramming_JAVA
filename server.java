	package odevim;
	// Mert Crumpton
	
	import java.io.*;
	import java.net.*;
	import java.util.*;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
		
		class Handler  implements Runnable {
			
		public static String[] sorular = {"2+2=?", "2+3=?", "2+4=?", "2+5=?", "9+6=?","115+922=? ", "81212+2193*422-12*2(5-1/2)*x^2=?"};
		public static String[] cevaplar = {"4", "5", "6", "7","15","1037", "8293283"};
		
		public static int kullanicisayisi=0, kontrolcu=0;
		public Socket client;
		public BufferedReader in;
		public PrintWriter out;
		int i=0, s=0;
		int random = (int)(Math.random()*7);
		
		public Handler(Socket clientSocket) throws IOException {
			this.client= clientSocket;
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);	
		}
		
		@Override
		public void run() {
			try {
				kullanicisayisi = kullanicisayisi +1;
				while(true) {
					if(kullanicisayisi==2) {
						kontrolcu=1;
					}
					String request = in.readLine();
					if(kullanicisayisi==1 && kontrolcu==1) {
						out.println("Sona kaldınız, tebrikler kazandınız.");
				}
					else if(request.contains(cevaplar[i])) {
						i=i+1;
						out.println(sorular[i]);
						
					}else if(request.contains("basla")){
						out.println(sorular[i]);
					}
					else if(i==0){
						out.println("Baslamak icin basla yaziniz.");
					}else if(s==1){
						out.println("Üzgünüz, tekrar başlayamazsınız. Çıkış için cikis yazabilirsiniz. Sonraki mesajinizda otomatik olarak atilacaksiniz. ");
					}else
					{
						out.println("Kaybettiniz :(");
						s=1;
						kullanicisayisi= kullanicisayisi-1;
					}
				}
				
			} catch (IOException e) {
				System.out.println("HATA");
			} finally {
				out.close();
				try {
					in.close();
				}catch (IOException e) {
					out.println("hata");
				}
			}	
		}
	}
		public class Serverim {
			
			public static final int PORT = 8080;
			
			public static ArrayList<Handler> clients = new ArrayList<>();
			public static ExecutorService pool = Executors.newFixedThreadPool(4); 
	
			public static void main(String[] args) throws IOException {
				
				ServerSocket dinleyici = new ServerSocket(PORT);
				
				while (true) {
				System.out.println("Baglanti bekleniyor...");
				Socket client = dinleyici.accept();
				System.out.println("Baglanti basarili!");
				Handler thread = new Handler(client);
				
				clients.add(thread);
				pool.execute(thread);
				
					
				}
			}
		}

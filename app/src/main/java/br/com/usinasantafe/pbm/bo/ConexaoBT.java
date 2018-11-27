package br.com.usinasantafe.pbm.bo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.util.UUID;

public class ConexaoBT {

	private static final UUID uuid = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	private boolean running;
	private BluetoothSocket socket;
	private InputStream in;
	private BluetoothAdapter btfAdapter;
	
	public ConexaoBT() {
		// TODO Auto-generated constructor stub
	}
	
	public void conBT(){
		
//		Log.i("TESTE", "CHEGOU AKI 1");
//
//		btfAdapter = BluetoothAdapter.getDefaultAdapter();
//		Log.i("TESTE", "CHEGOU AKI 2");
//
//		if (btfAdapter != null && btfAdapter.isEnabled()) {
//			Log.i("TESTE", "CHEGOU AKI 3");
//			new ThreadServidor().start();
//			running = true;
//		}
		
	}
	
//	class ThreadServidor extends Thread {
//
//		@Override
//		public void run() {
//			try {
//				BluetoothServerSocket serverSocket = btfAdapter
//						.listenUsingRfcommWithServiceRecord("LivroAndroid", uuid);
//				try {
//					socket = serverSocket.accept();
//				} catch (Exception e) {
//					return;
//				}
//
//				Log.i("TESTE", "CHEGOU AKI 4");
//
//				if (socket != null) {
//
//					serverSocket.close();
//					in = socket.getInputStream();
//
//					byte[] bytes = new byte[1024];
//					int length;
//
//					Log.i("TESTE", "CHEGOU AKI 5");
//
//					while (running) {
//
//						length = in.read(bytes);
//						String mensagemRecebida = new String(bytes, 0, length);
//						Log.i("TESTE", "MSG RECEBIDA = " + mensagemRecebida);
//
//					}
//
//				}
//
//			} catch (IOException e) {
//				running = false;
//			}
//		}
//	}
	
}

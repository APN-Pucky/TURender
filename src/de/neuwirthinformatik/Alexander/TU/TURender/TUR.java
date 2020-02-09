package de.neuwirthinformatik.Alexander.TU.TURender;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.security.Permissions;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.json.JSONObject;

import de.neuwirthinformatik.Alexander.TU.TU;
import de.neuwirthinformatik.Alexander.TU.Basic.GlobalData;
import de.neuwirthinformatik.Alexander.TU.util.GUI;
import de.neuwirthinformatik.Alexander.TU.util.Log;
import de.neuwirthinformatik.Alexander.TU.util.Task;
import de.neuwirthinformatik.Alexander.TU.util.Wget;

public class TUR {
		static {	
			GUI.setLookAndFeel();
		}
		public JFrame frame;
		public static String start_time = time();
		public static TUR _this;
		public static Log log = new Log();
		public RenderPanel rp;

		
		public static void startTUR()
		{
			new TUR();
		}
		
		public TUR()
		{
			TUR._this = this;
			GlobalData.init();
			frame = new JFrame();
			
			frame.setIconImage(GUI.icon);
			frame.setTitle("TUR");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
			frame.setResizable(true);
			
			
			
			JTabbedPane tabbedPane = new JTabbedPane();
			
			
			tabbedPane.addTab("Render", null, new JScrollPane(rp = new RenderPanel()),
	                "Render Cards");
			

			
			
			
			frame.add(tabbedPane);
			
			
			frame.pack();
	        frame.setVisible(true);
		}
		
		public static String time() {return Task.time();}
		
		
		
		
		public static void updateXML()  {updateXML(false);}
		public static void updateXML(boolean dev)
		{
			String tyrant_url = (dev?"http://mobile-dev.tyrantonline.com/assets/":"http://mobile.tyrantonline.com/assets/");
			//XML
			System.out.println("Downloading new XMLs ...");
			Task.start(() -> {
				int i =0;
		        Wget.Status status = Wget.Status.Success;
		        while (status == Wget.Status.Success)
		        {
		        	i++;
					String sec = "cards_section_" + i + ".xml";
					status = Wget.wGet("data" + GlobalData.file_seperator + sec, tyrant_url + sec);
				}
			});
			final String[] arr = new String[] {"fusion_recipes_cj2","missions","levels","skills_set"};
			for(int i =0; i < arr.length;i++)
			{
				final String sec = arr[i] + ".xml";
				Task.start(() -> Wget.wGet("data" + GlobalData.file_seperator + sec,tyrant_url + sec));
			}
			Task.start(() -> Wget.wGet("data" + GlobalData.file_seperator + "raids.xml", "https://raw.githubusercontent.com/APN-Pucky/tyrant_optimize/merged/data/raids.xml"));
			Task.start(() -> Wget.wGet("data" + GlobalData.file_seperator + "bges.txt", "https://raw.githubusercontent.com/APN-Pucky/tyrant_optimize/merged/data/bges.txt"));
		}
		
		public static void update()
		{		
			updateXML();
		}
		
		public static void main(String[] args)
		{
			
			startTUR();
		}
		
}

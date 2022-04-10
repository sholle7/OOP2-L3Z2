package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	
	private Panel juzniPanel, istocniPanel, p1, p2, p3, p4, p5;
	private Button igraj;
	private Label labelaBalans;
	private Label labelaKvota;
	private Label labelaDobitak;
	private Label pomocni;
	private Label statusnaTraka;
	private TextField tekstUlog;
	private Mreza mr;
	private int ukupnoPolja;
	
	public Igra() {
		super("Igra");
		
		//kreiranje mreze podrazumevane velicine i postavljanje tekuce klase za njenog "ownera"
		mr = new Mreza(this);
		setSize(600, 300);
		ukupnoPolja=mr.dohvBr();
		//inicijalizacija polja klase Igra
		tekstUlog = new TextField("100");
		tekstUlog.setColumns(3);
	    labelaBalans = new Label("0");
		labelaKvota = new Label("0");
		labelaDobitak = new Label("0");
		pomocni = new Label("");
		igraj = new Button("Igraj");
		statusnaTraka = new Label("");


		//dodavanje mreze
		add(mr,BorderLayout.CENTER);
		
		//kreiranje panela i organizacija objekata 	
		dodajJuzni();
		dodajIstocni();
	
		//dodavanje svih panela na prozor
		add(istocniPanel, BorderLayout.EAST);
		add(juzniPanel, BorderLayout.SOUTH);
		
		//podesavanje prozora
		setResizable(true);
		setVisible(true);
		dodajOsluskivace();
		pack();
		setLocationRelativeTo(null);
	}
	
	//juzni panel
	private void dodajJuzni() {
		juzniPanel=new Panel();
		juzniPanel.add(statusnaTraka);
		//juzniPanel.setPreferredSize(new Dimension(getWidth(),getHeight()/20));
		juzniPanel.setBackground(Color.darkGray);
	}

	//istocni panel
	private void dodajIstocni() {
		istocniPanel=new Panel(new GridLayout(5,1));
		istocniPanel.setBackground(Color.lightGray);
		istocniPanel.setPreferredSize(new Dimension(getWidth()/4, getHeight()-juzniPanel.getHeight()));
		
		p1=new Panel();
	    //p1.setPreferredSize(new Dimension(getWidth()/4, (getHeight())/6-juzniPanel.getHeight()));
		p1.add(new Label("Balans: "));
		p1.add(labelaBalans);
		istocniPanel.add(p1);
		
		p2=new Panel();
		//p2.setPreferredSize(new Dimension(getWidth()/4, (getHeight())/6-juzniPanel.getHeight()));
		p2.add(new Label("Ulog: "));
		p2.add(tekstUlog);
		istocniPanel.add(p2);
		
		p3=new Panel();
		//p3.setPreferredSize(new Dimension(getWidth()/4, (getHeight())/6-juzniPanel.getHeight()));
		p3.add(new Label("Kvota: "));
		p3.add(labelaKvota);
		istocniPanel.add(p3);
		
		p4=new Panel();
		//p4.setPreferredSize(new Dimension(getWidth()/4, (getHeight())/6-juzniPanel.getHeight()));
		p4.add(new Label("Dobitak: "));
		p4.add(labelaDobitak);
		istocniPanel.add(p4);
		
		p5=new Panel();
		//p5.setPreferredSize(new Dimension(getWidth()/4, (getHeight())/6-juzniPanel.getHeight()));
		p5.add(pomocni);
		p5.add(igraj);
		istocniPanel.add(p5);
	}

	//dodavanje osluskivaca za prozor 
	private void dodajOsluskivace() {
		//osluskivac za zatvaranje prozora
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();				
			}
		});
		
		//osluskivac za igraj dugme
		igraj.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mr.dohvSkup().size() > 0) {
					Generator g1 = new Generator();
					int b = g1.zahtevaj(0, ukupnoPolja-1);
					if(mr.dohvSkup().contains(b)) {
						statusnaTraka.setForeground(Color.green);
						labelaBalans.setText(String.valueOf(Double.parseDouble(labelaBalans.getText()) - Double.parseDouble(tekstUlog.getText())));
						labelaBalans.setText(String.valueOf(Double.parseDouble(labelaBalans.getText()) + Double.parseDouble(labelaDobitak.getText())));
						revalidate();
					}
					else {
						statusnaTraka.setForeground(Color.red);
						labelaBalans.setText(String.valueOf(Double.parseDouble(labelaBalans.getText()) - Double.parseDouble(tekstUlog.getText())));
						revalidate();
					}
					statusnaTraka.setText(String.valueOf(b));
					revalidate();
				}	
			}
		});
		
		//osluskivac za promenu uloga
		tekstUlog.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				azuriraj();		
			}
		});
		
		//osluskivac za textField za ulog
		tekstUlog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				  if (tekstUlog.getText().length() >= 6 ) e.consume(); 
			}
		});
		
		//osluskivac koji omogucava ispravan prikaz komponenti prozora prilikom resize-ovanja
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				revalidate();
			}
		});
	}
	
	//dohvatanje mreze
	public Mreza dohvMrezu() { return mr; }
	
	public static void main(String[] args) {
		new Igra();
	}

	//azuriranje kvote i dobitka
	public void azuriraj() {
		if(tekstUlog.getText().equals("")) tekstUlog.setText("0");
		if(mr.dohvSkup().size()==0) {
			labelaKvota.setText("0");
			labelaDobitak.setText("0");
		}
		else {
			labelaKvota.setText(String.valueOf(Math.round(mr.dohvBr()*100.0/mr.dohvSkup().size())/100.0));
			labelaDobitak.setText(String.valueOf(Math.round(Double.parseDouble(labelaKvota.getText())*100.0*Double.parseDouble(tekstUlog.getText()))/100.0));
		}
		revalidate();
	}
}

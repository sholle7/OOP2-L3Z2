package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Polje extends Canvas{
	private Mreza mreza;
	private int br;
	private Label labelaPolja;
	private Status status;
	
	public Polje(Mreza m,int b){
		mreza=m;
		br=b;
		labelaPolja = new Label("");
		labelaPolja.setText(Integer.toString(br));
		status= Status.SLOBODNO;
		setPreferredSize(new Dimension(75,75));
		setBackground(Color.orange);
		dodajOsluskivac();
	}
	//dohvatanje broja polja
	public int dohvBr() { return br; }
	//dodavanje osluskivaca za menjanje statusa pri kliku misa na polje
	private void dodajOsluskivac() {
		addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				if(status == Status.IZABRANO) status=Status.SLOBODNO;
				else status=Status.IZABRANO;	
				mreza.azuriraj();
			}
		});
		
	}
	//postavljanje mreze
	public void postaviMrezu(Mreza m) { mreza=m; }
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);	
		g.setFont(new Font("TimesRoman", Font.BOLD, (dohvManjuStranicuPolja()/3)));
		int p = g.getFontMetrics().stringWidth(labelaPolja.getText());
		if(status==Status.IZABRANO) {
			g.setColor(Color.blue);
			g.fillOval(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString(labelaPolja.getText(), getWidth()/2 - p/2, getHeight()/2 + p/2);
		}
		else {
			g.setColor(Color.black);
			g.drawString(labelaPolja.getText(), getWidth()/2 - p/2, getHeight()/2 + p/2);	
		}
	}
	//nalazenje manje stranice polja
	private int dohvManjuStranicuPolja() {
		return getWidth()<getHeight()? getWidth():getHeight();
	}
	//dohvatanje labelePolja
	public Label dohvLabeluPolja() { return labelaPolja; }
	//dohvatanje i postavljanje statusa polja
	public Status dohvStatus() { return status; }
	public void postStatus(Status s) { status=s; }
}

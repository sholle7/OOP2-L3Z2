package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.HashSet;

public class Mreza extends Panel{
	private Polje polja [][];
	private ArrayList<Polje> listaPolja = new ArrayList<>();
	private Igra igra;
	private int x, y;
	private int br;
	public Mreza(int x, int y, Igra ig) {
		setLayout(new GridLayout(x, y, 3, 3));
		this.x=x;
		this.y=y;
		igra=ig;
		polja = new Polje[x][y];
	
		for(int i=0; i<x; i++)
			for(int j=0; j<y; j++) {
				polja[i][j] = new Polje(this,br);
				add(polja[i][j]);
				br++;
			}
		setBackground(Color.black);
	}
	//dohvatanje broja polja
	public int dohvBr() { return br; }
	public Mreza(Igra i) {
		this(4, 5, i);
	}
	//dohvatanje skupa brojeva sa statusom izabrano
	public HashSet<Integer> dohvSkup() {
		HashSet<Integer> pomocni = new HashSet<>();
		for (Polje polje : listaPolja) if(polje.dohvStatus()==Status.IZABRANO)pomocni.add(polje.dohvBr());
		return pomocni;
	}
	//vracanje ArrayListe polja koji predstavljaju sva polja koji imaju status IZABRANO
	public ArrayList<Polje> dohvListuPolja(){ return listaPolja; }
	
	//azuriranje statusa svih polja
	public void azuriraj() {
		int flag;
		for(int i=0; i<x; i++)
			for(int j=0; j<y; j++) {
				flag=0;
				//ispiivanje da li je zadato polje izabrano
				if(polja[i][j].dohvStatus()==Status.IZABRANO) {
					//ako je polje izabrano ispituje se da li se on vec nalazi u listu
					for (Polje polje : listaPolja)if(polje.dohvBr()==polja[i][j].dohvBr())flag++;
					//ukoliko se polje ne nalazi u listi on se u nju dodaje
					if(flag==0) listaPolja.add(polja[i][j]);
				}
				//ponovno iscrtavanje polja posle azuriranja statusa	
				polja[i][j].repaint();
			}
		igra.azuriraj();
	}
}

package igra;

import java.util.Random;

public class Generator {
	public int zahtevaj(int donja, int gornja) {
		Random r = new Random();
		return r.nextInt((gornja - donja) + 1) + donja;
	}
}

package se.mejsla.korv.korvcli;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

public class KorvCliApplication {

	private static final Logger LOG = getLogger(KorvCliApplication.class);

	public static void main(String[] args) {
		Korvklient korvklient = new Korvklient();
		if(args.length == 0 || args[0].equalsIgnoreCase("alla")) {
			Korvar korvar = korvklient.hämtaAlla();
			korvar.getKorvar().forEach(KorvCliApplication::printKorv);
		} else {
			int id = Integer.parseInt(args[0]);
			Korv korven = korvklient.hämta(id);
			printKorv(korven);
		}
	}

	private static void printKorv(final Korv korv) {
		String format = String.format("Korvnamn: %-20s Smak: %s", korv.getNamn(), korv.getSmak());
		LOG.info(format);
	}
}
